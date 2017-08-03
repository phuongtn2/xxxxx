package vn.azteam.tabasupport.modules.farmer.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.SimpleResponse;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.farmer.object.model.Farmer;
import vn.azteam.tabasupport.modules.farmer.object.model.FarmerRelative;
import vn.azteam.tabasupport.modules.farmer.rest.FarmerRelativeAPI;
import vn.azteam.tabasupport.modules.farmer.service.FarmerService;

import javax.servlet.http.HttpServletRequest;

/**
 * package vn.azteam.tabasupport.modules.farmer.rest
 * created 3/10/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseRestImpl
 * @see FarmerRelativeAPI
 * @since 1.0.0
 */
@Component
public class FarmerRelativeAPIImpl extends BaseRestImpl implements FarmerRelativeAPI {
	@Autowired
	private FarmerService farmerService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object addRelative(OAuth2Authentication auth, @PathVariable("farmerId") long farmerId, @RequestBody FarmerRelative relative) throws ValidationException {
		Farmer farmer = farmerService.getFarmerById(farmerId);
		relative.setFarmerId(farmerId)
				.setFullName(String.format("%s %s", relative.getFirstName(), relative.getLastName()))
				.setProvinceId(farmer.getProvinceId())
				.setDistrictId(farmer.getDistrictId())
				.setWardId(farmer.getWardId())
				.setAddress(farmer.getAddress())
				.insertAuthor(auth);
		farmerService.createRelative(relative);
		return new SimpleResponse(new Object[]{relative});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object updateRelative(OAuth2Authentication auth, @PathVariable("farmerId") long farmerId, @RequestBody FarmerRelative relative) throws ValidationException {
		Farmer farmer = farmerService.getFarmerById(farmerId);
		relative.setFullName(String.format("%s %s", relative.getFirstName(), relative.getLastName()))
				.setProvinceId(farmer.getProvinceId())
				.setDistrictId(farmer.getDistrictId())
				.setWardId(farmer.getWardId())
				.setAddress(farmer.getAddress())
				.insertAuthor(auth);
		relative.setFarmerId(farmerId);
		farmerService.updateRelative(relative);
		return new SimpleResponse(new Object[]{relative});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object get(HttpServletRequest request,
	                  @PathVariable("farmerId") long farmerId,
	                  @RequestParam(name = "paging", defaultValue = "true") boolean isPaging) {
		return isPaging ? paging(request, farmerService.getAllByFarmerId(farmerId))
				: farmerService.getAllByFarmerId(farmerId);
	}
}
