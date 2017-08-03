package vn.azteam.tabasupport.modules.nursery.rest;

import org.springframework.beans.BeansException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.nursery.object.model.Nursery;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.nursery.rest
 * created 1/20/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@RestController
@RequestMapping("/crop-session")
public interface NurseryApi {
	/**
	 * Add nursery.
	 *
	 * @param auth           a {@link OAuth2Authentication} object
	 * @param registrationId a {@link Long} registrationId
	 * @param nursery        a {@link Nursery} object
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('CROP_NURSERY','ADD_NURSERY')")
	@RequestMapping(value = "/nursery/{registrationId:[0-9]+}/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object addNursery(OAuth2Authentication auth, @PathVariable long registrationId, @RequestBody Nursery nursery) throws NullPointerException, BeansException, NoSuchElementException, ValidationException;

	/**
	 * Update nursery.
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
	@PreAuthorize("hasPermission('CROP_NURSERY','UPDATE_NURSERY')")
	@RequestMapping(value = "/nursery/{registrationId:[0-9]+}/update", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object updateNursery(OAuth2Authentication auth, @PathVariable long registrationId, @RequestBody MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, NoSuchElementException, ValidationException;


	/**
	 * Get nursery by id.
	 *
	 * @param auth           a {@link OAuth2Authentication} object
	 * @param registrationId a {@link Long} registrationId
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('CROP_NURSERY','VIEW')")
	@RequestMapping(value = "/nursery/{registrationId:[0-9]+}/get/{nurseryId:[0-9]+}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object getNursery(OAuth2Authentication auth, @PathVariable long registrationId, @PathVariable long nurseryId) throws NullPointerException, BeansException, NoSuchElementException, ValidationException;

	/**
	 * Client sync nursery.
	 *
	 * @param auth a {@link OAuth2Authentication} object
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('CROP_NURSERY','VIEW')")
	@RequestMapping(value = "/nursery/sync", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object clientSync(OAuth2Authentication auth) throws NullPointerException, BeansException, NoSuchElementException, ValidationException;

	/**
	 * Server sync nursery.
	 *
	 * @param auth      a {@link OAuth2Authentication} object
	 * @param nurseries a {@link Nursery} object
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('CROP_NURSERY','UPDATE_NURSERY')")
	@RequestMapping(value = "/nursery/sync", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object serverSync(OAuth2Authentication auth, @RequestBody List<Nursery> nurseries) throws NullPointerException, BeansException, NoSuchElementException, ValidationException;
}
