package vn.azteam.tabasupport.modules.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.report.object.dao.ExportDao;
import vn.azteam.tabasupport.modules.report.object.model.cost.ActionDetailReport;
import vn.azteam.tabasupport.modules.report.object.model.cost.ActionReport;
import vn.azteam.tabasupport.modules.report.object.model.cost.CostFarmerDetailReport;
import vn.azteam.tabasupport.modules.report.object.model.cost.DataCostReport;
import vn.azteam.tabasupport.modules.report.service.FarmerCostReportService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Package vn.azteam.tabasupport.modules.report.service.impl
 * Created 3/23/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@Service
public class FarmerCostReportServiceImpl extends BaseServiceImpl implements FarmerCostReportService {

	@Autowired
	private ExportDao exportDao;

	@Override
	public List<DataCostReport> getAllFarmerCostReport() {
		return exportDao.findAllDataCostReport();
	}

	@Override
	public List<DataCostReport> getAllFarmerCostReportByCompanyId(long companyId) {
		return getAllFarmerCostReport().stream().filter(
				farmerCostReport -> farmerCostReport.getCompanyId() == companyId
		).collect(Collectors.toList());
	}

	@Override
	public List<CostFarmerDetailReport> getCostFarmerDetailReport() {
		return exportDao.findAllCostFarmerDetailReport();
	}

	@Override
	public List<CostFarmerDetailReport> getCostFarmerDetailReportByCompanyId(long companyId) {
		return getCostFarmerDetailReport().stream().filter(
				costFarmerDetailReport -> costFarmerDetailReport.getCompanyId() == companyId
		).collect(Collectors.toList());
	}

	@Override
	public List<ActionReport> getAllActionReport(long companyId) {
		List<DataCostReport> dataCostReports = getAllFarmerCostReportByCompanyId(companyId);

		List<CostFarmerDetailReport> costFarmerDetailReports = getCostFarmerDetailReportByCompanyId(companyId);

		List<ActionReport> actionReports = new ArrayList<>();

		for (CostFarmerDetailReport costFarmerDetailReport : costFarmerDetailReports) {
			List<ActionDetailReport> actionDetailReports = getAllActionDetailReport(companyId);

			ActionReport actionReport = new ActionReport();
			actionReport.setTitle(costFarmerDetailReport.getTitle());
			actionReport.setType(costFarmerDetailReport.getType());
			actionReport.setActionType(costFarmerDetailReport.getActionType());
			actionReport.setCompanyId(costFarmerDetailReport.getCompanyId());
			actionReport.setCompanyName(costFarmerDetailReport.getCompanyName());
			actionReport.setCropId(costFarmerDetailReport.getCropId());
			actionReport.setCropName(costFarmerDetailReport.getCropName());

			for (DataCostReport dataCostReport : dataCostReports) {
				for (ActionDetailReport actionDetailReport : actionDetailReports) {
					if (actionDetailReport.getEmployeeId() == dataCostReport.getEmployeeId()
							&& actionDetailReport.getFarmerId() == dataCostReport.getFarmerId()
							&& costFarmerDetailReport.getTitle().equalsIgnoreCase(dataCostReport.getTitle())) {
						actionDetailReport.setActionId(dataCostReport.getActionId());
						actionDetailReport.setQuantity(dataCostReport.getQuantity());
						actionDetailReport.setPrice(dataCostReport.getPrice());
					}
				}
			}

			actionReport.setActionDetailReports(actionDetailReports);
			actionReports.add(actionReport);
		}

		return actionReports;
	}

	@Override
	public List<ActionDetailReport> getAllActionDetailReport(long companyId) {
		List<DataCostReport> dataCostReports = getAllFarmerCostReportByCompanyId(companyId);
		List<ActionDetailReport> actionDetailReports = new ArrayList<>();
		ActionDetailReport currentActionDetailReport = new ActionDetailReport();

		long currentFarmer = 0;
		float currentAcreage = 0;

		for (DataCostReport dataCostReport : dataCostReports) {
			if (currentFarmer != dataCostReport.getFarmerId()) {
				currentAcreage = dataCostReport.getActualAcreage();
				currentFarmer = dataCostReport.getFarmerId();

				ActionDetailReport actionDetailReport = new ActionDetailReport();
				actionDetailReport.setEmployeeId(dataCostReport.getEmployeeId());
				actionDetailReport.setFarmerId(dataCostReport.getFarmerId());
				actionDetailReport.setEmployeeName(dataCostReport.getEmployeeName());
				actionDetailReport.setFarmerName(dataCostReport.getFarmerName());

				actionDetailReport.setAcreage(currentAcreage);

				currentActionDetailReport = actionDetailReport;
				actionDetailReports.add(currentActionDetailReport);
			} else {
				currentAcreage += dataCostReport.getActualAcreage();
				currentActionDetailReport.setAcreage(currentAcreage);
			}
		}

		return actionDetailReports;
	}
}
