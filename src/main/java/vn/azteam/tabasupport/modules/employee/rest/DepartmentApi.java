package vn.azteam.tabasupport.modules.employee.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.employee.object.model.Department;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.employee.rest
 * created 3/8/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@RestController
@RequestMapping("/department")
public interface DepartmentApi {

	/**
	 * Update department.
	 *
	 * @param auth       {@link OAuth2Authentication}
	 * @param department {@link Department}
	 * @return Object
	 * @throws ValidationException
	 * @throws NoSuchElementException
	 */
	@PreAuthorize("hasPermission('EMPLOYEE_MANAGER','DEPARTMENT_UPDATE')")
	@RequestMapping(value = "/update", method = RequestMethod.POST, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object updateDepartment(OAuth2Authentication auth, @RequestBody Department department)
			throws ValidationException, NoSuchElementException;

	/**
	 * Get all department.
	 *
	 * @param auth      {@link OAuth2Authentication}
	 * @param companyId long
	 * @param isPaging  boolean
	 * @return Object
	 * @throws ValidationException
	 * @throws NoSuchElementException
	 */
	@PreAuthorize("hasPermission('EMPLOYEE_MANAGER','DEPARTMENT_VIEW')")
	@RequestMapping(value = "/get", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object getDepartments(HttpServletRequest request, OAuth2Authentication auth,
	                      @RequestParam(value = "companyId", defaultValue = "-1") long companyId,
	                      @RequestParam(value = "paging", defaultValue = "true") boolean isPaging);
}
