package vn.azteam.tabasupport.modules.cultivation.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.cultivation.object.model.CultivationPest;

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
public interface CultivationPestDao {

	long insertCultivationPest(@Param("pest") CultivationPest cultivationPest);

	/**
	 * Update  CultivationPest
	 *
	 * @param cultivationPest a {@link CultivationPest}
	 */
	void update(@Param("pest") CultivationPest cultivationPest);

	/**
	 * Find All  CultivationPest by detailId
	 *
	 * @param detailId long
	 * @return a {@link List}
	 */
	List<CultivationPest> findAllPestByDetailId(@Param("detailId") long detailId);
}
