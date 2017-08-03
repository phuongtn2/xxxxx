package vn.azteam.tabasupport.modules.employee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.employee.object.dao.ZoneDao;
import vn.azteam.tabasupport.modules.employee.object.model.Zone;
import vn.azteam.tabasupport.modules.employee.service.ZoneService;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.employee.service.impl
 * Created 5/15/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@Service
public class ZoneServiceImpl extends BaseServiceImpl implements ZoneService {

	@Autowired
	private ZoneDao zoneDao;

	@Override
	public Zone getZoneById(long employeeId, long zoneId) {
		return syncGetZoneByEmployeeId(employeeId).stream().filter(
				zone -> zone.getId() == zoneId
		).findFirst().get();
	}

	@Override
	public List<Zone> syncGetZoneByEmployeeId(long employeeId) {
		return zoneDao.findAll(employeeId);
	}

	@Override
	public Zone syncPostZone(long employeeId, Zone zone) throws ValidationException {
		List<ObjectError> errors = zone.getErrors();
		if (!errors.isEmpty()) {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
		if (zone.getId() > 0) {
			getZoneById(employeeId, zone.getId());
			if (zone.getDelFlag() > 0) {
				zoneDao.delete(zone.getId());
			}
		} else {
			zoneDao.insert(zone);
		}
		return zone;
	}
}
