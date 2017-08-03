package vn.azteam.tabasupport.core.rest;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.azteam.tabasupport.core.object.exception.ValidationException;

import java.lang.reflect.InvocationTargetException;

/**
 * package vn.azteam.tabasupport.core.rest
 * created 12/26/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@RestController
@RequestMapping("/me")
public interface MeApi {
	/**
	 * Get current login user info
	 *
	 * @param auth a {@link OAuth2Authentication} object
	 * @return a {@link Object}
	 */
	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
	Object getCurrentUser(OAuth2Authentication auth);

	/**
	 * Get current login user info
	 *
	 * @param auth a {@link OAuth2Authentication} object
	 * @return a {@link Object}
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws ValidationException
	 * @throws InstantiationException
	 */
	@RequestMapping(value = "/update-info", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
	Object updateCurrentUser(OAuth2Authentication auth, @RequestBody MultiValueMap<String, String> mapper) throws InvocationTargetException, IllegalAccessException, ValidationException, InstantiationException;

	/**
	 * change password
	 *
	 * @param auth a {@link OAuth2Authentication} object
	 * @return a {@link Object}
	 * @throws ValidationException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/change-password", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
	Object changePassword(OAuth2Authentication auth, @RequestBody MultiValueMap<String, String> mapper) throws InvocationTargetException, IllegalAccessException, ValidationException;
}
