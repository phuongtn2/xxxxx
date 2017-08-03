package vn.azteam.tabasupport.modules.employee.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.SimpleResponse;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.core.service.AccountService;
import vn.azteam.tabasupport.core.service.CompanyService;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.modules.employee.rest.EmployeeApi;
import vn.azteam.tabasupport.modules.employee.service.EmployeeService;
import vn.azteam.tabasupport.modules.user.service.UserService;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.employee.rest.impl
 * created 12/22/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseRestImpl
 * @see EmployeeApi
 * @since 1.0.0
 */
@Component
public class EmployeeApiImpl extends BaseRestImpl implements EmployeeApi {
	static final String MODULE_ID = "EMPLOYEE_MANAGER";

	@Autowired
	private UserService userService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private CompanyService companyService;

	@Override
	public Object syncEmployee(OAuth2Authentication auth, HttpServletRequest request, @RequestBody List<Employee> employees) throws ValidationException, NoSuchElementException {
		for (Employee employee : employees) {
			List<ObjectError> errors = employee.getErrors();
			if (!errors.isEmpty()) {
				throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
			}

			final Account currentAccount = (Account) auth.getPrincipal();
			String roleSlug = "";

			final Map<String, String[]> paramsMapper = request.getParameterMap();
			if (paramsMapper.containsKey("roleSlug")) {
				roleSlug = request.getParameter("roleSlug");
			}

			employee.setFullName(String.format("%s %s", employee.getFirstName(), employee.getLastName())).insertAuthor(auth);
			employee.encodePasswordMD5();
			if (employee.getCompanyId() != currentAccount.getCompanyId()) {
				if (!currentAccount.hasGlobalPermission(MODULE_ID, "ADD_NEW") ||
						!companyService.getChildrenId(currentAccount.getCompanyId()).contains(employee.getCompanyId()))
					throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "another company"));
			} else {
				if (!currentAccount.hasGlobalPermission(MODULE_ID, "ADD_NEW") && roleSlug.equalsIgnoreCase("director")) {
					throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "roleSlug"));
				}
			}
			if (employee.getId() < 1) {
				if (!roleSlug.isEmpty()) {
					long accountId = employeeService.createEmployee(employee, roleSlug);
					employee.setId(accountId);
				}
			} else {
				//check existed employee
				employeeService.getEmployeeById(employee.getId());
				employee.setFullName(String.format("%s %s", employee.getFirstName(), employee.getLastName()))
						.insertAuthor(auth);

				employeeService.updateEmployee(employee);
			}
		}
		return employees;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object addEmployee(OAuth2Authentication auth, @RequestBody MultiValueMap<String, String> mapper)
			throws ValidationException {

		final Account account = (Account) auth.getPrincipal();
		final Employee employee = new Employee();
		final String roleSlug = mapper.containsKey("roleSlug") ? mapper.getFirst("roleSlug") : PropertiesParserUtil.getProperty("default.user.role.slug");

		employee.copyPropertiesFromMapper(mapper);
		employee.setFullName(String.format("%s %s", employee.getFirstName(), employee.getLastName())).insertAuthor(auth);
		employee.encodePasswordMD5();

		if (employee.getCompanyId() != account.getCompanyId()) {
			if (!account.hasGlobalPermission(MODULE_ID, "ADD_NEW") ||
					!companyService.getChildrenId(account.getCompanyId()).contains(employee.getCompanyId()))
				throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "another company"));
		} else {
			if (!account.hasGlobalPermission(MODULE_ID, "ADD_NEW") && roleSlug.equalsIgnoreCase("director")) {
				throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "roleSlug"));
			}
		}

		employee.insertAuthor(auth);
		long accountId = employeeService.createEmployee(employee, roleSlug);
		employee.setId(accountId);

		return new SimpleResponse(new Object[]{employee});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getEmployees(HttpServletRequest request, OAuth2Authentication auth,
	                           @RequestParam(value = "companyId", defaultValue = "-1") long companyId,
	                           @RequestParam(value = "paging", defaultValue = "true") boolean isPaging,
	                           @RequestParam(value = "role", defaultValue = "") String role) {
		final Account account = (Account) auth.getPrincipal();
		final Map<String, String[]> paramsMapper = request.getParameterMap();

		companyId = companyId < 1 ? account.getCompanyId() : companyId;

		if (companyId != account.getCompanyId() &&
				(!account.hasGlobalPermission(MODULE_ID, "VIEW") ||
						!companyService.getChildrenId(account.getCompanyId()).contains(companyId))) {
			throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "another company"));
		}

		final List<Employee> listEmployees = employeeService.getAll();

		userService.filterByCompanyIds(listEmployees, new long[]{companyId});

		// filter result
		paramsMapper.forEach((param, values) -> {
			switch (param) {
				case "firstName":
					if (values.length == 1)
						userService.filterByFirstName(listEmployees, values[0].toLowerCase());
					break;
				case "lastName":
					if (values.length == 1)
						userService.filterByLastName(listEmployees, values[0].toLowerCase());
					break;
				case "fullName":
					if (values.length == 1)
						userService.filterByFullName(listEmployees, values[0].toLowerCase());
					break;
				case "phone":
					if (values.length == 1)
						userService.filterByPhone(listEmployees, values[0]);
					break;
				case "employeeCode":
					if (values.length == 1)
						employeeService.filterByEmployeeCode(listEmployees, values[0].toLowerCase());
					break;
				case "userId":
					if (values.length == 1)
						accountService.filterByUserId(listEmployees, values[0].toLowerCase());
					break;
				default:
					break;
			}
		});

		// filter by role
		if (!role.isEmpty()) {
			accountService.filterByRoleSlug(listEmployees, role);
		}

		return !isPaging ? listEmployees : paging(request, listEmployees);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object updateEmployee(HttpServletRequest request, OAuth2Authentication auth, @PathVariable long employeeId, @RequestBody MultiValueMap<String, String> mapper)
			throws ValidationException, NoSuchElementException {
		Employee employee = employeeService.getEmployeeById(employeeId);
		final Account account = (Account) auth.getPrincipal();

		if (employee.getCompanyId() != account.getCompanyId()) {
			if (!account.hasGlobalPermission(MODULE_ID, "ADD_NEW") ||
					!companyService.getChildrenId(account.getCompanyId()).contains(employee.getCompanyId()))
				throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "another company"));
		}

		employee.copyPropertiesFromMapper(mapper, "id", "uId", "companyId");
		employee.setFullName(String.format("%s %s", employee.getFirstName(), employee.getLastName()))
				.insertAuthor(auth);

		employeeService.updateEmployee(employee);

		return new SimpleResponse(new Object[]{employee});
	}
}