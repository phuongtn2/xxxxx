package vn.azteam.tabasupport.modules.cropsession.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropRegistration;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
public interface CropRegistrationApi {
	/**
	 * Register farmer to crops.
	 *
	 * @param auth   an {@link OAuth2Authentication} object
	 * @param mapper a {@link MultiValueMap} object
	 * @return a {@link Object}
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('CROP_SESSION','REGISTER_FARMER') and hasDivisionManagerPermission(#auth)")
	@RequestMapping(value = "/farmer/register", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object registerFarmer(OAuth2Authentication auth, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException;

	/**
	 * Update farmer in crops.
	 *
	 * @param auth   an {@link OAuth2Authentication} object
	 * @param mapper a {@link MultiValueMap} object
	 * @return a {@link Object}
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('CROP_SESSION','UPDATE_FARMER') and hasDivisionManagerPermission(#auth)")
	@RequestMapping(value = "/farmer/update/{registrationId:[0-9]+}", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object updateRegisterFarmer(OAuth2Authentication auth, @PathVariable long registrationId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException;

	/**
	 * Get all crops registration.
	 *
	 * @param request an {@link HttpServletRequest} object
	 * @param auth    an {@link OAuth2Authentication} object
	 * @return a {@link Object}
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('CROP_SESSION','VIEW')")
	@RequestMapping(value = "/farmer/get", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object getAllRegisterFarmer(HttpServletRequest request, OAuth2Authentication auth) throws ValidationException;

	/**
	 * Get crops registration by id.
	 *
	 * @param auth an {@link OAuth2Authentication} object
	 * @return a {@link Object}
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('CROP_SESSION','VIEW')")
	@RequestMapping(value = "/farmer/get/{registrationId:[0-9]+}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object getRegisterFarmer(OAuth2Authentication auth, @PathVariable long registrationId) throws ValidationException;

	/**
	 * Sync client side all crops registration.
	 *
	 * @param auth an {@link OAuth2Authentication} object
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws NoSuchElementException
	 */
	@PreAuthorize("hasPermission('CROP_SESSION','VIEW')")
	@RequestMapping(value = "/farmer/sync", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object clientSyncRegistrationFarmer(OAuth2Authentication auth) throws NullPointerException, NoSuchElementException;

	/**
	 * Sync server side all crops registration.
	 *
	 * @param auth an {@link OAuth2Authentication} object
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('CROP_SESSION','UPDATE_FARMER')")
	@RequestMapping(value = "/farmer/sync", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object serverSyncRegistrationFarmer(OAuth2Authentication auth, @RequestBody List<CropRegistration> registrations) throws NullPointerException, NoSuchElementException, ValidationException;
}
