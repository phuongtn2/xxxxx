package vn.azteam.tabasupport.modules.cultivation.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.cultivation.object.model.Cultivation;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.object.dao
 * Created 1/17/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface CultivationDao {
	long insertCultivation(@Param("cultivation") Cultivation cultivation);

	void updateCultivation(@Param("cultivation") Cultivation cultivation);

	List<Cultivation> findAllCultivationByCompanyIds(@Param("companyIds") long[] companyIds);

	/**
	 * Find all cultivation by responsibilityId in current crop.
	 *
	 * @param responsibilityId long
	 * @return a {@link List} Object.
	 */
	List<Cultivation> findAllCultivationResponsibilityEmployeeId(@Param("responsibilityId") long responsibilityId);
}
