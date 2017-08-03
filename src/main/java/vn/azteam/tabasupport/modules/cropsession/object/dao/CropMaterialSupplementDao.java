package vn.azteam.tabasupport.modules.cropsession.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropMaterial;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropMaterialSupplement;

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
public interface CropMaterialSupplementDao {
	/**
	 * Insert new Crop Material.
	 *
	 * @param material a {@link CropMaterial} object
	 */
	void insert(@Param("material") CropMaterialSupplement material);

	/**
	 * Update exist crop material.
	 *
	 * @param material long
	 */
	void updateCropMaterialSupplement(@Param("material") CropMaterialSupplement material);

	/**
	 * Find all by registrationIds.
	 *
	 * @param registrationIds long[]
	 * @return a {@link List} object
	 */
	List<CropMaterialSupplement> findAllByRegistrationIds(@Param("registrationIds") long[] registrationIds);

	/**
	 * Find all by actionId.
	 *
	 * @param actionId long
	 * @param phase    String
	 * @return a {@link List} object
	 */
	List<CropMaterialSupplement> findAllCropMaterialSupplementByActionId(@Param("actionId") long actionId, @Param("phase") String phase);
}
