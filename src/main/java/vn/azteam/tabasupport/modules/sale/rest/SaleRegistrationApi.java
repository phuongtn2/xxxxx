package vn.azteam.tabasupport.modules.sale.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.sale.object.model.SaleRegistration;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.sale.rest
 * Created 2/9/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@RestController
@RequestMapping("/sale")
public interface SaleRegistrationApi {

	@PreAuthorize("hasPermission('SALE_MANAGER','VIEW')")
	@RequestMapping(value = "/sync", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json;charset=utf-8"})
	Object syncGetSaleRegistration(OAuth2Authentication auth);

	@PreAuthorize("hasPermission('SALE_MANAGER','UPDATE')")
	@RequestMapping(value = "/sync", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json;charset=utf-8"})
	Object syncPostSaleRegistration(OAuth2Authentication auth, @RequestBody List<SaleRegistration> saleRegistrations) throws ValidationException;

	@PreAuthorize("hasPermission('SALE_MANAGER','VIEW_MANAGER')")
	@RequestMapping(value = "/get/{registrationId:[0-9]+}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json;charset=utf-8"})
	Object syncGetSaleRegistration(OAuth2Authentication auth, @PathVariable long registrationId);
}
