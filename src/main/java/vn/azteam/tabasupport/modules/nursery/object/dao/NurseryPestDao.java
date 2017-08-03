package vn.azteam.tabasupport.modules.nursery.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.nursery.object.model.NurseryPest;

import java.util.List;

/**
 * package vn.azteam.tabasupport.modules.nursery.object.dao
 * created 2/13/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface NurseryPestDao {
	/**
	 * Insert new NurseryPest.
	 *
	 * @param pest a {@link NurseryPest}
	 */
	void insert(@Param("pest") NurseryPest pest);

	/**
	 * Update new NurseryPest.
	 *
	 * @param pest a {@link NurseryPest}
	 */
	void update(@Param("pest") NurseryPest pest);

	/**
	 * Find all by nurseryDetailIds.
	 *
	 * @param detailIds long[]
	 * @return a {@link List} object.
	 */
	List<NurseryPest> findAllByDetailIds(@Param("detailIds") long[] detailIds);

	/**
	 * Find all by nurseryId.
	 *
	 * @param nurseryId long[]
	 * @return a {@link List} object.
	 */
	List<NurseryPest> findAllByNurseryId(@Param("nurseryId") long nurseryId);
}
