package vn.azteam.tabasupport.modules.employee.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.SimpleResponse;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.employee.object.model.Department;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.modules.employee.rest.DepartmentApi;
import vn.azteam.tabasupport.modules.employee.service.DepartmentService;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.employee.rest.impl
 * created 3/8/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseRestImpl
 * @see DepartmentApi
 * @since 1.0.0
 */
@Component
public class DepartmentApiImpl extends BaseRestImpl implements DepartmentApi {
	static final String MODULE_ID = "EMPLOYEE_MANAGER";

	@Autowired
	private DepartmentService departmentService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object updateDepartment(OAuth2Authentication auth, @RequestBody Department department) throws ValidationException, NoSuchElementException {
		final Account account = (Account) auth.getPrincipal();

		if (!account.getEmployee().getManagedDepartment(department.getCompanyId(), department.getId()).contains(department.getCompanyId()))
			throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "another company"));

		departmentService.updateDepartment(department);

		return new SimpleResponse(new Object[]{department});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getDepartments(HttpServletRequest request,
	                             OAuth2Authentication auth,
	                             @RequestParam(value = "companyId", defaultValue = "-1") long companyId,
	                             @RequestParam(value = "paging", defaultValue = "true") boolean isPaging) {

		final Account account = (Account) auth.getPrincipal();

		companyId = companyId < 1 ? account.getCompanyId() : companyId;

		if (companyId != account.getCompanyId() &&
				(!account.hasGlobalPermission(MODULE_ID, "VIEW"))) {
			throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "another company"));
		}

		final Employee employee = account.getEmployee();
		final List<Department> departments = new ArrayList<>();

		//admin
		if (account.hasGlobalPermission(MODULE_ID, "VIEW")) {
			List<Department> departments1 = departmentService.getAllByCompanyId(companyId);
			for (Department department : departments1) {
				departments.add(department);
			}

			return isPaging ? paging(request, departments) : departments;
		}
		if (employee.isDepartmentManager(companyId, employee.getDepartmentId())) {
			List<Long> departmentIds = employee.getManagedDepartment(companyId, employee.getDepartmentId());
			for (long id : departmentIds) {
				departments.add(departmentService.getDepartmentById(companyId, id));
			}
			departments.add(departmentService.getDepartmentById(companyId, employee.getDepartmentId()));
		} else {
			departments.add(departmentService.getDepartmentById(companyId, employee.getDepartmentId()));
		}

		return isPaging ? paging(request, departments) : departments;
	}
}
