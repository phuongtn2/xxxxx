package vn.azteam.tabasupport.modules.report.service;

import vn.azteam.tabasupport.modules.report.object.model.cost.ActionDetailReport;
import vn.azteam.tabasupport.modules.report.object.model.cost.ActionReport;
import vn.azteam.tabasupport.modules.report.object.model.cost.CostFarmerDetailReport;
import vn.azteam.tabasupport.modules.report.object.model.cost.DataCostReport;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.report.service
 * Created 3/23/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface FarmerCostReportService {

	List<DataCostReport> getAllFarmerCostReport();

	List<DataCostReport> getAllFarmerCostReportByCompanyId(long companyId);

	List<CostFarmerDetailReport> getCostFarmerDetailReport();

	List<CostFarmerDetailReport> getCostFarmerDetailReportByCompanyId(long companyId);

	List<ActionReport> getAllActionReport(long companyId);

	List<ActionDetailReport> getAllActionDetailReport(long companyId);
}
