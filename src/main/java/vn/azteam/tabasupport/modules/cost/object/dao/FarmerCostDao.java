package vn.azteam.tabasupport.modules.cost.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.cost.object.model.FarmerCost;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.cost.object.dao
 * Created 3/9/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface FarmerCostDao {

	long insertFarmerCost(@Param("farmerCost") FarmerCost farmerCost);

	void updateFarmerCost(@Param("farmerCost") FarmerCost farmerCost);

	List<FarmerCost> findAllFarmerCostByCompanyId(@Param("companyId") long companyId, @Param("employeeId") long employeeId);
}
