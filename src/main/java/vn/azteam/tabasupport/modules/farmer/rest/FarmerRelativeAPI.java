package vn.azteam.tabasupport.modules.farmer.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.farmer.object.model.FarmerRelative;

import javax.servlet.http.HttpServletRequest;

/**
 * package vn.azteam.tabasupport.modules.farmer.rest
 * created 3/10/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@RestController
@RequestMapping("/farmer")
public interface FarmerRelativeAPI {
	@PreAuthorize("hasPermission('FARMER_MANAGER','ADD_FARMER_RELATIVE')")
	@RequestMapping(value = "/{farmerId:[0-9]+}/relative/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
	Object addRelative(OAuth2Authentication auth,
	                   @PathVariable("farmerId") long farmerId,
	                   @RequestBody FarmerRelative relative) throws ValidationException;

	@PreAuthorize("hasPermission('FARMER_MANAGER','UPDATE_FARMER_RELATIVE')")
	@RequestMapping(value = "/{farmerId:[0-9]+}/relative/update", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
	Object updateRelative(OAuth2Authentication auth,
	                      @PathVariable("farmerId") long farmerId,
	                      @RequestBody FarmerRelative relative) throws ValidationException;

	@PreAuthorize("hasPermission('FARMER_MANAGER','VIEW_FARMER_RELATIVE')")
	@RequestMapping(value = "{farmerId:[0-9]+}/relative/get", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
	Object get(HttpServletRequest request,
	           @PathVariable("farmerId") long farmerId,
	           @RequestParam(name = "paging", defaultValue = "true") boolean isPaging);
}
