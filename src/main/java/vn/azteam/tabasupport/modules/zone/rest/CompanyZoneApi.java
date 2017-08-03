package vn.azteam.tabasupport.modules.zone.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.azteam.tabasupport.modules.zone.object.model.CompanyZone;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.zone.rest
 * Created 3/6/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@RestController
@RequestMapping("/zone")
public interface CompanyZoneApi {

	/**
	 * Get all zone by companyId
	 *
	 * @param auth a {@link OAuth2Authentication} object
	 * @return a {@link List <  CompanyZone  >} objects.
	 */
	@PreAuthorize("hasPermission('ZONE_MANAGER','VIEW')")
	@RequestMapping(value = "/sync", method = RequestMethod.GET, headers = "Accept=application/json;charset=utf-8", produces = {"application/json;charset=utf-8"})
	Object getAllZoneByCompanyId(OAuth2Authentication auth);
}
