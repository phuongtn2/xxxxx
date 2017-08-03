package vn.azteam.tabasupport.core.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Package vn.azteam.tabasupport.core.rest
 * Created 4/11/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@RestController
@RequestMapping("/company")
public interface CompanyApi {

	@PreAuthorize("hasPermission('EMPLOYEE_MANAGER','VIEW_COMPANY')")
	@RequestMapping(value = "/get", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
	Object getAllCompany(OAuth2Authentication auth);
}
