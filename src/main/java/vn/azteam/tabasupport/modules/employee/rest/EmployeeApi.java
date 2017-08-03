package vn.azteam.tabasupport.modules.employee.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.employee.rest
 * created 12/22/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@RestController
@RequestMapping("/employee")
public interface EmployeeApi {

	@PreAuthorize("hasPermission('EMPLOYEE_MANAGER','ADD_NEW')")
	@RequestMapping(value = "/sync", method = RequestMethod.POST, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object syncEmployee(OAuth2Authentication auth, HttpServletRequest request, @RequestBody List<Employee> employees)
			throws ValidationException, NoSuchElementException;

	/**
	 * Add new User
	 *
	 * @param auth   {@link OAuth2Authentication} object
	 * @param mapper {@link MultiValueMap} request data
	 * @return Object
	 * @throws ValidationException
	 * @throws NoSuchElementException
	 */
	@PreAuthorize("hasPermission('EMPLOYEE_MANAGER','ADD_NEW')")
	@RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object addEmployee(OAuth2Authentication auth, @RequestBody MultiValueMap<String, String> mapper)
			throws ValidationException, NoSuchElementException;

	/**
	 * Get Users
	 *
	 * @param request  {@link HttpServletRequest}
	 * @param auth     {@link OAuth2Authentication}
	 * @param companyId long
	 * @param isPaging boolean
	 * @param role     String
	 * @return Object
	 */
	@PreAuthorize("hasPermission('EMPLOYEE_MANAGER','VIEW')")
	@RequestMapping(value = "/get", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object getEmployees(HttpServletRequest request, OAuth2Authentication auth,
	                    @RequestParam(value = "companyId", defaultValue = "-1") long companyId,
	                    @RequestParam(value = "paging", defaultValue = "true") boolean isPaging,
	                    @RequestParam(value = "role", defaultValue = "") String role);

	/**
	 * Update Users
	 *
	 * @param auth {@link OAuth2Authentication} object
	 * @return Object
	 * @throws ValidationException
	 * @throws NullPointerException
	 */
	@PreAuthorize("hasPermission('EMPLOYEE_MANAGER','UPDATE')")
	@RequestMapping(value = "/update/{employeeId:[0-9]+}", method = RequestMethod.POST, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object updateEmployee(HttpServletRequest request, OAuth2Authentication auth, @PathVariable long employeeId, @RequestBody MultiValueMap<String, String> mapper)
			throws ValidationException, NoSuchElementException;
}
