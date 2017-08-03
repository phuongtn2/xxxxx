package vn.azteam.tabasupport.modules.cost.service.impl;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.cost.object.dao.FarmerCostDao;
import vn.azteam.tabasupport.modules.cost.object.model.FarmerCost;
import vn.azteam.tabasupport.modules.cost.service.FarmerCostService;

import java.util.Arrays;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.cost.service.impl
 * Created 3/9/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see FarmerCostService
 * @since 1.0.0
 */
@Service
public class FarmerCostServiceImpl extends BaseServiceImpl implements FarmerCostService {

	@Autowired
	private FarmerCostDao farmerCostDao;

	@Override
	public FarmerCost insertFarmerCost(FarmerCost farmerCost) throws ValidationException {
		final List<ObjectError> errors = farmerCost.getErrors();
		if (errors.isEmpty()) {
			farmerCostDao.insertFarmerCost(farmerCost);
			return farmerCost;
		} else {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
	}

	@Override
	public FarmerCost updateFarmerCost(FarmerCost farmerCost) throws ValidationException {
		final List<ObjectError> errors = farmerCost.getErrors();
		if (errors.isEmpty()) {
			farmerCostDao.updateFarmerCost(farmerCost);
			return farmerCost;
		} else {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
	}

	@Override
	public List<FarmerCost> getAllFarmerCostByCompanyId(long companyId, long employeeId) {
		List<FarmerCost> farmerCosts = farmerCostDao.findAllFarmerCostByCompanyId(companyId, employeeId);
		return farmerCosts;
	}

	@Override
	public FarmerCost getFarmerCostById(long companyId, long farmerCostId, long employeeId) {
		return getAllFarmerCostByCompanyId(companyId, employeeId).stream().filter(
				farmerCost -> farmerCost.getId() == farmerCostId
		).findFirst().get();
	}

	@Override
	public void filterFarmerCostByRegistrationId(List<FarmerCost> farmerCosts, long[] registrationIds) {
		farmerCosts.removeIf(
				farmerCost -> !Arrays.asList(ArrayUtils.toObject(registrationIds)).contains(farmerCost.getRegistrationId())
		);
	}

	@Override
	public void filterFarmerCostByTitle(List<FarmerCost> farmerCosts, String title) {
		farmerCosts.removeIf(
				farmerCost -> !farmerCost.getTitle().toLowerCase().matches(String.format(".*%s.*", title).toString())
		);
	}

	@Override
	public void filterFarmerCostByType(List<FarmerCost> farmerCosts, String type) {
		farmerCosts.removeIf(
				farmerCost -> !farmerCost.getType().equalsIgnoreCase(type)
		);
	}

	@Override
	public void filterFarmerCostByActionType(List<FarmerCost> farmerCosts, String actionType) {
		farmerCosts.removeIf(
				farmerCost -> !farmerCost.getActionType().equalsIgnoreCase(actionType)
		);
	}
}
