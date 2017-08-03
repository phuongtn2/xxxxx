package vn.azteam.tabasupport.modules.farmer.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.SimpleResponse;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.farmer.object.model.Farmer;
import vn.azteam.tabasupport.modules.farmer.rest.FarmerApi;
import vn.azteam.tabasupport.modules.farmer.service.FarmerService;
import vn.azteam.tabasupport.modules.user.service.UserService;
import vn.azteam.tabasupport.util.PropertiesParserUtil;
import vn.azteam.tabasupport.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Package vn.azteam.tabasupport.modules.farmer.rest.impl
 * Created 12/26/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@Component
public class FarmerApiImpl extends BaseRestImpl implements FarmerApi {

	static final String MODULE_ID = "farmer_manager";

	@Autowired
	private UserService userService;

	@Autowired
	private FarmerService farmerService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object addFarmer(OAuth2Authentication auth, @RequestBody MultiValueMap<String, String> farmerMap)
			throws ValidationException {
		final Account account = (Account) auth.getPrincipal();
		final Farmer farmer = new Farmer();
		farmer.copyPropertiesFromMapper(farmerMap);
		farmer.setCompanyId(account.getCompanyId());
		farmer.setFullName(String.format("%s %s", farmer.getFirstName(), farmer.getLastName())).insertAuthor(auth);
		farmer.setIdentityCard(farmer.getFarmerCode());
		long userId = farmerService.createFarmer(farmer);
		farmer.setId(userId);
		return new SimpleResponse(new Object[]{farmer});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object updateFarmer(HttpServletRequest request, OAuth2Authentication auth, @PathVariable long farmerId, @RequestBody MultiValueMap<String, String> farmerMap)
			throws ValidationException {
		final Account account = (Account) auth.getPrincipal();
		final Farmer farmer = farmerService.getFarmerById(farmerId);

		if (farmer == null || account.getCompanyId() != farmer.getCompanyId()) {
			throw new AccessDeniedException(PropertiesParserUtil.parsePropertyByFormat("security.access.denied.default.msg", "another company"));
		}
		farmer.copyPropertiesFromMapper(farmerMap, "id", "uId");
		farmer.setFullName(String.format("%s %s", farmer.getFirstName(), farmer.getLastName())).insertAuthor(auth);
		farmerService.updateFarmer(farmer);

		return new SimpleResponse(new Object[]{farmer});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getAllFarmer(HttpServletRequest request, OAuth2Authentication auth) {
		final Map<String, String[]> paramsMapper = request.getParameterMap();
		final Account account = (Account) auth.getPrincipal();

		long[] companyId = new long[]{0, 0};
		companyId[0] = account.getCompanyId();
		final List<Farmer> farmerList = farmerService.getAllFarmer();

		if (account.hasGlobalPermission("FARMER_MANAGER", "VIEW")) {
			if (paramsMapper.containsKey("companyId")) {
				String companyIdString = request.getParameter("companyId");
				companyId[1] = StringUtil.convertStringToLong(companyIdString, 0);
			}
		}

		userService.filterByCompanyIds(farmerList, companyId);

		// filter result
		paramsMapper.forEach((param, values) -> {
			switch (param) {
				case "fullName":
					if (values.length == 1)
						farmerService.filterFarmerByFullName(farmerList, values[0].toLowerCase());
					break;
				case "phone":
					if (values.length == 1)
						farmerService.filterFarmerByPhone(farmerList, values[0]);
					break;
				case "provinceId":
					if (values.length == 1)
						farmerService.filterFarmerByProvinceId(farmerList, StringUtil.convertStringToLong(values[0], 0));
					break;
				case "districtId":
					if (values.length == 1)
						farmerService.filterFarmerByDistrictId(farmerList, StringUtil.convertStringToLong(values[0], 0));
					break;
				case "wardId":
					if (values.length == 1)
						farmerService.filterFarmerByWardId(farmerList, StringUtil.convertStringToLong(values[0], 0));
					break;
				default:
					break;
			}
		});

		//paging
		return paging(request, farmerList);
	}

	@Override
	public Object getAllFarmer(OAuth2Authentication auth) {
		final Account account = (Account) auth.getPrincipal();
		long[] companyId = new long[]{account.getCompanyId()};

		final List<Farmer> farmerList = farmerService.getAllFarmer();

		//Get all farmer of 1 company
		userService.filterByCompanyIds(farmerList, companyId);
		return farmerList;
	}
}
