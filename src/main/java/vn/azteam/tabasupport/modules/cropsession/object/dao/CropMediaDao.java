package vn.azteam.tabasupport.modules.cropsession.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropMedia;

import java.util.List;

/**
 * package vn.azteam.tabasupport.modules.cropsession.object.dao
 * created 2/16/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface CropMediaDao {
	/**
	 * Insert new crop media.
	 *
	 * @param media CropMedia object
	 */
	void insert(@Param("media") CropMedia media);

	/**
	 * Update exists crop media.
	 *
	 * @param media CropMedia object
	 */
	void update(@Param("media") CropMedia media);

	/**
	 * find all file of media.
	 *
	 * @param actionId long
	 * @param phase    string
	 * @param delFlag  int
	 * @return a {@link List} object
	 */
	List<CropMedia> findAllByAction(@Param("actionId") long actionId, @Param("phase") String phase, @Param("delFlag") int delFlag);
}
