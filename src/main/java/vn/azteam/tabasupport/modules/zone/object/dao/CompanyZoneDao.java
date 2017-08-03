package vn.azteam.tabasupport.modules.zone.object.dao;

import vn.azteam.tabasupport.modules.zone.object.model.CompanyZone;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.object.dao
 * Created 3/6/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface CompanyZoneDao {

	List<CompanyZone> findAll();
}
