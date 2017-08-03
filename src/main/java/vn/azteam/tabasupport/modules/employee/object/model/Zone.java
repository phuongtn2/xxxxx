package vn.azteam.tabasupport.modules.employee.object.model;

import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.BaseModel;

/**
 * Package vn.azteam.tabasupport.modules.employee.object.model
 * Created 5/15/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class Zone extends BaseModel {

	private long id;
	private long employeeId;
	private long cultivationZoneId;
	private int delFlag;

	public long getId() {
		return id;
	}

	public Zone setId(long id) {
		this.id = id;
		return this;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public Zone setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	public long getCultivationZoneId() {
		return cultivationZoneId;
	}

	public Zone setCultivationZoneId(long cultivationZoneId) {
		this.cultivationZoneId = cultivationZoneId;
		return this;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public Zone setDelFlag(int delFlag) {
		this.delFlag = delFlag;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return this.getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

	}
}
