package vn.azteam.tabasupport.modules.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.material.object.model.Material;
import vn.azteam.tabasupport.modules.material.service.MaterialService;
import vn.azteam.tabasupport.modules.report.object.dao.ExportDao;
import vn.azteam.tabasupport.modules.report.object.model.fertilizer.FertilizerReport;
import vn.azteam.tabasupport.modules.report.object.model.fertilizer.FertilizerType;
import vn.azteam.tabasupport.modules.report.object.model.fertilizer.FertilizerTypeCompanyBranch;
import vn.azteam.tabasupport.modules.report.object.model.fertilizer.FertilizerTypeReport;
import vn.azteam.tabasupport.modules.report.service.FertilizerService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Package vn.azteam.tabasupport.modules.report.service.impl
 * Created 4/4/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@Service
public class FertilizerServiceImpl extends BaseServiceImpl implements FertilizerService {

	@Autowired
	private ExportDao exportDao;
	@Autowired
	private MaterialService materialService;

	@Override
	public List<FertilizerType> getAllFertilizerReport() {
		return exportDao.findAllFertilizerReport();
	}

	@Override
	public List<FertilizerType> getAllFertilizerReportByCompanyIds(List<Long> companyIds) {
		return getAllFertilizerReport().stream().filter(
				fertilizerType -> companyIds.contains(fertilizerType.getCompanyId())
		).collect(Collectors.toList());
	}

	@Override
	public List<FertilizerTypeReport> getAllFertilizerTypeReportExcel(List<Long> companyIds) {
		List<Material> materials = materialService.getAllMaterials();
		materials.removeIf(
				material -> !material.getSubType().equalsIgnoreCase("fertilizer")
		);

		List<FertilizerType> fertilizerTypes = getAllFertilizerReportByCompanyIds(companyIds);
		List<FertilizerTypeReport> fertilizerTypeReports = new ArrayList<>();
		List<FertilizerReport> currentFertilizerReports = null;

		float currentAcreage = 0;
		float currentDensity = 0;
		long currentCultivationId = 0;

		long currentCompany = 0;
		long currentProvinceId = 0;
		long currentDistrictId = 0;
		FertilizerTypeReport currentFertilizerTypeReport = new FertilizerTypeReport();
		FertilizerTypeCompanyBranch currentFertilizerTypeCompanyBranch = new FertilizerTypeCompanyBranch();

		for (FertilizerType fertilizerType : fertilizerTypes) {
			//khac cong ty
			if (currentCompany != fertilizerType.getCompanyId()) {
				currentAcreage = fertilizerType.getActualAcreage();
				currentDensity = fertilizerType.getFieldPlotTreeQuantity() / currentAcreage;

				//array 1
				currentFertilizerTypeReport = new FertilizerTypeReport();
				currentFertilizerTypeReport.setCompanyId(fertilizerType.getCompanyId());
				currentFertilizerTypeReport.setCompanyName(fertilizerType.getCompanyName());

				//array 2
				FertilizerTypeCompanyBranch fertilizerTypeCompanyBranch = new FertilizerTypeCompanyBranch();
				fertilizerTypeCompanyBranch.setProvinceName(fertilizerType.getProvinceName());
				fertilizerTypeCompanyBranch.setProvinceId(fertilizerType.getProvinceId());
				fertilizerTypeCompanyBranch.setDistrictId(fertilizerType.getDistrictId());
				fertilizerTypeCompanyBranch.setDistrictName(fertilizerType.getDistrictName());
				fertilizerTypeCompanyBranch.setAcreage(currentAcreage);
				fertilizerTypeCompanyBranch.setDensity(currentDensity);
				currentFertilizerTypeCompanyBranch = fertilizerTypeCompanyBranch;

				// array 3
				currentFertilizerReports = new ArrayList<>();
				for (Material material : materials) {
					FertilizerReport fertilizerReport = new FertilizerReport();
					fertilizerReport.setMaterialId(material.getId());
					if (material.getId() == fertilizerType.getMaterialId()) {
						fertilizerReport.setMaterialQuantity(fertilizerType.getMaterialQuantity());
					}
					currentFertilizerReports.add(fertilizerReport);
					currentFertilizerTypeCompanyBranch.getFertilizerReports().add(fertilizerReport);
				}

				currentFertilizerTypeReport.getFertilizerTypeCompanyBranches().add(currentFertilizerTypeCompanyBranch);
				fertilizerTypeReports.add(currentFertilizerTypeReport);

				currentCompany = fertilizerType.getCompanyId();
				currentProvinceId = fertilizerType.getProvinceId();
				currentDistrictId = fertilizerType.getDistrictId();
				currentCultivationId = fertilizerType.getCultivationId();
			} else {// cung cong ty
				//cung vung trong
				if (currentDistrictId == fertilizerType.getDistrictId()
						&& currentProvinceId == fertilizerType.getProvinceId()) {

					// khac ruong
					if (currentCultivationId != fertilizerType.getCultivationId()) {
						currentAcreage += fertilizerType.getActualAcreage();
						currentCultivationId = fertilizerType.getCultivationId();
					}
					currentDensity += fertilizerType.getFieldPlotTreeQuantity() / currentAcreage;
					currentFertilizerTypeCompanyBranch.setAcreage(currentAcreage);
					currentFertilizerTypeCompanyBranch.setDensity(currentDensity);
					// array 3
					for (FertilizerReport fertilizerReport : currentFertilizerReports) {
						if (fertilizerReport.getMaterialId() == fertilizerType.getMaterialId()) {
							fertilizerReport.setMaterialQuantity(fertilizerReport.getMaterialQuantity() + fertilizerType.getMaterialQuantity());
						}
					}
				} else { //khac vung trong

					currentAcreage = fertilizerType.getActualAcreage();
					currentDensity = fertilizerType.getFieldPlotTreeQuantity() / currentAcreage;

					//array 2
					FertilizerTypeCompanyBranch fertilizerTypeCompanyBranch = new FertilizerTypeCompanyBranch();
					fertilizerTypeCompanyBranch.setProvinceName(fertilizerType.getProvinceName());
					fertilizerTypeCompanyBranch.setProvinceId(fertilizerType.getProvinceId());
					fertilizerTypeCompanyBranch.setDistrictId(fertilizerType.getDistrictId());
					fertilizerTypeCompanyBranch.setDistrictName(fertilizerType.getDistrictName());

					fertilizerTypeCompanyBranch.setAcreage(currentAcreage);
					fertilizerTypeCompanyBranch.setDensity(currentDensity);
					currentFertilizerTypeCompanyBranch = fertilizerTypeCompanyBranch;

					// array 3
					currentFertilizerReports = new ArrayList<>();
					for (Material material : materials) {
						FertilizerReport fertilizerReport = new FertilizerReport();
						fertilizerReport.setMaterialId(material.getId());

						if (material.getId() == fertilizerReport.getMaterialId()) {
							fertilizerReport.setMaterialQuantity(fertilizerType.getMaterialQuantity());
						}
						currentFertilizerReports.add(fertilizerReport);
						currentFertilizerTypeCompanyBranch.getFertilizerReports().add(fertilizerReport);
					}
					currentFertilizerTypeReport.getFertilizerTypeCompanyBranches().add(currentFertilizerTypeCompanyBranch);

					currentCompany = fertilizerType.getCompanyId();
					currentProvinceId = fertilizerType.getProvinceId();
					currentDistrictId = fertilizerType.getDistrictId();
					currentCultivationId = fertilizerType.getCultivationId();
				}
			}
		}
		return fertilizerTypeReports;
	}
}
