package vn.azteam.tabasupport.modules.cultivation.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.cultivation.object.model.CultivationManuring;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.object.dao
 * Created 1/18/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface CultivationManuringDao {

	long insertCultivationManuring(@Param("manuring") CultivationManuring cultivationManuring);

	/**
	 * Update cultivationManuring
	 *
	 * @param cultivationManuring a {@link CultivationManuring}
	 */
	void update(@Param("manuring") CultivationManuring cultivationManuring);

	/**
	 * find All  CultivationManuring by detailId
	 *
	 * @param detailId long
	 * @return a {@link List}
	 */
	List<CultivationManuring> findAllByDetailId(@Param("detailId") long detailId);
}
