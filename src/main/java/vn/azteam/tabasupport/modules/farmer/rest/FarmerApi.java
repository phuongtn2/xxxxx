package vn.azteam.tabasupport.modules.farmer.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.farmer.object.model.Farmer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.farmer.rest
 * Created 12/26/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@RestController
@RequestMapping("/farmer")
public interface FarmerApi {

	/**
	 * Insert a farmer
	 * @param auth {@link OAuth2Authentication} object
	 * @param farmerMap a {@link Farmer} object
	 * @return a {@link Farmer} object
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('FARMER_MANAGER','ADD_NEW')")
	@RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
	Object addFarmer(OAuth2Authentication auth, @RequestBody MultiValueMap<String, String> farmerMap)
			throws ValidationException;

	/**
	 * Update a farmer
	 * @param request a {@link HttpServletRequest} object
	 * @param auth {@link OAuth2Authentication} object
	 * @param farmerId a long
	 * @param farmerMap a {@link Farmer} object
	 * @return
	 * @throws ValidationException
	 */
	@PreAuthorize("hasPermission('FARMER_MANAGER','UPDATE')")
	@RequestMapping(value = "/update/{farmerId:[0-9]+}", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
	Object updateFarmer(HttpServletRequest request, OAuth2Authentication auth, @PathVariable long farmerId, @RequestBody MultiValueMap<String, String> farmerMap)
			throws ValidationException;

	/**
	 * Get all farmer
	 * @param request a {@link HttpServletRequest} object
	 * @param auth {@link OAuth2Authentication} object
	 * @return a {@link List<Farmer>} object
	 */
	@PreAuthorize("hasPermission('FARMER_MANAGER','VIEW')")
	@RequestMapping(value = "/get", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
	Object getAllFarmer(HttpServletRequest request, OAuth2Authentication auth);

	@PreAuthorize("hasPermission('FARMER_MANAGER','VIEW')")
	@RequestMapping(value = "/sync", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
	Object getAllFarmer(OAuth2Authentication auth);
}
