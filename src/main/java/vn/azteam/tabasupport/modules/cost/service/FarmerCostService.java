package vn.azteam.tabasupport.modules.cost.service;

import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.cost.object.model.FarmerCost;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.cost.service
 * Created 3/9/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface FarmerCostService {

	FarmerCost insertFarmerCost(FarmerCost farmerCost) throws ValidationException;

	FarmerCost updateFarmerCost(FarmerCost farmerCost) throws ValidationException;

	List<FarmerCost> getAllFarmerCostByCompanyId(long companyId, long employeeId);

	FarmerCost getFarmerCostById(long companyId, long farmerCostId, long employeeId);

	void filterFarmerCostByRegistrationId(List<FarmerCost> farmerCosts, long[] registrationIds);

	void filterFarmerCostByTitle(List<FarmerCost> farmerCosts, String title);

	void filterFarmerCostByType(List<FarmerCost> farmerCosts, String type);

	void filterFarmerCostByActionType(List<FarmerCost> farmerCosts, String actionType);
}
