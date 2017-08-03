package vn.azteam.tabasupport.core.rest;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.azteam.tabasupport.core.object.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;

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
public interface AccountAPI {

	/**
	 * @param request a {@link HttpServletRequest} object
	 * @return a object contain code
	 * @throws ValidationException
	 * @throws NoSuchElementException
	 */
	@RequestMapping(value = "/reset-password", method = RequestMethod.GET)
	Object getResetPassCode(HttpServletRequest request) throws ValidationException, NoSuchElementException;

	/**
	 * @param request a {@link HttpServletRequest} object
	 * @param mapper  a {@link MultiValueMap} object
	 * @return a {@link Object} message
	 * @throws ValidationException
	 * @throws NoSuchElementException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/password", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
	Object resetPassword(HttpServletRequest request, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException, NoSuchElementException, InvocationTargetException, IllegalAccessException;

	/**
	 * Revoke access token of current user.
	 *
	 * @param auth an {@link OAuth2Authentication} object
	 */
	@RequestMapping(value = "/oauth/token/revoke", method = RequestMethod.GET)
	Object revokeToken(HttpServletRequest request, OAuth2Authentication auth);
}
