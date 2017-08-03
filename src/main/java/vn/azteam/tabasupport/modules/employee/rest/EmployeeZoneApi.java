package vn.azteam.tabasupport.modules.employee.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.employee.object.model.Zone;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Package vn.azteam.tabasupport.modules.employee.rest
 * Created 5/17/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@RestController
@RequestMapping("/employee-zone")
public interface EmployeeZoneApi {

	@PreAuthorize("hasPermission('EMPLOYEE_MANAGER','ADD_NEW')")
	@RequestMapping(value = "/sync/{employeeId:[0-9]+}", method = RequestMethod.POST, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object syncPostEmployeeZone(OAuth2Authentication auth, HttpServletRequest request, @PathVariable("employeeId") long employeeId, @RequestBody List<Zone> zones)
			throws ValidationException, NoSuchElementException;

	@PreAuthorize("hasPermission('EMPLOYEE_MANAGER','VIEW')")
	@RequestMapping(value = "/sync/{employeeId:[0-9]+}", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object syncGetEmployeeZone(OAuth2Authentication auth, @PathVariable("employeeId") long employeeId)
			throws ValidationException, NoSuchElementException;
}
