package vn.azteam.tabasupport.modules.video.service;

import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.video.object.model.Video;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.video.service
 * Created 1/4/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface VideoService {

	/**
	 * Upload a video
	 *
	 * @param video a {@link Video} object.
	 * @return a long
	 * @throws ValidationException
	 */
	long uploadVideo(Video video) throws ValidationException;

	/**
	 * Get all video
	 * @return a {@link List<Video>} object.
	 */
	List<Video> getAll();

	/**
	 * Get a video by id
	 * @param videoId a long
	 * @return a {@link Video} object.
	 */
	Video getVideoById(long videoId);
}
