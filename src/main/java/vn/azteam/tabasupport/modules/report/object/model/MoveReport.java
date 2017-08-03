package vn.azteam.tabasupport.modules.report.object.model;

import org.springframework.beans.BeansException;
import vn.azteam.tabasupport.core.service.LocationService;
import vn.azteam.tabasupport.core.service.impl.LocationServiceImpl;
import vn.azteam.tabasupport.modules.employee.service.EmployeeService;
import vn.azteam.tabasupport.modules.employee.service.impl.EmployeeServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.StringUtil;

import java.util.Date;
import java.util.NoSuchElementException;

/**
 * Package vn.azteam.tabasupport.modules.report.object.model
 * Created 3/13/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class MoveReport {
	private long employeeId;
	private long companyId;
	private Date moveStartDate;
	private Date moveDueDate;
	private int startSpeedometer;
	private int endSpeedometer;
	private int actualSpeedometer;
	private String explanation;
	private String licensePlate;
	private long provinceId;
	private long districtId;
	private long wardId;

	private String employeeName;
	private String moveStartDateString;
	private String hourStart;
	private String hourEnd;
	private String provinceName;
	private String districtName;
	private String wardName;

	public String getEmployeeName() {
		EmployeeService employeeService;
		try {
			employeeService = ApplicationContextProvider.getApplicationContext().getBean(EmployeeServiceImpl.class);
			employeeName = employeeService.getEmployeeById(employeeId).getFullName();
		} catch (NullPointerException | BeansException | NoSuchElementException e) {
			e.printStackTrace();
		}
		return employeeName;
	}

	public MoveReport setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public MoveReport setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public String getMoveStartDateString() {
		moveStartDateString = StringUtil.getDateFromDateTime(getMoveStartDate());
		return moveStartDateString;
	}

	public MoveReport setMoveStartDateString(String moveStartDateString) {
		this.moveStartDateString = moveStartDateString;
		return this;
	}

	public String getHourStart() {
		hourStart = StringUtil.getTimeFromDateTime(moveStartDate);
		return hourStart;
	}

	public MoveReport setHourStart(String hourStart) {
		this.hourStart = hourStart;
		return this;
	}

	public String getHourEnd() {
		hourEnd = StringUtil.getTimeFromDateTime(moveDueDate);
		return hourEnd;
	}

	public MoveReport setHourEnd(String hourEnd) {
		this.hourEnd = hourEnd;
		return this;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public MoveReport setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	public Date getMoveStartDate() {
		return moveStartDate;
	}

	public MoveReport setMoveStartDate(Date moveStartDate) {
		this.moveStartDate = moveStartDate;
		return this;
	}

	public Date getMoveDueDate() {
		return moveDueDate;
	}

	public MoveReport setMoveDueDate(Date moveDueDate) {
		this.moveDueDate = moveDueDate;
		return this;
	}

	public int getStartSpeedometer() {
		return startSpeedometer;
	}

	public MoveReport setStartSpeedometer(int startSpeedometer) {
		this.startSpeedometer = startSpeedometer;
		return this;
	}

	public int getEndSpeedometer() {
		return endSpeedometer;
	}

	public MoveReport setEndSpeedometer(int endSpeedometer) {
		this.endSpeedometer = endSpeedometer;
		return this;
	}

	public int getActualSpeedometer() {
		actualSpeedometer = 0;
		if (startSpeedometer <= endSpeedometer) {
			actualSpeedometer = endSpeedometer - startSpeedometer;
		}
		return actualSpeedometer;
	}

	public MoveReport setActualSpeedometer(int actualSpeedometer) {
		this.actualSpeedometer = actualSpeedometer;
		return this;
	}

	public String getExplanation() {
		return explanation;
	}

	public MoveReport setExplanation(String explanation) {
		this.explanation = explanation;
		return this;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public MoveReport setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
		return this;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public MoveReport setProvinceId(long provinceId) {
		this.provinceId = provinceId;
		return this;
	}

	public long getDistrictId() {
		return districtId;
	}

	public MoveReport setDistrictId(long districtId) {
		this.districtId = districtId;
		return this;
	}

	public long getWardId() {
		return wardId;
	}

	public MoveReport setWardId(long wardId) {
		this.wardId = wardId;
		return this;
	}

	public String getProvinceName() {
		if (provinceId < 1) return "";
		LocationService locationService;
		try {
			locationService = ApplicationContextProvider.getApplicationContext().getBean(LocationServiceImpl.class);
			provinceName = locationService.getProvinceById(provinceId).getName();
		} catch (NullPointerException | BeansException e) {
			e.printStackTrace();
		}
		return provinceName;
	}

	public MoveReport setProvinceName(String provinceName) {
		this.provinceName = provinceName;
		return this;
	}

	public String getDistrictName() {
		if (districtId < 1) return "";
		LocationService locationService;
		try {
			locationService = ApplicationContextProvider.getApplicationContext().getBean(LocationServiceImpl.class);
			districtName = locationService.getDistrictById(districtId).getName();
		} catch (NullPointerException | BeansException e) {
			e.printStackTrace();
		}
		return districtName;
	}

	public MoveReport setDistrictName(String districtName) {
		this.districtName = districtName;
		return this;
	}

	public String getWardName() {
		if (wardId < 1) return "";
		LocationService locationService;
		try {
			locationService = ApplicationContextProvider.getApplicationContext().getBean(LocationServiceImpl.class);
			wardName = locationService.getWardById(wardId).getName();
		} catch (NullPointerException | BeansException e) {
			e.printStackTrace();
		}
		return wardName;
	}

	public MoveReport setWardName(String wardName) {
		this.wardName = wardName;
		return this;
	}
}
