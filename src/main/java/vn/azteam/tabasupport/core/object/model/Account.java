package vn.azteam.tabasupport.core.object.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.BeansException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import vn.azteam.tabasupport.core.service.AccountService;
import vn.azteam.tabasupport.core.service.OperationService;
import vn.azteam.tabasupport.core.service.impl.AccountServiceImpl;
import vn.azteam.tabasupport.core.service.impl.OperationServiceImpl;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.modules.employee.service.EmployeeService;
import vn.azteam.tabasupport.modules.employee.service.impl.EmployeeServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * package vn.azteam.tabasupport.core.object.dao
 * created 12/13/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see User
 * @see UserDetails
 * @see Serializable
 * @since 1.0.0
 */
public class Account extends User implements UserDetails, Serializable {
	private long id;
	private long uId;
	private long companyId;
	private int status;
	private String userId;
	@JsonIgnore
	private String password;
	private String deviceType;
	private String languageId;
	private String timeZoneId;
	private String loginIP;
	private String lastLoginIP;
	private Date loginDate;
	private Date lastLoginDate;
	@JsonIgnore
	private String pushKey;

	@JsonIgnore
	private String newPassword;
	@JsonIgnore
	private String confirmPassword;
	@JsonIgnore
	private String currentPassword;

	@JsonIgnore
	private Employee employee;

	public Employee getEmployee() {
		try {
			final EmployeeService service = ApplicationContextProvider.getApplicationContext().getBean(EmployeeServiceImpl.class);
			employee = service.getEmployeeByuId(uId);
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}
		return employee;
	}

	public Account setEmployee(Employee employee) {
		this.employee = employee;
		return this;
	}

	public long getId() {
		return id;
	}

	public Account setId(long id) {
		this.id = id;
		return this;
	}

	public long getuId() {
		return uId;
	}

	public Account setuId(long uId) {
		this.uId = uId;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public Account setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public int getStatus() {
		return status;
	}

	public Account setStatus(int status) {
		this.status = status;
		return this;
	}

	public String getUserId() {
		return userId;
	}

	public Account setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public Account setDeviceType(String deviceType) {
		this.deviceType = deviceType;
		return this;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public Account setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
		return this;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public Account setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
		return this;
	}


	/**
	 * Encode password with MD5 hash/
	 */
	public void encodePasswordMD5() {
		password = md5Encoding(password);
	}

	/**
	 * Encode with MD5 hash
	 *
	 * @param str a {@link String} Object
	 * @return a {@link String} Object
	 */
	private String md5Encoding(String str) {
		Md5PasswordEncoder pwEncoder = new Md5PasswordEncoder();
		return pwEncoder.encodePassword(str, null);
	}

	@JsonIgnore
	public String getCurrentPassword() {
		return currentPassword;
	}

	public Account setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
		return this;
	}

	@JsonIgnore
	public String getNewPassword() {
		return newPassword;
	}

	public Account setNewPassword(String newPassword) {
		this.newPassword = newPassword;
		return this;
	}

	@JsonIgnore
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public Account setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {

		AccountService service;
		try {
			service = ApplicationContextProvider.getApplicationContext().getBean(AccountServiceImpl.class);
			return service.getRolesByUid(getuId());
		} catch (NullPointerException | BeansException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Get Account Roles
	 *
	 * @return a {@link List} Role
	 */
	public List<Role> getRoles() {
		return (List<Role>) getAuthorities();
	}

	/**
	 * Get account operations
	 *
	 * @return Object
	 */
	public Collection<? extends GrantedAuthority> getOperations() {
		List<Operation> operations;
		AccountService service;

		try {
			service = ApplicationContextProvider.getApplicationContext().getBean(AccountServiceImpl.class);
			operations = service.getOperations(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			operations = new ArrayList<>();
		}
		return operations;
	}

	public String getPassword() {
		return password;
	}

	public Account setPassword(String password) {
		this.password = password;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@JsonIgnore
	public String getUsername() {
		return userId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return status > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return status > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return status > 0;
	}

	public String getLanguageId() {
		return languageId;
	}

	public Account setLanguageId(String languageId) {
		this.languageId = languageId;
		return this;
	}

	public String getTimeZoneId() {
		return timeZoneId;
	}

	public Account setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
		return this;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public Account setLoginIP(String loginIP) {
		this.loginIP = loginIP;
		return this;
	}

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public Account setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
		return this;
	}

	public String getPushKey() {
		return pushKey;
	}

	public Account setPushKey(String pushKey) {
		this.pushKey = pushKey;
		return this;
	}

	@Override
	protected void beforeValidate() {
		super.beforeValidate();
		Date now = new Date();
		status = 1;
		loginIP = loginIP == null || loginIP.isEmpty() ? "0.0.0.0" : loginIP;
		lastLoginDate = loginDate != null ? loginDate : now;
		loginDate = now;
		languageId = "vi_VN";
		timeZoneId = "+7";
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return this.getClass().equals(aClass);
	}

	/**
	 * Check when reset password
	 *
	 * @return a {@link List} object
	 */
	public List<ObjectError> validateResetPassword() {
		beforeValidate();
		MapBindingResult errors = new ErrorMapBindingResult(this, getClass().getName());
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "account", "newPassword"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "newPassword"));

		if (!newPassword.equals(confirmPassword))
			errors.rejectValue("newPassword",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "account", "currentPassword"),
					"New password and confirm password are not match.");

		newPassword = md5Encoding(newPassword);
		return errors.getAllErrors();
	}

	/**
	 * Check when change password
	 *
	 * @return a {@link List} object
	 */
	public List<ObjectError> validateChangePassword() {
		beforeValidate();

		MapBindingResult errors = new ErrorMapBindingResult(this, getClass().getName());

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentPassword",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "account", "currentPassword"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "currentPassword"));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "account", "newPassword"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "newPassword"));
		if (!md5Encoding(currentPassword).equals(password))
			errors.rejectValue("currentPassword",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "account", "currentPassword"),
					"Current password incorrect.");
		if (!newPassword.equals(confirmPassword))
			errors.rejectValue("newPassword",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "account", "currentPassword"),
					"New password and confirm password are not match.");

		newPassword = md5Encoding(newPassword);
		return errors.getAllErrors();
	}

	/**
	 * Get list ObjectError of model validation.
	 */
	public void validate(Object o, Errors errors) {
		super.validate(o, errors);
		final Account account = (Account) o;

		// check empty
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "account", "userId"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "userId"));

		if (account.getId() < 1) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "account", "password"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "password"));
		}
		if (account.getStatus() < 1) {
			errors.rejectValue("status",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "account", "status"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "status"));
		}
	}

	/**
	 * Check if user has global permission
	 *
	 * @param moduleId {@link String} operation module id
	 * @param slug     {@link String} operation slug
	 * @return boolean
	 */
	public boolean hasGlobalPermission(String moduleId, String slug) {
		final long[] globalRoleIds = getRoles().stream()
				.filter(role -> role.getScope().equals(Role.SCOPE_GLOBAL))
				.mapToLong(Role::getId).toArray();

		if (globalRoleIds == null || globalRoleIds.length < 1) {
			return false;
		}
		OperationService service;
		try {
			service = ApplicationContextProvider.getApplicationContext().getBean(OperationServiceImpl.class);
			return service.getOperationByRoleIds(globalRoleIds)
					.stream()
					.filter(o -> o.getModuleId().equals(moduleId.toLowerCase()) && o.getSlug().equals(slug.toLowerCase()))
					.count() > 0;
		} catch (NullPointerException | BeansException e) {
			logger.error(e.getMessage());
		}

		return false;
	}

	/**
	 * Check if user has any role
	 *
	 * @param roleSlug {@link String} operation module id
	 * @return boolean
	 */
	public boolean hasAnyRole(String roleSlug) {
		return getRoles().stream().anyMatch(role -> role.getSlug().toLowerCase().equals(roleSlug.toLowerCase()));
	}

	@Override
	public String toString() {
		return "Account{" +
				"id=" + id +
				", uId=" + uId +
				", companyId=" + companyId +
				", status=" + status +
				", userId='" + userId + '\'' +
				", password='" + password + '\'' +
				", languageId='" + languageId + '\'' +
				", timeZoneId='" + timeZoneId + '\'' +
				", loginIP='" + loginIP + '\'' +
				", lastLoginIP='" + lastLoginIP + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object obj) {
		if (!getClass().isInstance(obj))
			return false;

		final Account account = (Account) obj;
		return userId.equals(account.getUserId()) && companyId == account.getCompanyId();
	}
}