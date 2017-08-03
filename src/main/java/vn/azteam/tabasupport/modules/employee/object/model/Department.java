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
public class Department extends BaseModel {
	private long id;
	private long parentId;
	private long companyId;
	private String name;
	private long managerId;
	private long subManagerId;

	public long getId() {
		return id;
	}

	public Department setId(long id) {
		this.id = id;
		return this;
	}

	public long getParentId() {
		return parentId;
	}

	public Department setParentId(long parentId) {
		this.parentId = parentId;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public Department setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public String getName() {
		return name;
	}

	public Department setName(String name) {
		this.name = name;
		return this;
	}

	public long getManagerId() {
		return managerId;
	}

	public Department setManagerId(long managerId) {
		this.managerId = managerId;
		return this;
	}

	public long getSubManagerId() {
		return subManagerId;
	}

	public Department setSubManagerId(long subManagerId) {
		this.subManagerId = subManagerId;
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
		return "Department{" +
				"id=" + id +
				", parentId=" + parentId +
				", companyId=" + companyId +
				", name='" + name + '\'' +
				", managerId=" + managerId +
				", subManagerId=" + subManagerId +
				'}';
	}
}
