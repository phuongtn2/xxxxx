package vn.azteam.tabasupport.modules.employee.object.model;

import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.BaseModel;

/**
 * package vn.azteam.tabasupport.modules.employee.object.model
 * created 1/25/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @since 1.0.0
 */
public class ObServer extends BaseModel {
	private long id;
	private long employeeId;
	private String firstName;
	private String lastName;
	private String fullName;
	private String phone;
	private long companyId;
	private long cultivationZoneId;

	public long getId() {
		return id;
	}

	public ObServer setId(long id) {
		this.id = id;
		return this;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public ObServer setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public ObServer setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public ObServer setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getFullName() {
		return fullName;
	}

	public ObServer setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public ObServer setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public ObServer setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public long getCultivationZoneId() {
		return cultivationZoneId;
	}

	public ObServer setCultivationZoneId(long cultivationZoneId) {
		this.cultivationZoneId = cultivationZoneId;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {

	}

	@Override
	public String toString() {
		return "ObServer{" +
				"id=" + id +
				", employeeId=" + employeeId +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", fullName='" + fullName + '\'' +
				", phone='" + phone + '\'' +
				", companyId=" + companyId +
				", cultivationZoneId=" + cultivationZoneId +
				'}';
	}
}
