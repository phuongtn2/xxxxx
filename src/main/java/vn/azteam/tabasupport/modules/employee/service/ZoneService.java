package vn.azteam.tabasupport.modules.employee.service;


import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.employee.object.model.Zone;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.employee.service
 * Created 5/15/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface ZoneService {

	Zone getZoneById(long employeeId, long zoneId);

	List<Zone> syncGetZoneByEmployeeId(long employeeId);

	Zone syncPostZone(long employeeId, Zone zone) throws ValidationException;
}
