package vn.azteam.tabasupport.modules.video.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.video.object.model.Video;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.video.object.dao
 * Created 1/4/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface VideoDao {

	/**
	 * Insert a video
	 *
	 * @param video a {@link Video} object.
	 * @return a long.
	 */
	long insertVideo(@Param("video") Video video);

	/**
	 * Get all Video
	 * @return a {@link List<Video>} object.
	 */
	List<Video> findAll();
}
