package vn.azteam.tabasupport.modules.cropsession.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropMaterial;

import java.util.List;

/**
 * package vn.azteam.tabasupport.modules.cropsession.object.dao
 * created 1/18/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface CropMaterialDao {
	/**
	 * Insert new Crop Material.
	 *
	 * @param material a {@link CropMaterial} object
	 */
	void insertCropMaterial(@Param("material") CropMaterial material);

	/**
	 * Get all crop material by companyIds
	 *
	 * @param companyIds long[]
	 * @return a {@link List} object
	 */
	List<CropMaterial> findAllByCompanyIds(@Param("companyIds") long[] companyIds);

	/**
	 * Find all by registrationIds.
	 *
	 * @param registrationIds long[]
	 * @return a {@link List} object
	 */
	List<CropMaterial> findAllByRegistrationIds(@Param("registrationIds") long[] registrationIds);

	/**
	 * Update exist crop material.
	 *
	 * @param material long
	 */
	void updateCropMaterial(@Param("material") CropMaterial material);
}
