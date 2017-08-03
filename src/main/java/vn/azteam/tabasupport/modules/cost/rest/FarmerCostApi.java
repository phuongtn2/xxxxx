package vn.azteam.tabasupport.modules.cost.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.cost.object.model.FarmerCost;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.cost.rest
 * Created 3/9/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@RestController
@RequestMapping("/cost")
public interface FarmerCostApi {

	@PreAuthorize("hasPermission('COST_MANAGER','ADD')")
	@RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object insertFarmerCost(OAuth2Authentication auth, @RequestBody List<FarmerCost> farmerCosts) throws ValidationException;

	@PreAuthorize("hasPermission('COST_MANAGER','UPDATE')")
	@RequestMapping(value = "/update", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object updateFarmerCost(OAuth2Authentication auth, @RequestBody List<FarmerCost> farmerCosts) throws ValidationException;

	@PreAuthorize("hasPermission('COST_MANAGER','VIEW')")
	@RequestMapping(value = "/get", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json;char-set=utf-8"})
	Object getAllFarmerCost(OAuth2Authentication auth, HttpServletRequest request, @RequestParam(value = "paging", defaultValue = "true") boolean isPaging);
}
