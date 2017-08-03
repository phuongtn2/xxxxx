package vn.azteam.tabasupport.modules.cropsession.rest;

import org.springframework.beans.BeansException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;

import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.cropsession.rest
 * created 1/19/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@RestController
@RequestMapping("/crop-session")
public interface CropDryingOvenApi {
	/**
	 * Add Drying Oven.
	 *
	 * @param auth           a {@link OAuth2Authentication} object
	 * @param registrationId a {@link Long} registrationId
	 * @param mapper         a {@link MultiValueMap} object
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('CROP_SESSION','ADD_DRYING_OVEN')")
	@RequestMapping(value = "/farmer/{registrationId:[0-9]+}/drying-oven/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object addDryingOven(OAuth2Authentication auth, @PathVariable long registrationId, @RequestBody MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, NoSuchElementException, ValidationException;

	/**
	 * Update exist Drying Oven.
	 *
	 * @param auth             a {@link OAuth2Authentication} object
	 * @param registrationId   a {@link Long} registrationId
	 * @param cropDryingOvenId a {@link Long} cropDryingOvenId
	 * @param mapper           a {@link MultiValueMap} object
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('CROP_SESSION','UPDATE_DRYING_OVEN')")
	@RequestMapping(value = "/farmer/{registrationId:[0-9]+}/drying-oven/update/{cropDryingOvenId:[0-9]+}", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object updateDryingOven(OAuth2Authentication auth, @PathVariable long registrationId, @PathVariable long cropDryingOvenId, @RequestBody MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, NoSuchElementException, ValidationException;
}
