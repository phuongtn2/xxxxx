package vn.azteam.tabasupport.modules.employee.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.modules.employee.object.model.Zone;
import vn.azteam.tabasupport.modules.employee.rest.EmployeeZoneApi;
import vn.azteam.tabasupport.modules.employee.service.EmployeeService;
import vn.azteam.tabasupport.modules.employee.service.ZoneService;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Package vn.azteam.tabasupport.modules.employee.rest.impl
 * Created 5/17/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@Component
public class EmployeeZoneApiImpl extends BaseRestImpl implements EmployeeZoneApi {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ZoneService zoneService;

	@Override
	public Object syncPostEmployeeZone(OAuth2Authentication auth, HttpServletRequest request, @PathVariable("employeeId") long employeeId, @RequestBody List<Zone> zones) throws ValidationException, NoSuchElementException {
		Account currentAccount = (Account) auth.getPrincipal();
		Employee currentEmployee = currentAccount.getEmployee();
		Employee employee = employeeService.getEmployeeById(employeeId);

		if (currentAccount.hasGlobalPermission("EMPLOYEE_MANAGER", "VIEW")
				|| currentEmployee.isDepartmentManager(employee.getCompanyId(), employee.getDepartmentId())
				|| currentEmployee.isDivisionManager(employee.getCompanyId(), employee.getDivisionId())) {
			for (Zone zone : zones) {
				zone.setEmployeeId(employeeId);
				zoneService.syncPostZone(employeeId, zone);
			}
			return zones;
		}
		throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "another department Or company"));
	}

	@Override
	public Object syncGetEmployeeZone(OAuth2Authentication auth, @PathVariable("employeeId") long employeeId) throws ValidationException, NoSuchElementException {
		Account currentAccount = (Account) auth.getPrincipal();
		Employee currentEmployee = currentAccount.getEmployee();
		Employee employee = employeeService.getEmployeeById(employeeId);

		if (currentAccount.hasGlobalPermission("EMPLOYEE_MANAGER", "VIEW")
				|| currentEmployee.isDepartmentManager(employee.getCompanyId(), employee.getDepartmentId())
				|| currentEmployee.isDivisionManager(employee.getCompanyId(), employee.getDivisionId())) {
			return zoneService.syncGetZoneByEmployeeId(employeeId);
		}

		throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "another department Or company"));
	}
}
