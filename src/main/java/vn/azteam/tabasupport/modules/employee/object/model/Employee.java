package vn.azteam.tabasupport.modules.employee.object.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.BeansException;
import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.User;
import vn.azteam.tabasupport.core.service.AccountService;
import vn.azteam.tabasupport.core.service.impl.AccountServiceImpl;
import vn.azteam.tabasupport.modules.employee.service.DepartmentService;
import vn.azteam.tabasupport.modules.employee.service.ZoneService;
import vn.azteam.tabasupport.modules.employee.service.impl.DepartmentServiceImpl;
import vn.azteam.tabasupport.modules.employee.service.impl.ZoneServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * package vn.azteam.tabasupport.modules.employee.object.model
 * created 12/22/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see User
 * @since 1.0.0
 */
@JsonIgnoreProperties({"createId", "updateId", "created", "updated", "errors", "operations"})
public class Employee extends Account {
	private long uId;
	private String employeeCode;
	private long departmentId;
	private long divisionId;
	private String position;
	private List<Zone> zones = new ArrayList<>();

	@JsonIgnore
	public boolean isDirector(long companyId, long departmentId) {

		boolean isDirector = getRoles().get(0).getSlug().equalsIgnoreCase("director");
		if (isDirector && getDepartmentId() == departmentId && getCompanyId() == companyId) {
			return true;
		} else {
			return false;
		}
	}

	@JsonIgnore
	public boolean isSubDirector(long companyId, long departmentId) {

		boolean isSubDirector = getRoles().get(0).getSlug().equalsIgnoreCase("sub_director");
		if (isSubDirector && getDepartmentId() == departmentId && getCompanyId() == companyId) {
			return true;
		} else {
			return false;
		}
	}

	@JsonIgnore
	public boolean isAccountant(long companyId, long divisionId) {

		boolean isAccountant = getRoles().get(0).getSlug().equalsIgnoreCase("accountant");
		if (isAccountant && getDivisionId() == divisionId && getCompanyId() == companyId) {
			return true;
		} else {
			return false;
		}
	}

	@JsonIgnore
	public boolean isStoreKeeper(long companyId, long divisionId) {

		boolean isAccountant = getRoles().get(0).getSlug().equalsIgnoreCase("storekeeper");
		if (isAccountant && getDivisionId() == divisionId && getCompanyId() == companyId) {
			return true;
		} else {
			return false;
		}
	}

	@JsonIgnore
	public boolean isLeader(long companyId, long divisionId) {

		boolean isLeader = getRoles().get(0).getSlug().equalsIgnoreCase("technician_leader");
		if (isLeader && getDivisionId() == divisionId && getCompanyId() == companyId) {
			return true;
		} else {
			return false;
		}
	}

	@JsonIgnore
	public boolean isDepartmentManager(long companyId, long departmentId) {
		if (isDirector(companyId, departmentId) || isSubDirector(companyId, departmentId)) {
			return true;
		}
		return false;
	}

	@JsonIgnore
	public boolean isDivisionManager(long companyId, long divisionId) {
		if (isAccountant(companyId, divisionId)
				|| isStoreKeeper(companyId, divisionId)
				|| isLeader(companyId, divisionId)) {
			return true;
		}
		return false;
	}

	//role middle approve
	@JsonIgnore
	public boolean isMiddleApprove(long companyId, long divisionId) {
		if (isLeader(companyId, divisionId)) {
			return true;
		}

		return false;
	}

	// role latest approved
	@JsonIgnore
	public boolean isLatestApprove(long companyId, long divisionId) {
		if (isAccountant(companyId, divisionId)
				|| isStoreKeeper(companyId, divisionId)) {
			return true;
		}
		return false;
	}

	public long getDepartmentId() {
		return departmentId;
	}

	public Employee setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
		return this;
	}

	public long getDivisionId() {
		return divisionId;
	}

	public Employee setDivisionId(long divisionId) {
		this.divisionId = divisionId;
		return this;
	}

	public String getPosition() {
		return position;
	}

	public Employee setPosition(String position) {
		this.position = position;
		return this;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public Employee setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
		return this;
	}

	public long getuId() {
		return uId;
	}

	public Employee setuId(long uId) {
		this.uId = uId;
		return this;
	}

	public List<Zone> getZones() {
		if (zones != null && zones.size() > 0) {
			return zones;
		}
		ZoneService zoneService;
		try {
			zoneService = ApplicationContextProvider.getApplicationContext().getBean(ZoneServiceImpl.class);
			return zoneService.syncGetZoneByEmployeeId(getId());
		} catch (NullPointerException | BeansException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public Employee setZones(List<Zone> zones) {
		this.zones = zones;
		return this;
	}

	@JsonIgnore
	public long getAccountId() {
		AccountService service;
		try {
			service = ApplicationContextProvider.getApplicationContext().getBean(AccountServiceImpl.class);
			return service.getAccountByUid(uId).getId();
		} catch (NullPointerException | BeansException e) {
			logger.error(e.getMessage());
		}
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return this.getClass().equals(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object target, Errors errors) {
		super.validate(target, errors);
		final Employee employee = (Employee) target;
		if (employee.getDepartmentId() < 1) {
			errors.rejectValue("departmentId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "employee", "departmentId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "departmentId"));
		}

		if (employee.getDivisionId() < 1) {
			errors.rejectValue("divisionId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "employee", "divisionId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "divisionId"));
		}

		// email validation
		final Pattern pattern = Pattern.compile(PropertiesParserUtil.getProperty("validation.model.pattern.email.regexp"));
		if (!pattern.matcher(employee.getEmail()).matches()) {
			errors.rejectValue("email",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "employee", "email"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.email.incorrect", "email"));
		}

		if (employee.getPhone() == null || employee.getPhone().isEmpty()) {
			errors.rejectValue("phone",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "employee", "phone"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "phone"));
		}
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + getId() +
				"uId=" + uId +
				"employeeCode=" + employeeCode +
				'}';
	}

	public List<Long> getManagedDepartment(long companyId, long departmentId) {
		List<Long> departmentIds = new ArrayList<>();

		try {
			final DepartmentService departmentService = ApplicationContextProvider.getApplicationContext().getBean(DepartmentServiceImpl.class);
			//director
			if (isDepartmentManager(companyId, departmentId)) {
				departmentIds.addAll(departmentService.getChildrenId(companyId, departmentId));
			}
		} catch (NullPointerException | BeansException | BindingException e) {
			logger.error(e);
		}

		return departmentIds;
	}
}
