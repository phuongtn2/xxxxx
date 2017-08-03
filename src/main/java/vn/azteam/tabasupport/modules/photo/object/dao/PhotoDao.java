package vn.azteam.tabasupport.modules.photo.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.photo.object.model.Photo;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.photo.object.dao
 * Created 1/4/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface PhotoDao {

	/**
	 * Insert Photo
	 *
	 * @param photo a {@link Photo} object.
	 * @return a long
	 */
	long insertPhoto(@Param("photo") Photo photo);

	/**
	 * Get all photo
	 * @return a {@link List<Photo>} object.
	 */
	List<Photo> findAll();
}
