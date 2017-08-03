package vn.azteam.tabasupport.modules.move.object.model;

import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.BaseModel;

import java.util.Date;

/**
 * Package vn.azteam.tabasupport.modules.move.object.model
 * Created 5/15/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class Move extends BaseModel {

	private long id;
	private long companyId;
	private long employeeId;
	private long cropId;
	private String licensePlate;
	private int startSpeedometer;
	private int endSpeedometer;
	private Date moveStartDate;
	private Date moveDueDate;
	private String explanation;
	private long provinceId;
	private long districtId;
	private long wardId;
	private int delFlag;

	public long getId() {
		return id;
	}

	public Move setId(long id) {
		this.id = id;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public Move setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public Move setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	public long getCropId() {
		return cropId;
	}

	public Move setCropId(long cropId) {
		this.cropId = cropId;
		return this;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public Move setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
		return this;
	}

	public int getStartSpeedometer() {
		return startSpeedometer;
	}

	public Move setStartSpeedometer(int startSpeedometer) {
		this.startSpeedometer = startSpeedometer;
		return this;
	}

	public int getEndSpeedometer() {
		return endSpeedometer;
	}

	public Move setEndSpeedometer(int endSpeedometer) {
		this.endSpeedometer = endSpeedometer;
		return this;
	}

	public Date getMoveStartDate() {
		return moveStartDate;
	}

	public Move setMoveStartDate(Date moveStartDate) {
		this.moveStartDate = moveStartDate;
		return this;
	}

	public Date getMoveDueDate() {
		return moveDueDate;
	}

	public Move setMoveDueDate(Date moveDueDate) {
		this.moveDueDate = moveDueDate;
		return this;
	}

	public String getExplanation() {
		return explanation;
	}

	public Move setExplanation(String explanation) {
		this.explanation = explanation;
		return this;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public Move setProvinceId(long provinceId) {
		this.provinceId = provinceId;
		return this;
	}

	public long getDistrictId() {
		return districtId;
	}

	public Move setDistrictId(long districtId) {
		this.districtId = districtId;
		return this;
	}

	public long getWardId() {
		return wardId;
	}

	public Move setWardId(long wardId) {
		this.wardId = wardId;
		return this;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public Move setDelFlag(int delFlag) {
		this.delFlag = delFlag;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

	}
}
