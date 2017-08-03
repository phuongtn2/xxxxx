package vn.azteam.tabasupport.modules.employee.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.employee.object.model.ObServer;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.employee.rest
 * created 3/9/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@RestController
@RequestMapping("/observer")
public interface ObserverApi {
	/**
	 * Add new ObServer
	 *
	 * @param auth {@link OAuth2Authentication} object
	 * @param obServer  {@link ObServer} request data
	 * @return Object
	 * @throws ValidationException
	 */
	@PreAuthorize("hasGlobalPermission('EMPLOYEE_MANAGER','ADD_OBSERVER')")
	@RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object addObserver(OAuth2Authentication auth, @RequestBody ObServer obServer)
			throws ValidationException;

	/**
	 * Add new ObServer
	 *
	 * @param auth {@link OAuth2Authentication} object
	 * @param obServer  {@link ObServer} request data
	 * @return Object
	 * @throws ValidationException
	 */
	@PreAuthorize("hasGlobalPermission('EMPLOYEE_MANAGER','UPDATE_OBSERVER')")
	@RequestMapping(value = "/update", method = RequestMethod.POST, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object updateObserver(OAuth2Authentication auth, @RequestBody ObServer obServer)
			throws ValidationException;

	/**
	 * getAll ObServer
	 *
	 * @param request {@link HttpServletRequest} object
	 * @param isPaging  boolean
	 * @return Object
	 * @throws NoSuchElementException
	 */
	@PreAuthorize("hasGlobalPermission('EMPLOYEE_MANAGER','VIEW_OBSERVER')")
	@RequestMapping(value = "/get", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object getAll(HttpServletRequest request, @RequestParam(name = "paging", defaultValue = "true") boolean isPaging)
			throws NoSuchElementException;

	/**
	 * get ObServer
	 * principal
	 *
	 * @param auth  {@link OAuth2Authentication} object
	 * @param observerId {@link Long} observerId
	 * @return Object
	 * @throws NoSuchElementException
	 */
	@PreAuthorize("hasGlobalPermission('EMPLOYEE_MANAGER','VIEW_OBSERVER')")
	@RequestMapping(value = "/get/{observerId:[0-9]+}", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object getObServer(OAuth2Authentication auth, @PathVariable("observerId") long observerId)
			throws NoSuchElementException;
}
