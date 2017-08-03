package vn.azteam.tabasupport.modules.cultivation.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.cultivation.object.model.CultivationHarvest;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.object.dao
 * Created 1/19/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface CultivationHarvestDao {
	long insertCultivationHarvest(@Param("harvest") CultivationHarvest cultivationHarvest);

	/**
	 * Update exists
	 *
	 * @param cultivationHarvest a {@link CultivationHarvest} obj
	 */
	void update(@Param("harvest") CultivationHarvest cultivationHarvest);

	/**
	 * Find all Cultivation harvest by cultivationId
	 *
	 * @param cultivationId long
	 * @return a {@link List}
	 */
	List<CultivationHarvest> findAllHarvestByCultivationId(@Param("cultivationId") long cultivationId);

	/**
	 * Find all Cultivation harvest by registrationId
	 *
	 * @param registrationId long
	 * @return {@link List}
	 */
	List<CultivationHarvest> findAllHarvestByRegistrationId(@Param("registrationId") long registrationId);
}
