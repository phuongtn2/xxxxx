package vn.azteam.tabasupport.modules.cropsession.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.cropsession.object.model.Crop;

import java.util.List;

/**
 * package vn.azteam.tabasupport.modules.cropsession.object.dao
 * created 1/16/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface CropDao {
	/**
	 * Insert new crop session.
	 *
	 * @param crop a {@link Crop} object
	 */
	void insertCrop(@Param("crop") Crop crop);

	/**
	 * Find all crop session.
	 *
	 * @return a {@link List} object
	 */
	List<Crop> findAll();

	/**
	 * Update exists crop session.
	 *
	 * @param crop a {@link Crop} object
	 */
	void updateCrop(@Param("crop") Crop crop);
}