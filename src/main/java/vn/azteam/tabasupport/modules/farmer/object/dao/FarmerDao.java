package vn.azteam.tabasupport.modules.farmer.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.farmer.object.model.Farmer;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.farmer.object.dao
 * Created 12/26/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface FarmerDao {

	/**
	 * Insert a farmer
	 * @param farmer a {@link Farmer} object.
	 */
	void insertFarmer(@Param("farmer")Farmer farmer);

	/**
	 * Update a farmer
	 * @param farmer a {@link Farmer} object.
	 */
	void updateFarmer(@Param("farmer")Farmer farmer);

	/**
	 * Get all farmer
	 * @return a {@link List<Farmer>} object.
	 */
	List<Farmer> findAllFarmer();
}
