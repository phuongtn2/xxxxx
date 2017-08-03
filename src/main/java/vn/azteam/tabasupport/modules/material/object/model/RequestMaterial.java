package vn.azteam.tabasupport.modules.material.object.model;

import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.BaseModel;

import java.util.Date;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.material.object.model
 * Created 3/7/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class RequestMaterial extends BaseModel {

	private long id;
	private long companyId;
	private long registrationId;
	private long departmentId;
	private long divisionId;
	private long employeeRequestId;
	private Date requestDate;
	private int approvedStatus;
	private String memo;
	private List<RequestMaterialDetail> requestMaterialDetails;

	public List<RequestMaterialDetail> getRequestMaterialDetails() {
		return requestMaterialDetails;
	}

	public RequestMaterial setRequestMaterialDetails(List<RequestMaterialDetail> requestMaterialDetails) {
		this.requestMaterialDetails = requestMaterialDetails;
		return this;
	}

	public long getId() {
		return id;
	}

	public RequestMaterial setId(long id) {
		this.id = id;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public RequestMaterial setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public long getDepartmentId() {
		return departmentId;
	}

	public RequestMaterial setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
		return this;
	}

	public long getDivisionId() {
		return divisionId;
	}

	public RequestMaterial setDivisionId(long divisionId) {
		this.divisionId = divisionId;
		return this;
	}

	public long getRegistrationId() {
		return registrationId;
	}

	public RequestMaterial setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public long getEmployeeRequestId() {
		return employeeRequestId;
	}

	public RequestMaterial setEmployeeRequestId(long employeeRequestId) {
		this.employeeRequestId = employeeRequestId;
		return this;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public RequestMaterial setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
		return this;
	}

	public int getApprovedStatus() {
		return approvedStatus;
	}

	public RequestMaterial setApprovedStatus(int approvedStatus) {
		this.approvedStatus = approvedStatus;
		return this;
	}

	public String getMemo() {
		return memo;
	}

	public RequestMaterial setMemo(String memo) {
		this.memo = memo;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

	}

	@Override
	public String toString() {
		return "RequestMaterial{" +
				"id=" + id +
				", companyId='" + companyId + '\'' +
				", registrationId='" + registrationId + '\'' +
				", employeeRequestId='" + employeeRequestId + '\'' +
				", requestDate='" + requestDate + '\'' +
				", approvedStatus='" + approvedStatus + '\'' +
				", memo='" + memo + '\'' +
				'}';
	}
}
