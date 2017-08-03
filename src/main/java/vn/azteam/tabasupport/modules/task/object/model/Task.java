package vn.azteam.tabasupport.modules.task.object.model;

import org.springframework.beans.BeansException;
import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropRegistration;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;
import vn.azteam.tabasupport.modules.cropsession.service.impl.CropSessionServiceImpl;
import vn.azteam.tabasupport.modules.cultivation.object.model.Cultivation;
import vn.azteam.tabasupport.modules.cultivation.service.CultivationService;
import vn.azteam.tabasupport.modules.cultivation.service.impl.CultivationServiceImpl;
import vn.azteam.tabasupport.modules.farmer.object.model.Farmer;
import vn.azteam.tabasupport.modules.nursery.object.model.Nursery;
import vn.azteam.tabasupport.modules.nursery.service.NurseryService;
import vn.azteam.tabasupport.modules.nursery.service.impl.NurseryServiceImpl;
import vn.azteam.tabasupport.modules.task.service.TaskService;
import vn.azteam.tabasupport.modules.task.service.impl.TaskServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.PropertiesParserUtil;
import vn.azteam.tabasupport.util.StringUtil;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Package vn.azteam.tabasupport.modules.task.object.model
 * Created 2/13/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class Task extends BaseModel {

	private long id;
	private long cropId;
	private long companyId;
	private long departmentId;
	private long divisionId;
	private long registrationId;
	private long nurseryId;
	private long cultivationId;
	private String title;
	private String description;
	private Date startDate;
	private Date dueDate;
	private String morningTask;
	private String afternoonTask;
	private long assigneeId;
	private long ownerId;
	private Date workStartDate;
	private Date workDueDate;
	private long provinceId;
	private long districtId;
	private long wardId;
	private long status;
	private Nursery nursery;
	private Cultivation cultivation;
	private Farmer farmer;
	private List<TaskReport> taskReports;
	private int delFlag;

	public long getId() {

		return id;
	}

	public Task setId(long id) {
		this.id = id;
		return this;
	}

	public long getCropId() {
		return cropId;
	}

	public Task setCropId(long cropId) {
		this.cropId = cropId;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public Task setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public long getDepartmentId() {
		return departmentId;
	}

	public Task setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
		return this;
	}

	public long getDivisionId() {
		return divisionId;
	}

	public Task setDivisionId(long divisionId) {
		this.divisionId = divisionId;
		return this;
	}

	public long getRegistrationId() {
		return registrationId;
	}

	public Task setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public long getNurseryId() {
		return nurseryId;
	}

	public Task setNurseryId(long nurseryId) {
		this.nurseryId = nurseryId;
		return this;
	}

	public long getCultivationId() {
		return cultivationId;
	}

	public Task setCultivationId(long cultivationId) {
		this.cultivationId = cultivationId;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Task setTitle(String title) {
		this.title = title;
		return this;
	}

	public long getStatus() {
		return status;
	}

	public Task setStatus(long status) {
		this.status = status;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Task setDescription(String description) {
		this.description = description;
		return this;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Task setStartDate(Date startDate) {
		this.startDate = startDate;
		return this;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public Task setDueDate(Date dueDate) {
		this.dueDate = dueDate;
		return this;
	}

	public String getMorningTask() {
		return morningTask;
	}

	public Task setMorningTask(String morningTask) {
		this.morningTask = morningTask;
		return this;
	}

	public String getAfternoonTask() {
		return afternoonTask;
	}

	public Task setAfternoonTask(String afternoonTask) {
		this.afternoonTask = afternoonTask;
		return this;
	}

	public long getAssigneeId() {
		return assigneeId;
	}

	public Task setAssigneeId(long assigneeId) {
		this.assigneeId = assigneeId;
		return this;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public Task setOwnerId(long ownerId) {
		this.ownerId = ownerId;
		return this;
	}

	public Date getWorkStartDate() {
		return workStartDate;
	}

	public Task setWorkStartDate(Date workStartDate) {
		this.workStartDate = workStartDate;
		return this;
	}

	public Date getWorkDueDate() {
		return workDueDate;
	}

	public Task setWorkDueDate(Date workDueDate) {
		this.workDueDate = workDueDate;
		return this;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public Task setProvinceId(long provinceId) {
		this.provinceId = provinceId;
		return this;
	}

	public long getDistrictId() {
		return districtId;
	}

	public Task setDistrictId(long districtId) {
		this.districtId = districtId;
		return this;
	}

	public long getWardId() {
		return wardId;
	}

	public Task setWardId(long wardId) {
		this.wardId = wardId;
		return this;
	}

	public Nursery getNursery() {
		if (nurseryId < 1) return null;
		NurseryService nurseryService;
		try {
			nurseryService = ApplicationContextProvider.getApplicationContext().getBean(NurseryServiceImpl.class);
			nursery = nurseryService.getNurseryById(nurseryId, registrationId);
			return nursery;
		} catch (NoSuchElementException | NullPointerException | BeansException e) {
			logger.error(e);
			return null;
		}
	}

	public Task setNursery(Nursery nursery) {
		this.nursery = nursery;
		return this;
	}

	public Cultivation getCultivation() {
		if (cultivationId < 1) {
			return null;
		}
		CultivationService cultivationService;
		try {
			cultivationService = ApplicationContextProvider.getApplicationContext().getBean(CultivationServiceImpl.class);
			cultivation = cultivationService.getCultivationById(companyId, cultivationId);
			return cultivation;
		} catch (NoSuchElementException | BeansException e) {
			logger.error(e);
			return null;
		}
	}

	public Task setCultivation(Cultivation cultivation) {
		this.cultivation = cultivation;
		return this;
	}

	public Farmer getFarmer() {
		if (registrationId < 1) return null;
		CropSessionService sessionService;
		try {
			sessionService = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
			CropRegistration cropRegistration = sessionService.getCropRegistrationById(registrationId);
			farmer = cropRegistration.getFarmer();
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}
		return farmer;
	}

	public Task setFarmer(Farmer farmer) {
		this.farmer = farmer;
		return this;
	}

	public List<TaskReport> getTaskReports() {
		if (taskReports != null) {
			return taskReports;
		}

		if (nurseryId == 0 && cultivationId == 0) {
			return null;
		}
		TaskService taskService;
		long actionId = (nurseryId != 0) ? nurseryId : cultivationId;
		try {
			taskService = ApplicationContextProvider.getApplicationContext().getBean(TaskServiceImpl.class);
			taskReports = taskService.getAllTaskReportByActionId(id, actionId);// list taskReport of nursery OR cultivation
			return taskReports;
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
			return null;
		}
	}

	public Task setTaskReports(List<TaskReport> taskReports) {
		this.taskReports = taskReports;
		return this;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public Task setDelFlag(int delFlag) {
		this.delFlag = delFlag;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (StringUtil.isEmpty(title)) {
			errors.rejectValue("title",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "task", "title"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "title"));
		}

		if (startDate == null) {
			errors.rejectValue("startDate",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "task", "startDate"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "startDate"));
		}

		if (dueDate == null) {
			errors.rejectValue("dueDate",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "task", "dueDate"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "dueDate"));
		}
	}
}
