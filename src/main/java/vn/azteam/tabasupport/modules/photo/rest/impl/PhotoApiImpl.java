package vn.azteam.tabasupport.modules.photo.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.photo.object.model.Photo;
import vn.azteam.tabasupport.modules.photo.rest.PhotoApi;
import vn.azteam.tabasupport.modules.photo.service.PhotoService;

import javax.servlet.http.HttpServletResponse;

/**
 * Package vn.azteam.tabasupport.modules.photo.rest.impl
 * Created 1/4/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see vn.azteam.tabasupport.modules.photo.rest.PhotoApi
 * @since 1.0.0
 */
@Component
public class PhotoApiImpl extends BaseRestImpl implements PhotoApi {

	@Autowired
	private PhotoService photoService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object uploadPhoto(MultipartHttpServletRequest request, OAuth2Authentication auth) throws ValidationException, IllegalAccessException, InstantiationException {
		final Photo photo = Photo.newInstanceFromHttpServlet(Photo.class, request);
		photo.insertAuthor(auth);
		long photoId = photoService.uploadPhoto(photo);
		photo.setId(photoId);
		return photo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewPhoto(HttpServletResponse response, OAuth2Authentication auth, @PathVariable long photoId) throws ValidationException {
		final Photo photo = photoService.getPhotoById(photoId);
		responseToClient(response, photo);
	}
}
