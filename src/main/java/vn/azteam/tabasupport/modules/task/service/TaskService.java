package vn.azteam.tabasupport.modules.task.service;

import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.modules.task.object.model.Task;
import vn.azteam.tabasupport.modules.task.object.model.TaskReport;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.task.service
 * Created 2/13/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface TaskService {

	List<Task> syncGetTask(long companyId, Account account);

	List<Task> getAllTask();

	List<Task> getAllTaskByCompanyId(long companyId);

	List<Task> getAllTaskByRoles(long companyId, Account account);

	List<Task> getAllTaskByEmployeeId(long companyId, Account account, long employeeId);

	Task getTaskById(long taskId, long companyId, Account account) throws ValidationException;

	Task syncPostTask(Task task, Account account) throws ValidationException;

	List<TaskReport> getAllTaskReport();

	List<TaskReport> getAllTaskReportByTaskId(long taskId);

	List<TaskReport> getAllTaskReportByActionId(long taskId, long actionId);

	TaskReport getTaskReportById(long taskId, long taskReportId);

	TaskReport syncTaskReport(Task task, TaskReport taskReport) throws ValidationException;
}
