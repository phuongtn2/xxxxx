package vn.azteam.tabasupport.modules.cropsession.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropDryingOven;

import java.util.List;

/**
 * package vn.azteam.tabasupport.modules.cropsession.object.dao
 * created 1/19/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface CropDryingOvenDao {
	/**
	 * Insert new CropDryingOven.
	 *
	 * @param cropDryingOven {@link CropDryingOven} object
	 */
	void insertCropDryingOven(@Param("cropDryingOven") CropDryingOven cropDryingOven);

	/**
	 * Update exist CropDryingOven.
	 *
	 * @param cropDryingOven {@link CropDryingOven} object
	 */
	void updateCropDryingOven(@Param("cropDryingOven") CropDryingOven cropDryingOven);

	/**
	 * Delete existed CropDryingOven
	 * Set NULL for column delFlag
	 *
	 * @param cropDryingOven
	 */
	void deleteCropDryingOven(@Param("cropDryingOven") CropDryingOven cropDryingOven);

	/**
	 * Find all Crop Drying Oven by registrationIds.
	 *
	 * @param registrationIds long[]
	 * @return a {@link List} object
	 */
	List<CropDryingOven> findAllByRegistrationIds(@Param("registrationIds") long[] registrationIds);
}
