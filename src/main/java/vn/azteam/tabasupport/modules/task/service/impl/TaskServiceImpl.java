package vn.azteam.tabasupport.modules.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.modules.task.object.dao.TaskDao;
import vn.azteam.tabasupport.modules.task.object.dao.TaskReportDao;
import vn.azteam.tabasupport.modules.task.object.model.Task;
import vn.azteam.tabasupport.modules.task.object.model.TaskReport;
import vn.azteam.tabasupport.modules.task.service.TaskService;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Package vn.azteam.tabasupport.modules.task.service.impl
 * Created 2/13/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@Service
public class TaskServiceImpl extends BaseServiceImpl implements TaskService {

	@Autowired
	private TaskDao taskDao;
	@Autowired
	private TaskReportDao taskReportDao;

	@Override
	public List<Task> syncGetTask(long companyId, Account account) {
		return getAllTaskByRoles(companyId, account);
	}

	@Override
	public List<Task> getAllTask() {
		return taskDao.findAll();
	}

	@Override
	public List<Task> getAllTaskByCompanyId(long companyId) {
		return getAllTask().stream().filter(
				task -> task.getCompanyId() == companyId
		).collect(Collectors.toList());
	}

	@Override
	public List<Task> getAllTaskByRoles(long companyId, Account account) {
		Employee employee = account.getEmployee();
		if (employee.isDepartmentManager(companyId, employee.getDepartmentId())) {
			return getAllTaskByCompanyId(companyId).stream().filter(
					task -> task.getDepartmentId() == employee.getDepartmentId()
			).collect(Collectors.toList());
		} else if (employee.isDivisionManager(companyId, employee.getDivisionId())) {
			return getAllTaskByCompanyId(companyId).stream().filter(
					task -> task.getDivisionId() == employee.getDivisionId()
			).collect(Collectors.toList());
		}
		return getAllTaskByCompanyId(companyId).stream().filter(
				task -> task.getAssigneeId() == employee.getId()
		).collect(Collectors.toList());
	}

	@Override
	public List<Task> getAllTaskByEmployeeId(long companyId, Account account, long employeeId) {
		return getAllTaskByRoles(companyId, account).stream().filter(
				task -> task.getAssigneeId() == employeeId
		).collect(Collectors.toList());
	}

	@Override
	public Task getTaskById(long taskId, long companyId, Account account) throws ValidationException {
		return getAllTaskByRoles(companyId, account).stream().filter(
				task -> task.getId() == taskId
		).findFirst().get();
	}

	@Override
	public Task syncPostTask(Task task, Account account) throws ValidationException {
		List<ObjectError> errors = task.getErrors();
		List<TaskReport> taskReports = task.getTaskReports();

		if (errors.isEmpty()) {
			if (task.getId() < 1) {
				taskDao.insertTask(task);
			} else {
				//check task existed
				getTaskById(task.getId(), account.getCompanyId(), account);
				taskDao.updateTask(task);
			}

			//Insert task report
			if (taskReports != null) {
				for (TaskReport taskReport : taskReports) {
					syncTaskReport(task, taskReport);
				}
			}

			return task;
		} else {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}

	}

	@Override
	public List<TaskReport> getAllTaskReport() {
		return taskReportDao.findAll();
	}

	@Override
	public List<TaskReport> getAllTaskReportByTaskId(long taskId) {
		return getAllTaskReport().stream().filter(
				taskReport -> taskReport.getTaskId() == taskId
		).collect(Collectors.toList());
	}

	@Override
	public List<TaskReport> getAllTaskReportByActionId(long taskId, long actionId) {
		return getAllTaskReportByTaskId(taskId).stream().filter(
				taskReport -> taskReport.getActionId() == actionId
		).collect(Collectors.toList());
	}

	@Override
	public TaskReport getTaskReportById(long taskId, long taskReportId) {
		return getAllTaskReportByTaskId(taskId).stream().filter(
				taskReport -> taskReport.getId() == taskReportId
		).findFirst().get();
	}

	@Override
	public TaskReport syncTaskReport(Task task, TaskReport taskReport) throws ValidationException {
		List<ObjectError> errors = taskReport.getErrors();
		taskReport.setTaskId(task.getId());
		taskReport.setRegistrationId(task.getRegistrationId());
		//check update moving action
		if (taskReport.getActionId() == 0) {
			taskReport.setPhase("MOVE");
		} else {
			if (task.getNurseryId() > 0) {
				taskReport.setPhase("NURSERY");
			} else if (task.getCultivationId() > 0) {
				taskReport.setPhase("CULTIVATION");
			} else {
				throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"),
						PropertiesParserUtil.getProperty("validation.request.default.msg"));
			}
		}
		if (errors.isEmpty()) {
			if (taskReport.getId() < 1) {
				taskReportDao.insertTaskReport(taskReport);
			} else {
				getTaskReportById(task.getId(), taskReport.getId());
				taskReportDao.updateTaskReport(taskReport);
			}
			return taskReport;
		} else {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
	}
}
