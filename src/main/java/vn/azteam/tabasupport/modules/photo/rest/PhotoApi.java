package vn.azteam.tabasupport.modules.photo.rest;

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
 * Package vn.azteam.tabasupport.modules.photo.rest
 * Created 1/4/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@RestController
@RequestMapping("/photo")
public interface PhotoApi {

	/**
	 * Upload photo
	 *
	 * @param request a {@link MultipartHttpServletRequest} object.
	 * @param auth    a {@link OAuth2Authentication} object
	 * @return Object.
	 * @throws ValidationException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@PreAuthorize("hasPermission('PHOTO_MANAGER','UPLOAD')")
	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = {"multipart/mixed", "multipart/form-data"}, produces = {"application/json"})
	Object uploadPhoto(MultipartHttpServletRequest request, OAuth2Authentication auth) throws ValidationException, IllegalAccessException, InstantiationException;

	/**
	 * View Photo
	 * @param response a {@link HttpServletResponse} object.
	 * @param auth a {@link OAuth2Authentication} object
	 * @param photoId a long.
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('PHOTO_MANAGER','VIEW')")
	@RequestMapping(value = "/view/{photoId:[0-9]+}", method = RequestMethod.GET)
	void viewPhoto(HttpServletResponse response, OAuth2Authentication auth, @PathVariable long photoId) throws ValidationException;
}
