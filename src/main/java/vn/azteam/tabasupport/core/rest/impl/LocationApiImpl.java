package vn.azteam.tabasupport.core.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import vn.azteam.tabasupport.core.rest.LocationApi;
import vn.azteam.tabasupport.core.service.LocationService;

/**
 * Package vn.azteam.tabasupport.core.rest.impl
 * Created 2/9/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see LocationApi
 * @since 1.0.0
 */
@Component
public class LocationApiImpl extends BaseRestImpl implements LocationApi {

	@Autowired
	private LocationService locationService;

	@Override
	public Object getAllProvince(OAuth2Authentication auth) {
		return locationService.getAllProvinces();
	}

	@Override
	public Object getAllDistrictByProvinceId(OAuth2Authentication auth, @PathVariable long provinceId) {
		//check existed provinceId
		locationService.getProvinceById(provinceId);
		return locationService.getAllDistrictsByProvinceId(provinceId);
	}

	@Override
	public Object getAllWardByDistrictId(OAuth2Authentication auth, @PathVariable long districtId) {
		//check existed district
		locationService.getDistrictById(districtId);
		return locationService.getAllWardsByDistrictId(districtId);
	}

	@Override
	public Object getAllDistrict(OAuth2Authentication auth) {
		return locationService.getAllDistricts();
	}

	@Override
	public Object getAllWard(OAuth2Authentication auth) {
		return locationService.getAllWards();
	}
}
