package vn.azteam.tabasupport.modules.video.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.video.object.model.Video;
import vn.azteam.tabasupport.modules.video.rest.VideoApi;
import vn.azteam.tabasupport.modules.video.service.VideoService;

import javax.servlet.http.HttpServletResponse;

/**
 * Package vn.azteam.tabasupport.modules.video.rest.impl
 * Created 1/4/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see VideoApi
 * @since 1.0.0
 */
@Component
public class VideoApiImpl extends BaseRestImpl implements VideoApi {

	@Autowired
	private VideoService videoService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object uploadVideo(MultipartHttpServletRequest request, OAuth2Authentication auth) throws ValidationException, IllegalAccessException, InstantiationException {
		final Video video = Video.newInstanceFromHttpServlet(Video.class, request);
		video.insertAuthor(auth);
		long videoId = videoService.uploadVideo(video);
		video.setId(videoId);
		return video;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewVideo(HttpServletResponse response, OAuth2Authentication auth, @PathVariable long videoId) throws ValidationException {
		final Video video = videoService.getVideoById(videoId);
		responseToClient(response, video);
	}
}
