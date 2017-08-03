package vn.azteam.tabasupport.modules.report.object.model;

import org.springframework.beans.BeansException;
import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.core.service.LocationService;
import vn.azteam.tabasupport.core.service.impl.LocationServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.StringUtil;

import java.util.Date;

/**
 * Package vn.azteam.tabasupport.modules.report.object.model
 * Created 3/21/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class TaskPlanReport extends BaseModel {
	private long taskId;
	private long registrationId;
	private String title;
	private String description;
	private Date startDate;
	private String startDateReport;
	private String morningTask;
	private String afternoonTask;
	private long assigneeId;
	private long provinceId;
	private long districtId;
	private long wardId;
	private int endSpeedometer;
	private int currentSpeedometer;
	private String provinceName;
	private String districtName;
	private Date dateReport;

	public String getProvinceName() {
		if (provinceId < 1) return "";
		LocationService locationService;
		try {
			locationService = ApplicationContextProvider.getApplicationContext().getBean(LocationServiceImpl.class);
			provinceName = locationService.getProvinceById(provinceId).getName();
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}
		return provinceName;
	}

	public TaskPlanReport setProvinceName(String provinceName) {
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
			logger.error(e);
		}
		return districtName;
	}

	public TaskPlanReport setDistrictName(String districtName) {
		this.districtName = districtName;
		return this;
	}

	public long getTaskId() {
		return taskId;
	}

	public TaskPlanReport setTaskId(long taskId) {
		this.taskId = taskId;
		return this;
	}

	public long getRegistrationId() {
		return registrationId;
	}

	public TaskPlanReport setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public TaskPlanReport setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public TaskPlanReport setDescription(String description) {
		this.description = description;
		return this;
	}

	public Date getStartDate() {
		return startDate;
	}

	public TaskPlanReport setStartDate(Date startDate) {
		this.startDate = startDate;
		return this;
	}

	public String getStartDateReport() {
		startDateReport = StringUtil.getDateFromDateTime(startDate);
		return startDateReport;
	}

	public TaskPlanReport setStartDateReport(String startDateReport) {
		this.startDateReport = startDateReport;
		return this;
	}

	public String getMorningTask() {
		return morningTask;
	}

	public TaskPlanReport setMorningTask(String morningTask) {
		this.morningTask = morningTask;
		return this;
	}

	public String getAfternoonTask() {
		return afternoonTask;
	}

	public TaskPlanReport setAfternoonTask(String afternoonTask) {
		this.afternoonTask = afternoonTask;
		return this;
	}

	public long getAssigneeId() {
		return assigneeId;
	}

	public TaskPlanReport setAssigneeId(long assigneeId) {
		this.assigneeId = assigneeId;
		return this;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public TaskPlanReport setProvinceId(long provinceId) {
		this.provinceId = provinceId;
		return this;
	}

	public long getDistrictId() {
		return districtId;
	}

	public TaskPlanReport setDistrictId(long districtId) {
		this.districtId = districtId;
		return this;
	}

	public long getWardId() {
		return wardId;
	}

	public TaskPlanReport setWardId(long wardId) {
		this.wardId = wardId;
		return this;
	}

	public int getEndSpeedometer() {
		return endSpeedometer;
	}

	public TaskPlanReport setEndSpeedometer(int endSpeedometer) {
		this.endSpeedometer = endSpeedometer;
		return this;
	}

	public int getCurrentSpeedometer() {
		return currentSpeedometer;
	}

	public TaskPlanReport setCurrentSpeedometer(int currentSpeedometer) {
		this.currentSpeedometer = currentSpeedometer;
		return this;
	}

	public Date getDateReport() {
		dateReport = new Date();
		return dateReport;
	}

	public TaskPlanReport setDateReport(Date dateReport) {
		this.dateReport = dateReport;
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
