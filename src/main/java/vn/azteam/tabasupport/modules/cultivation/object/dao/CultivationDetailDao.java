package vn.azteam.tabasupport.modules.cultivation.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.cultivation.object.model.CultivationDetail;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.object.dao
 * Created 1/17/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface CultivationDetailDao {

	long insertCultivationDetail(@Param("cultivation") CultivationDetail cultivationDetail);

	/**
	 * Update CultivationDetail
	 *
	 * @param detail {@link CultivationDetail}
	 */
	void update(@Param("cultivation") CultivationDetail detail);

	/**
	 * Find all by cultivationId and fieldId.
	 *
	 * @param cultivationId long
	 * @param fieldId       long
	 * @return a {@link List}
	 */
	List<CultivationDetail> findAllByCultivationIdAndFieldPlotId(@Param("cultivationId") long cultivationId, @Param("fieldId") long fieldId);
}
