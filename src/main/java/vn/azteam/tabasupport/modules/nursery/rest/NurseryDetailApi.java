package vn.azteam.tabasupport.modules.nursery.rest;

import org.springframework.beans.BeansException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import vn.azteam.tabasupport.core.object.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
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
public interface NurseryDetailApi {
	/**
	 * Add nursery detail.
	 *
	 * @param request        a {@link HttpServletRequest} object
	 * @param auth           a {@link OAuth2Authentication} object
	 * @param registrationId a {@link Long} registrationId
	 * @param nurseryId      a {@link Long} nurseryId
	 * @param action         a {@link String} action
	 * @param mapper         a {@link MultiValueMap} object
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 * @throws NoSuchRequestHandlingMethodException
	 */
	@PreAuthorize("hasPermission('CROP_NURSERY','ADD_NURSERY_DETAIL')")
	@RequestMapping(value = "/nursery/{registrationId:[0-9]+}/detail/{nurseryId:[0-9]+}/{action:[a-zA-Z-]+}/add",
			method = RequestMethod.POST,
			headers = "Accept=application/json",
			produces = {"application/json;char-set=utf-8"})
	Object addNurseryDetail(HttpServletRequest request, OAuth2Authentication auth,
	                        @PathVariable long registrationId,
	                        @PathVariable long nurseryId,
	                        @PathVariable String action,
	                        @RequestBody MultiValueMap<String, String> mapper)
			throws NullPointerException, BeansException, NoSuchElementException, ValidationException, NoSuchRequestHandlingMethodException;

	/**
	 * Update nursery detail.
	 *
	 * @param request        a {@link HttpServletRequest} object
	 * @param auth           a {@link OAuth2Authentication} object
	 * @param registrationId a {@link Long} registrationId
	 * @param nurseryId      a {@link Long} nurseryId
	 * @param detailId       a {@link Long} detailId
	 * @param action         a {@link String} action
	 * @param mapper         a {@link MultiValueMap} object
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 * @throws NoSuchRequestHandlingMethodException
	 */
	@PreAuthorize("hasPermission('CROP_NURSERY','UPDATE_NURSERY_DETAIL')")
	@RequestMapping(value = "/nursery/{registrationId:[0-9]+}/detail/{nurseryId:[0-9]+}/{action:[a-zA-Z-]+}/update/{detailId:[0-9]+}",
			method = RequestMethod.POST,
			headers = "Accept=application/json",
			produces = {"application/json;char-set=utf-8"})
	Object updateNurseryDetail(HttpServletRequest request, OAuth2Authentication auth,
	                           @PathVariable long registrationId, @PathVariable long nurseryId,
	                           @PathVariable String action, @PathVariable long detailId,
	                           @RequestBody MultiValueMap<String, String> mapper)
			throws NullPointerException, BeansException, NoSuchElementException, ValidationException, NoSuchRequestHandlingMethodException;

	/**
	 * Get nursery by id.
	 *
	 * @param request        a {@link HttpServletRequest} object
	 * @param auth           a {@link OAuth2Authentication} object
	 * @param registrationId a {@link Long} registrationId
	 * @param nurseryId      a {@link Long} nurseryId
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('CROP_NURSERY','VIEW_DETAIL')")
	@RequestMapping(value = "/nursery/{registrationId:[0-9]+}/detail/{nurseryId:[0-9]+}/get",
			method = RequestMethod.GET,
			headers = "Accept=application/json",
			produces = {"application/json;char-set=utf-8"})
	Object getNurseryDetails(HttpServletRequest request, OAuth2Authentication auth,
	                         @PathVariable long registrationId, @PathVariable long nurseryId)
			throws NullPointerException, BeansException, NoSuchElementException, ValidationException;

	/**
	 * Get nursery by id.
	 *
	 * @param request        a {@link HttpServletRequest} object
	 * @param auth           a {@link OAuth2Authentication} object
	 * @param registrationId a {@link Long} registrationId
	 * @param nurseryId      a {@link Long} nurseryId
	 * @param detailId       a {@link Long} detail
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('CROP_NURSERY','VIEW_DETAIL')")
	@RequestMapping(value = "/nursery/{registrationId:[0-9]+}/detail/{nurseryId:[0-9]+}/get/{detailId:[0-9]+}",
			method = RequestMethod.GET,
			headers = "Accept=application/json",
			produces = {"application/json;char-set=utf-8"})
	Object getNurseryDetail(HttpServletRequest request, OAuth2Authentication auth,
	                        @PathVariable long registrationId,
	                        @PathVariable long nurseryId, @PathVariable long detailId)
			throws NullPointerException, BeansException, NoSuchElementException, ValidationException;

	/**
	 * Client sync nursery detail.
	 *
	 * @param auth a {@link OAuth2Authentication} object
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('CROP_NURSERY','VIEW_DETAIL')")
	@RequestMapping(value = "/nursery/detail/sync",
			method = RequestMethod.GET,
			headers = "Accept=application/json",
			produces = {"application/json;char-set=utf-8"})
	Object clientSync(OAuth2Authentication auth) throws NullPointerException, BeansException, NoSuchElementException, ValidationException;
}
