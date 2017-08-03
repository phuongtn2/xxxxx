package vn.azteam.tabasupport.modules.photo.service;

import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.photo.object.model.Photo;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.photo.service
 * Created 1/4/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface PhotoService {

	/**
	 * Upload photo
	 *
	 * @param photo a {@link Photo} object.
	 * @return a long
	 * @throws ValidationException
	 */
	long uploadPhoto(Photo photo) throws ValidationException;

	/**
	 * Get all photo
	 * @return a {@link List<Photo>} object.
	 */
	List<Photo> getAll();

	/**
	 * Get a photo by id
	 * @param photoId a long.
	 * @return a {@link Photo} object.
	 */
	Photo getPhotoById(long photoId);
}
