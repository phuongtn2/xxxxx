package vn.azteam.tabasupport.modules.zone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.zone.object.dao.CompanyZoneDao;
import vn.azteam.tabasupport.modules.zone.object.model.CompanyZone;
import vn.azteam.tabasupport.modules.zone.service.CompanyZoneService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Package vn.azteam.tabasupport.modules.zone.service.impl
 * Created 3/6/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see CompanyZoneService
 * @since 1.0.0
 */
@Service
public class CompanyZoneServiceImpl extends BaseServiceImpl implements CompanyZoneService {

	@Autowired
	private CompanyZoneDao cultivationZoneDao;

	@Override
	public List<CompanyZone> getAllCultivationZone() {
		return cultivationZoneDao.findAll();
	}

	@Override
	public List<CompanyZone> getAllCultivationZoneByCompanyId(long companyId) {
		return getAllCultivationZone().stream().filter(
				cultivationZone -> cultivationZone.getCompanyId() == companyId
		).collect(Collectors.toList());
	}

	@Override
	public CompanyZone getCompanyZoneById(long companyId, long zoneId) {
		return getAllCultivationZoneByCompanyId(companyId).stream().filter(
				companyZone -> companyZone.getId() == zoneId
		).findFirst().get();
	}
}
