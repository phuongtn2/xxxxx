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
 * created 1/17/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@RestController
@RequestMapping("/crop-session")
public interface CropMaterialApi {
	/**
	 * Add Crop material
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
	@PreAuthorize("hasPermission('CROP_SESSION','ADD_MATERIAL')")
	@RequestMapping(value = "/farmer/{registrationId:[0-9]+}/material/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object addMaterial(OAuth2Authentication auth, @PathVariable long registrationId, @RequestBody MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, NoSuchElementException, ValidationException;

	/**
	 * Update exitst Crop material.
	 *
	 * @param auth           a {@link OAuth2Authentication} object
	 * @param registrationId a {@link Long} registrationId
	 * @param cropMaterialId a {@link Long} cropMaterialId
	 * @param mapper         a {@link MultiValueMap} object
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('CROP_SESSION','UPDATE_MATERIAL')")
	@RequestMapping(value = "/farmer/{registrationId:[0-9]+}/material/update/{cropMaterialId:[0-9]+}", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object updateMaterial(OAuth2Authentication auth, @PathVariable long registrationId, @PathVariable long cropMaterialId, @RequestBody MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, NoSuchElementException, ValidationException;
}
