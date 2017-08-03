package vn.azteam.tabasupport.modules.cropsession.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.cropsession.rest
 * created 1/16/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@RestController
@RequestMapping("/crop-session")
public interface CropSessionApi {

	/**
	 * Add new crop session.
	 *
	 * @param auth   a {@link OAuth2Authentication} object
	 * @param mapper a {@link MultiValueMap} object
	 * @return a {@link Object}
	 * @throws ValidationException
	 */
	@PreAuthorize("hasGlobalPermission('CROP_SESSION','ADD_NEW')")
	@RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object addCropSession(OAuth2Authentication auth, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException;

	/**
	 * Get all crop.
	 *
	 * @param auth {@link OAuth2Authentication} object
	 * @return Object
	 */
	@PreAuthorize("hasPermission('CROP_SESSION','VIEW')")
	@RequestMapping(value = "/get", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object getAllCropSession(HttpServletRequest request, OAuth2Authentication auth);

	/**
	 * Get crop.
	 *
	 * @param request   {@link HttpServletRequest} object
	 * @param auth   {@link OAuth2Authentication} object
	 * @param cropId a {@link Long} cropId
	 * @return Object
	 * @throws NoSuchElementException
	 */
	@PreAuthorize("hasPermission('CROP_SESSION','VIEW')")
	@RequestMapping(value = "/get/{cropId:[0-9]+}", method = RequestMethod.GET, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object getCropSession(HttpServletRequest request, OAuth2Authentication auth, @PathVariable long cropId) throws NoSuchElementException;

	/**
	 * update exists crop session.
	 *
	 * @param auth   a {@link OAuth2Authentication} object
	 * @param cropId a {@link Long} cropId
	 * @param mapper a {@link MultiValueMap} object
	 * @return a {@link Object}
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	@PreAuthorize("hasGlobalPermission('CROP_SESSION','UPDATE')")
	@RequestMapping(value = "/update/{cropId:[0-9]+}", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object updateCropSession(OAuth2Authentication auth, @PathVariable long cropId, @RequestBody MultiValueMap<String, String> mapper) throws NoSuchElementException, ValidationException;
}
