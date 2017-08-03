package vn.azteam.tabasupport.modules.employee.service.impl;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.service.AccountService;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.employee.object.dao.EmployeeDao;
import vn.azteam.tabasupport.modules.employee.object.model.Department;
import vn.azteam.tabasupport.modules.employee.object.model.Division;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.modules.employee.object.model.Zone;
import vn.azteam.tabasupport.modules.employee.service.DepartmentService;
import vn.azteam.tabasupport.modules.employee.service.DivisionService;
import vn.azteam.tabasupport.modules.employee.service.EmployeeService;
import vn.azteam.tabasupport.modules.employee.service.ZoneService;
import vn.azteam.tabasupport.modules.user.service.impl.UserServiceImpl;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.employee.service.impl
 * created 12/22/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see UserServiceImpl
 * @see EmployeeService
 * @since 1.0.0
 */
@Service
public class EmployeeServiceImpl extends BaseServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private AccountService accountService;
	@Autowired
	private DivisionService divisionService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private ZoneService zoneService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createEmployee(Employee employee, String roleSlug) throws ValidationException {

		final List<ObjectError> errors = employee.getErrors();
		if (!errors.isEmpty()) {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}

		Division division = divisionService.getDivisionById(employee.getCompanyId(), employee.getDivisionId());
		Department department = departmentService.getDepartmentById(employee.getCompanyId(), employee.getDepartmentId());

		try {
			createTransaction();
			accountService.createAccount(employee, roleSlug);
			employeeDao.insertEmployee(employee);
			List<Zone> zones = employee.getZones();
			if (zones != null) {
				for (Zone zone : zones) {
					zone.setEmployeeId(employee.getId());
					zoneService.syncPostZone(employee.getId(), zone);
				}
			}

			// Update department, division role
			if (roleSlug.equalsIgnoreCase("technician_leader")) {
				division.setManagerId(employee.getId());
				divisionService.updateDivision(division);
			} else if (roleSlug.equalsIgnoreCase("storekeeper")) {
				division.setStoreKeeperId(employee.getId());
				divisionService.updateDivision(division);
			} else if (roleSlug.equalsIgnoreCase("accountant")) {
				division.setAccountantId(employee.getId());
				divisionService.updateDivision(division);
			} else if (roleSlug.equalsIgnoreCase("sub_director")) {
				department.setSubManagerId(employee.getId());
				departmentService.updateDepartment(department, true);
			} else if (roleSlug.equalsIgnoreCase("director")) {
				department.setManagerId(employee.getId());
				departmentService.updateDepartment(department, true);
			}
			transactionManager.commit(status);

		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"),
					PropertiesParserUtil.getProperty("validation.request.default.msg"));
		}

		return employee.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateEmployee(Employee employee) throws ValidationException, NoSuchElementException {
		final List<ObjectError> errors = employee.getErrors();
		if (!errors.isEmpty()) {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
		final Account account = Account.newInstanceFromChild(employee, Account.class, "employee");
		final long accountId = accountService.getAccountByUid(employee.getuId()).getId();
		try {
			createTransaction();
			accountService.updateAccount(account.setId(accountId));
			employeeDao.updateEmployee(employee);
			List<Zone> zones = employee.getZones();
			if (zones != null) {
				for (Zone zone : zones) {
					zone.setEmployeeId(employee.getId());
					zoneService.syncPostZone(employee.getId(), zone);
				}
			}
			transactionManager.commit(status);
		} catch (ValidationException e) {
			transactionManager.rollback(status);
			throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"),
					PropertiesParserUtil.getProperty("validation.request.default.msg"));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Employee> getAll() throws NullPointerException, BindingException {
		return employeeDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Employee getEmployeeById(long employeeId) throws NullPointerException, BindingException, NoSuchElementException {
		return getAll().stream().filter(e -> e.getId() == employeeId).findFirst().get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Employee getEmployeeByuId(long uId) {
		return getAll().stream().filter(e -> e.getuId() == uId).findFirst().get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void filterByEmployeeCode(List<Employee> listEmployees, String str) {
		listEmployees.removeIf(e -> !e.getEmployeeCode().toLowerCase().matches(String.format(".*%s.*", str).toString()));
	}
}
