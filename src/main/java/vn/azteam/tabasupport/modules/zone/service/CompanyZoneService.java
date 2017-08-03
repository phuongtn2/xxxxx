package vn.azteam.tabasupport.modules.zone.service;

import vn.azteam.tabasupport.modules.zone.object.model.CompanyZone;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.zone.service
 * Created 3/6/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface CompanyZoneService {

	/**
	 * Get all cultivation zone
	 *
	 * @return a {@link List < CompanyZone >} objects
	 */
	List<CompanyZone> getAllCultivationZone();

	/**
	 * Get all cultivation zone
	 *
	 * @param companyId a long
	 * @return a {@link List< CompanyZone >} objects
	 */
	List<CompanyZone> getAllCultivationZoneByCompanyId(long companyId);

	CompanyZone getCompanyZoneById(long companyId, long zoneId);
}
