package vn.azteam.tabasupport.modules.employee.object.model;

import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.BaseModel;

/**
 * package vn.azteam.tabasupport.modules.employee.object.model
 * created 1/24/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @since 1.0.0
 */
public class Division extends BaseModel {
	private long id;
	private long companyId;
	private long departmentId;
	private String name;
	private long managerId;
	private long subManagerId;
	private long accountantId;
	private long storeKeeperId;

	public long getId() {
		return id;
	}

	public Division setId(long id) {
		this.id = id;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public Division setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public long getDepartmentId() {
		return departmentId;
	}

	public Division setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
		return this;
	}

	public String getName() {
		return name;
	}

	public Division setName(String name) {
		this.name = name;
		return this;
	}

	public long getManagerId() {
		return managerId;
	}

	public Division setManagerId(long managerId) {
		this.managerId = managerId;
		return this;
	}

	public long getSubManagerId() {
		return subManagerId;
	}

	public Division setSubManagerId(long subManagerId) {
		this.subManagerId = subManagerId;
		return this;
	}

	public long getAccountantId() {
		return accountantId;
	}

	public Division setAccountantId(long accountantId) {
		this.accountantId = accountantId;
		return this;
	}

	public long getStoreKeeperId() {
		return storeKeeperId;
	}

	public Division setStoreKeeperId(long storeKeeperId) {
		this.storeKeeperId = storeKeeperId;
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
		return "Division{" +
				"id=" + id +
				", companyId=" + companyId +
				", departmentId=" + departmentId +
				", name='" + name + '\'' +
				", managerId=" + managerId +
				", subManagerId=" + subManagerId +
				", accountantId=" + accountantId +
				", storeKeeperId=" + storeKeeperId +
				'}';
	}
}
