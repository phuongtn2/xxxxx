package vn.azteam.tabasupport.modules.pest.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Package vn.azteam.tabasupport.modules.pest.rest
 * Created 1/20/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@RestController
@RequestMapping("/pest")
public interface PestApi {

	@PreAuthorize("hasPermission('PEST_MANAGER', 'VIEW')")
	@RequestMapping(value = "/get", method = RequestMethod.GET, headers = "Accept=application/json;charset=utf-8", produces = {"application/json;charset=utf-8"})
	Object getAll(HttpServletRequest request, OAuth2Authentication auth, @RequestParam(value = "paging", defaultValue = "true") boolean isPaging);
}
