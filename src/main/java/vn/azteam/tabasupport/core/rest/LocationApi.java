package vn.azteam.tabasupport.core.rest;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Package vn.azteam.tabasupport.core.rest
 * Created 2/9/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@RestController
@RequestMapping("/location")
public interface LocationApi {

	@RequestMapping(value = "province/get", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
	Object getAllProvince(OAuth2Authentication auth);

	@RequestMapping(value = "{provinceId:[0-9]+}/district/get", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
	Object getAllDistrictByProvinceId(OAuth2Authentication auth, @PathVariable long provinceId);

	@RequestMapping(value = "{districtId:[0-9]+}/ward/get", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
	Object getAllWardByDistrictId(OAuth2Authentication auth, @PathVariable long districtId);

	@RequestMapping(value = "district/get", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
	Object getAllDistrict(OAuth2Authentication auth);

	@RequestMapping(value = "ward/get", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
	Object getAllWard(OAuth2Authentication auth);

}
