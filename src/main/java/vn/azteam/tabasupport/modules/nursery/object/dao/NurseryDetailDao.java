package vn.azteam.tabasupport.modules.nursery.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.nursery.object.model.NurseryDetail;

import java.util.List;

/**
 * package vn.azteam.tabasupport.modules.nursery.object.dao
 * created 1/20/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface NurseryDetailDao {
	/**
	 * Insert new NurseryDetail.
	 *
	 * @param detail {@link NurseryDetail} object
	 */
	void insert(@Param("detail") NurseryDetail detail);

	/**
	 * Update new NurseryDetail.
	 *
	 * @param detail {@link NurseryDetail} object
	 */
	void update(@Param("detail") NurseryDetail detail);

	/**
	 * Find all Nursery Details by nurseryIds.
	 *
	 * @param nurseryIds long[]
	 * @return a {@link List} object
	 */
	List<NurseryDetail> findAllByNurseryIds(@Param("nurseryIds") long[] nurseryIds);
}
