package vn.azteam.tabasupport.modules.task.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.core.service.NotificationService;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.modules.task.object.model.Task;
import vn.azteam.tabasupport.modules.task.rest.TaskApi;
import vn.azteam.tabasupport.modules.task.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.task.rest.impl
 * Created 2/13/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@Component
public class TaskApiImpl extends BaseRestImpl implements TaskApi {

	@Autowired
	private TaskService taskService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private CropSessionService cropSessionService;

	@Override
	public Object syncGetTask(OAuth2Authentication auth) {
		Account account = (Account) auth.getPrincipal();
		return taskService.syncGetTask(account.getCompanyId(), account);
	}

	@Override
	public Object syncGetTask(HttpServletRequest request, OAuth2Authentication auth, @PathVariable long employeeId) {
		Account account = (Account) auth.getPrincipal();
		List<Task> tasks = taskService.getAllTaskByEmployeeId(account.getCompanyId(), account, employeeId);
		return tasks;
	}

	@Override
	public Object syncPostTask(OAuth2Authentication auth, @RequestBody List<Task> tasks) throws ValidationException {
		logger.info(tasks);
		Account account = (Account) auth.getPrincipal();
		Employee employee = account.getEmployee();
		for (Task task : tasks) {
			task.setCropId(cropSessionService.getCurrentCropSessionId());
			task.setCompanyId(account.getCompanyId());
			task.setDepartmentId(employee.getDepartmentId());
			task.setDivisionId(employee.getDivisionId());
			if (task.getAssigneeId() == 0) {
				task.setAssigneeId(employee.getId());
			}
			task.setOwnerId(employee.getId());
			taskService.syncPostTask(task, account);
		}
		return tasks;
	}

	@Override
	public void insertData(OAuth2Authentication auth) throws ValidationException {
		notificationService.insertAllRecordPushNeededToDay();
	}

	@Override
	public void push(OAuth2Authentication auth) throws ValidationException {

		notificationService.pushNotification();
//		IOSPushNotificationService service = new IOSPushNotificationService();
//		service.pushIos("2e72b1295120eee2220547b56fc0e62582fc29039671fd987077dc9aba6dcd5f", "Tiếng việt");
	}
}
