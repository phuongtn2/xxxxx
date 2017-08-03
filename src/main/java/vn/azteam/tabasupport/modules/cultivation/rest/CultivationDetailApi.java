package vn.azteam.tabasupport.modules.cultivation.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.rest
 * Created 1/17/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@RestController
@RequestMapping("/cultivation")
public interface CultivationDetailApi {

	@PreAuthorize("hasPermission('CULTIVATION_MANAGER','ADD_NEW')")
	@RequestMapping(value = "/{fieldPlotId:[0-9]+}/manuring/add", method = RequestMethod.POST, headers = "Accept=application/json;charset=utf-8", produces = {"application/json;charset=utf-8"})
	Object addActionManuring(OAuth2Authentication auth, @PathVariable long fieldPlotId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException;

	@PreAuthorize("hasPermission('CULTIVATION_MANAGER','ADD_NEW')")
	@RequestMapping(value = "/{fieldPlotId:[0-9]+}/irrigation/add", method = RequestMethod.POST, headers = "Accept=application/json;charset=utf-8", produces = {"application/json;charset=utf-8"})
	Object addActionIrrigation(OAuth2Authentication auth, @PathVariable long fieldPlotId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException;

	@PreAuthorize("hasPermission('CULTIVATION_MANAGER','ADD_NEW')")
	@RequestMapping(value = "/{fieldPlotId:[0-9]+}/clover-cutting/add", method = RequestMethod.POST, headers = "Accept=application/json;charset=utf-8", produces = {"application/json;charset=utf-8"})
	Object addActionCloverCutting(OAuth2Authentication auth, @PathVariable long fieldPlotId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException;

	@PreAuthorize("hasPermission('CULTIVATION_MANAGER','ADD_NEW')")
	@RequestMapping(value = "/{fieldPlotId:[0-9]+}/pest-update/add", method = RequestMethod.POST, headers = "Accept=application/json;charset=utf-8", produces = {"application/json;charset=utf-8"})
	Object addActionPestUpdate(OAuth2Authentication auth, @PathVariable long fieldPlotId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException;

	@PreAuthorize("hasPermission('CULTIVATION_MANAGER','ADD_NEW')")
	@RequestMapping(value = "/{fieldPlotId:[0-9]+}/pesticide-spraying/add", method = RequestMethod.POST, headers = "Accept=application/json;charset=utf-8", produces = {"application/json;charset=utf-8"})
	Object addActionPesticideSpraying(OAuth2Authentication auth, @PathVariable long fieldPlotId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException;

	@PreAuthorize("hasPermission('CULTIVATION_MANAGER','ADD_NEW')")
	@RequestMapping(value = "/{fieldPlotId:[0-9]+}/harvest/add", method = RequestMethod.POST, headers = "Accept=application/json;charset=utf-8", produces = {"application/json;charset=utf-8"})
	Object addActionHarvest(OAuth2Authentication auth, @PathVariable long fieldPlotId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException;
}
