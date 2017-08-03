package vn.azteam.tabasupport.modules.task.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.task.object.model.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.task.rest
 * Created 2/13/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@RestController
@RequestMapping("/task")
public interface TaskApi {

	@PreAuthorize("hasPermission('TASK_MANAGER','VIEW')")
	@RequestMapping(value = "/sync", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object syncGetTask(OAuth2Authentication auth);

	@PreAuthorize("hasPermission('TASK_MANAGER','VIEW')")
	@RequestMapping(value = "/get/{employeeId:[0-9]+}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object syncGetTask(HttpServletRequest request, OAuth2Authentication auth, @PathVariable long employeeId);

	@PreAuthorize("hasPermission('TASK_MANAGER','UPDATE')")
	@RequestMapping(value = "/sync", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object syncPostTask(OAuth2Authentication auth, @RequestBody List<Task> tasks) throws ValidationException;

	//TODO: remove when release
	@RequestMapping(value = "/insert-data", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	void insertData(OAuth2Authentication auth) throws ValidationException;

	//TODO: remove when release
	@RequestMapping(value = "/push", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	void push(OAuth2Authentication auth) throws ValidationException;
}
