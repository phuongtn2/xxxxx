package vn.azteam.tabasupport.modules.video.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import vn.azteam.tabasupport.core.object.exception.ValidationException;

import javax.servlet.http.HttpServletResponse;

/**
 * Package vn.azteam.tabasupport.modules.video.rest
 * Created 1/4/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@RestController
@RequestMapping("/video")
public interface VideoApi {

	/**
	 * Upload a video
	 *
	 * @param request a {@link MultipartHttpServletRequest} object.
	 * @param auth    a {@link OAuth2Authentication} object.
	 * @return a Object.
	 * @throws ValidationException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@PreAuthorize("hasPermission('VIDEO_MANAGER','UPLOAD')")
	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = {"multipart/mixed", "multipart/form-data"}, produces = {"application/json"})
	Object uploadVideo(MultipartHttpServletRequest request, OAuth2Authentication auth) throws ValidationException, IllegalAccessException, InstantiationException;

	/**
	 * Video a video
	 * @param response a {@link HttpServletResponse} object.
	 * @param auth a {@link OAuth2Authentication} object.
	 * @param videoId a long.
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('VIDEO_MANAGER','VIEW')")
	@RequestMapping(value = "/view/{videoId:[0-9]+}", method = RequestMethod.GET)
	void viewVideo(HttpServletResponse response, OAuth2Authentication auth, @PathVariable long videoId) throws ValidationException;
}
