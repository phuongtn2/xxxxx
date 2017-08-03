package vn.azteam.tabasupport.modules.cultivation.rest;

import org.springframework.beans.BeansException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.cultivation.object.model.Cultivation;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.rest
 * Created 1/19/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@RestController
@RequestMapping("/cultivation")
public interface CultivationApi {

	@PreAuthorize("hasPermission('CULTIVATION_MANAGER','ADD_NEW')")
	@RequestMapping(value = "{registrationId:[0-9]+}/add", method = RequestMethod.POST, headers = "Accept=application/json;charset=utf-8", produces = {"application/json;charset=utf-8"})
	Object addCultivation(OAuth2Authentication auth, @PathVariable long registrationId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException;

	@PreAuthorize("hasPermission('CULTIVATION_MANAGER','UPDATE')")
	@RequestMapping(value = "{registrationId:[0-9]+}/{cultivationId:[0-9]+}/update", method = RequestMethod.POST, headers = "Accept=application/json;charset=utf-8", produces = {"application/json;charset=utf-8"})
	Object updateCultivation(OAuth2Authentication auth, @PathVariable long registrationId, @PathVariable long cultivationId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException;

	@PreAuthorize("hasPermission('CULTIVATION_MANAGER','UPDATE')")
	@RequestMapping(value = "{registrationId:[0-9]+}/{cultivationId:[0-9]+}/field-plot/add", method = RequestMethod.POST, headers = "Accept=application/json;charset=utf-8", produces = {"application/json;charset=utf-8"})
	Object addActionPlanting(OAuth2Authentication auth, @PathVariable long registrationId, @PathVariable long cultivationId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException;

	/**
	 * get cultivation by Id.
	 *
	 * @param auth          a {@link OAuth2Authentication} object
	 * @param cultivationId long
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws NoSuchElementException
	 */
	@PreAuthorize("hasPermission('CULTIVATION_MANAGER','VIEW')")
	@RequestMapping(value = "{registrationId:[0-9]+}/get/{cultivationId:[0-9]+}", method = RequestMethod.GET, produces = {"application/json;char-set=utf-8"})
	Object get(OAuth2Authentication auth, @PathVariable("registrationId") String registrationId, @PathVariable("cultivationId") long cultivationId) throws NullPointerException, BeansException, NoSuchElementException;

	/**
	 * Client sync cultivation.
	 *
	 * @param auth a {@link OAuth2Authentication} object
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('CULTIVATION_MANAGER','VIEW')")
	@RequestMapping(value = "/sync", method = RequestMethod.GET, produces = {"application/json;char-set=utf-8"})
	Object clientSync(OAuth2Authentication auth) throws NullPointerException, BeansException, NoSuchElementException, ValidationException;

	/**
	 * Server sync cultivation.
	 *
	 * @param auth         a {@link OAuth2Authentication} object
	 * @param cultivations a {@link List} object
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('CULTIVATION_MANAGER','UPDATE')")
	@RequestMapping(value = "/sync", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object serverSync(OAuth2Authentication auth, @RequestBody List<Cultivation> cultivations) throws NullPointerException, BeansException, NoSuchElementException, ValidationException;
}
