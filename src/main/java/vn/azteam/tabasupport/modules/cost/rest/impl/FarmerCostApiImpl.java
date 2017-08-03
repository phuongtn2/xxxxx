package vn.azteam.tabasupport.modules.cost.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.cost.object.model.FarmerCost;
import vn.azteam.tabasupport.modules.cost.rest.FarmerCostApi;
import vn.azteam.tabasupport.modules.cost.service.FarmerCostService;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Package vn.azteam.tabasupport.modules.cost.rest.impl
 * Created 3/9/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see FarmerCostApi
 * @since 1.0.0
 */
@Component
public class FarmerCostApiImpl extends BaseRestImpl implements FarmerCostApi {

	@Autowired
	private FarmerCostService farmerCostService;
	@Autowired
	private CropSessionService cropSessionService;

	@Override
	public Object insertFarmerCost(OAuth2Authentication auth, @RequestBody List<FarmerCost> farmerCosts) throws ValidationException {
		Account account = (Account) auth.getPrincipal();
		for (FarmerCost farmerCost : farmerCosts) {
			farmerCost.setCompanyId(account.getCompanyId())
					.setCropId(cropSessionService.getCurrentCropSessionId());
			farmerCostService.insertFarmerCost(farmerCost);
		}
		return farmerCosts;
	}

	@Override
	public Object updateFarmerCost(OAuth2Authentication auth, @RequestBody List<FarmerCost> farmerCosts) throws ValidationException {
		Account account = (Account) auth.getPrincipal();
		Employee employee = account.getEmployee();
		for (FarmerCost farmerCost : farmerCosts) {
			// check existed farmer cost
			farmerCostService.getFarmerCostById(account.getCompanyId(), farmerCost.getId(), employee.getId());
			farmerCostService.updateFarmerCost(farmerCost);
		}
		return farmerCosts;
	}

	@Override
	public Object getAllFarmerCost(OAuth2Authentication auth, HttpServletRequest request, @RequestParam(value = "paging", defaultValue = "true") boolean isPaging) {
		Account account = (Account) auth.getPrincipal();
		Employee employee = account.getEmployee();
		final Map<String, String[]> paramsMapper = request.getParameterMap();

		List<FarmerCost> farmerCosts = farmerCostService.getAllFarmerCostByCompanyId(account.getCompanyId(), employee.getId());

		//filter
		long[] registrationIds;
		if (paramsMapper.containsKey("registrationId")) {
			String[] registrationId = paramsMapper.get("registrationId");
			registrationIds = StringUtil.convertArrayStringToLong(registrationId, 0);
			farmerCostService.filterFarmerCostByRegistrationId(farmerCosts, registrationIds);
		}

		if (paramsMapper.containsKey("title")) {
			String title = request.getParameter("title").toLowerCase();
			if (!StringUtil.isEmpty(title)) {
				farmerCostService.filterFarmerCostByTitle(farmerCosts, title);
			}
		}

		if (paramsMapper.containsKey("type")) {
			String type = request.getParameter("type").toLowerCase();
			if (!StringUtil.isEmpty(type)) {
				farmerCostService.filterFarmerCostByType(farmerCosts, type);
			}
		}

		if (paramsMapper.containsKey("actionType")) {
			String actionType = request.getParameter("actionType").toLowerCase();
			if (!StringUtil.isEmpty(actionType)) {
				farmerCostService.filterFarmerCostByActionType(farmerCosts, actionType);
			}
		}

		//paging
		if (paramsMapper.containsKey("paging")) {
			if (isPaging) {
				return paging(request, farmerCosts);
			} else {
				return farmerCosts;
			}
		}

		return paging(request, farmerCosts);
	}
}
