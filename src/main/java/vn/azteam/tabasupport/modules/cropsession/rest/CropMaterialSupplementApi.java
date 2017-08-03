package vn.azteam.tabasupport.modules.cropsession.rest;

import org.springframework.beans.BeansException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropMaterialSupplement;

import java.util.List;
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
public interface CropMaterialSupplementApi {
	/**
	 * Add Crop material supplement.
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
	@RequestMapping(value = "/farmer/{registrationId:[0-9]+}/material/supplement/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object addMaterial(OAuth2Authentication auth, @PathVariable long registrationId, @RequestBody MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, NoSuchElementException, ValidationException;

	/**
	 * Add Crop material supplements.
	 *
	 * @param auth           a {@link OAuth2Authentication} object
	 * @param registrationId a {@link Long} registrationId
	 * @param materials      a {@link List} object
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('CROP_SESSION','ADD_MATERIAL')")
	@RequestMapping(value = "/farmer/{registrationId:[0-9]+}/material/supplement/adds", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object addMaterials(OAuth2Authentication auth, @PathVariable long registrationId, @RequestBody List<CropMaterialSupplement> materials) throws NullPointerException, BeansException, NoSuchElementException, ValidationException;

	/**
	 * Update exist Crop material supplement.
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
	@RequestMapping(value = "/farmer/{registrationId:[0-9]+}/material/supplement/update/{cropMaterialId:[0-9]+}", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object updateMaterial(OAuth2Authentication auth, @PathVariable long registrationId, @PathVariable long cropMaterialId, @RequestBody MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, NoSuchElementException, ValidationException;

	/**
	 * Update exist Crop material supplement.
	 *
	 * @param auth           a {@link OAuth2Authentication} object
	 * @param registrationId a {@link Long} registrationId
	 * @param materials      a {@link List} object
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('CROP_SESSION','UPDATE_MATERIAL')")
	@RequestMapping(value = "/farmer/{registrationId:[0-9]+}/material/supplement/updates", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object updateMaterials(OAuth2Authentication auth, @PathVariable long registrationId, @RequestBody List<CropMaterialSupplement> materials) throws NullPointerException, BeansException, NoSuchElementException, ValidationException;
}
