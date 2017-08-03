package vn.azteam.tabasupport.modules.farmer.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.farmer.object.model.FarmerRelative;

import java.util.List;

/**
 * package vn.azteam.tabasupport.modules.farmer.object.dao
 * created 3/10/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface FarmerRelativeDao {

	/**
	 * Insert a farmer relative
	 *
	 * @param relative a {@link FarmerRelative} object.
	 */
	void insert(@Param("relative") FarmerRelative relative);

	/**
	 * Update a farmer relative
	 *
	 * @param relative a {@link FarmerRelative} object.
	 */
	void update(@Param("relative") FarmerRelative relative);

	/**
	 * Get all farmer relative by farmerId.
	 *
	 * @return a {@link List<FarmerRelative>} object.
	 */
	List<FarmerRelative> findAllByFarmerId(@Param("farmerId") long farmerId);
}
