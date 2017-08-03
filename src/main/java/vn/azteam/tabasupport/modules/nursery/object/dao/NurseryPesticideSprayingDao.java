package vn.azteam.tabasupport.modules.nursery.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.nursery.object.model.NurseryPesticideSpraying;

import java.util.List;

/**
 * package vn.azteam.tabasupport.modules.nursery.object.dao
 * created 2/10/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface NurseryPesticideSprayingDao {
	/**
	 * Insert new NurseryPesticideSpraying.
	 *
	 * @param spraying {@link NurseryPesticideSpraying} object.
	 */
	void insert(@Param("spraying") NurseryPesticideSpraying spraying);

	/**
	 * Update exists NurseryPesticideSpraying.
	 *
	 * @param spraying {@link NurseryPesticideSpraying} object.
	 */
	void update(@Param("spraying") NurseryPesticideSpraying spraying);

	/**
	 * Get all by nursery detail ids.
	 *
	 * @param detailIds long[]
	 * @return a {@link List} object
	 */
	List<NurseryPesticideSpraying> findAllByDetailIds(@Param("detailIds") long[] detailIds);
}
