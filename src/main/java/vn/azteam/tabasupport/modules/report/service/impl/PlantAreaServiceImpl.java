package vn.azteam.tabasupport.modules.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.azteam.tabasupport.modules.report.object.dao.PlantAreaDao;
import vn.azteam.tabasupport.modules.report.object.model.CompanyBranch;
import vn.azteam.tabasupport.modules.report.object.model.PlantArea;
import vn.azteam.tabasupport.modules.report.object.model.PlantAreaReport;
import vn.azteam.tabasupport.modules.report.service.PlantAreaService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Package vn.azteam.tabasupport.modules.report.service.impl
 * Created 1/9/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */

@Service
public class PlantAreaServiceImpl implements PlantAreaService {

	@Autowired
	private PlantAreaDao plantAreaDao;

	@Override
	public List<PlantArea> getAll() {
		return plantAreaDao.findAll();
	}

	@Override
	public List<PlantArea> getAllPlantAreaByCompanyIds(List<Long> companyIds) {
		return getAll().stream().filter(
				plantArea -> companyIds.contains(plantArea.getCompanyId())
		).collect(Collectors.toList());
	}

	@Override
	public List<PlantArea> getAllActualPlantAreaByCompanyIds(List<Long> companyIds) {
		return getAll().stream().filter(
				plantArea -> companyIds.contains(plantArea.getCompanyId()) && plantArea.getIsCancel() == 0
		).collect(Collectors.toList());
	}

	@Override
	public List<PlantAreaReport> getAllPlantAreaReportByCompanyIds(List<Long> companyIds, boolean isRegistActual) {

		List<PlantArea> plantAreas;
		if (isRegistActual) {
			plantAreas = getAllActualPlantAreaByCompanyIds(companyIds);
		} else {
			plantAreas = getAllPlantAreaByCompanyIds(companyIds);
		}
		List<PlantAreaReport> plantAreaReports = new ArrayList<>();
		PlantAreaReport currentPlantAreaReport = new PlantAreaReport();
		CompanyBranch currentCompanyBranch = new CompanyBranch();
		long currentCompany = 0;
		long currentProvinceId = 0;
		long currentDistrictId = 0;
		float currentActualAcreage = 0;
		float currentRegistAcreage = 0;
		long currentFarmerQuantity = 0;
		long currentNurseryStandardQuantity = 0;
		float currentAverageLeaveInTree = 0; // trung binh so la /cay
		float currentAverageDriedLeaveInTree = 0; // trung binh so la kho /cay
		float currentAverageDensity = 0; //mat do trung binh
		int currentCultivationQuantity = 0;

		for (PlantArea plantArea : plantAreas) {

			// khac cong ty
			if (currentCompany != plantArea.getCompanyId()) {
				CompanyBranch companyBranch = new CompanyBranch();
				companyBranch.setProvinceId(plantArea.getProvinceId())
						.setDistrictId(plantArea.getDistrictId());

				currentNurseryStandardQuantity = plantArea.getNurseryStandardQuantity();
				currentActualAcreage = plantArea.getActualAcreage();
				currentRegistAcreage = plantArea.getRegistrationAcreage();
				currentFarmerQuantity = 1;
				currentAverageLeaveInTree = plantArea.getAverageLeaveInTree();
				currentAverageDriedLeaveInTree = plantArea.getAverageDriedLeaveInTree();
				currentAverageDensity = plantArea.getAverageDensity();
				if (plantArea.getCultivationId() != 0 && plantArea.getIsCancel() == 0) {
					currentCultivationQuantity = 1;
				}

				companyBranch.setNurseryStandardQuantity(currentNurseryStandardQuantity)
						.setActualAcreage(currentActualAcreage)
						.setRegistAcreage(currentRegistAcreage)
						.setFarmerQuantity(currentFarmerQuantity)
						.setAverageLeaveInTree(currentAverageLeaveInTree)
						.setAverageDriedLeaveInTree(currentAverageDriedLeaveInTree)
						.setAverageDensity(currentAverageDensity)
						.setCultivationQuantity(currentCultivationQuantity);

				currentPlantAreaReport = new PlantAreaReport();
				currentPlantAreaReport.setCompanyId(plantArea.getCompanyId())
						.setCompanyName(plantArea.getCompanyName());

				currentPlantAreaReport.getCompanyBranches().add(companyBranch);
				currentCompanyBranch = companyBranch;
				plantAreaReports.add(currentPlantAreaReport);

				currentCompany = plantArea.getCompanyId();
				currentDistrictId = plantArea.getDistrictId();
				currentProvinceId = plantArea.getProvinceId();
			} else { // cung cong ty

				// cung vung trong
				if (currentDistrictId == plantArea.getDistrictId()
						&& currentProvinceId == plantArea.getProvinceId()) {

					currentNurseryStandardQuantity += plantArea.getNurseryStandardQuantity();
					currentActualAcreage += plantArea.getActualAcreage();
					currentRegistAcreage += plantArea.getRegistrationAcreage();
					currentFarmerQuantity += 1;
					currentAverageLeaveInTree += plantArea.getAverageLeaveInTree();
					currentAverageDriedLeaveInTree += plantArea.getAverageDriedLeaveInTree();
					currentAverageDensity += plantArea.getAverageDensity();
					if (plantArea.getCultivationId() != 0 && plantArea.getIsCancel() == 0) {
						currentCultivationQuantity += 1;
					}

					currentCompanyBranch.setNurseryStandardQuantity(currentNurseryStandardQuantity)
							.setActualAcreage(currentActualAcreage)
							.setRegistAcreage(currentRegistAcreage)
							.setFarmerQuantity(currentFarmerQuantity)
							.setAverageLeaveInTree(currentAverageLeaveInTree)
							.setAverageDriedLeaveInTree(currentAverageDriedLeaveInTree)
							.setAverageDensity(currentAverageDensity)
							.setCultivationQuantity(currentCultivationQuantity);

				} else { // khac vung trong
					CompanyBranch companyBranch = new CompanyBranch();
					companyBranch.setProvinceId(plantArea.getProvinceId())
							.setDistrictId(plantArea.getDistrictId());

					currentNurseryStandardQuantity = plantArea.getNurseryStandardQuantity();
					currentActualAcreage = plantArea.getActualAcreage();
					currentRegistAcreage = plantArea.getRegistrationAcreage();
					currentFarmerQuantity = 1;
					currentAverageLeaveInTree = plantArea.getAverageLeaveInTree();
					currentAverageDriedLeaveInTree = plantArea.getAverageDriedLeaveInTree();
					currentAverageDensity = plantArea.getAverageDensity();
					if (plantArea.getCultivationId() != 0) {
						currentCultivationQuantity = 1;
					}

					companyBranch.setNurseryStandardQuantity(currentNurseryStandardQuantity)
							.setActualAcreage(currentActualAcreage)
							.setRegistAcreage(currentRegistAcreage)
							.setFarmerQuantity(currentFarmerQuantity)
							.setAverageLeaveInTree(currentAverageLeaveInTree)
							.setAverageDriedLeaveInTree(currentAverageDriedLeaveInTree)
							.setAverageDensity(currentAverageDensity)
							.setCultivationQuantity(currentCultivationQuantity);

					currentDistrictId = plantArea.getDistrictId();
					currentProvinceId = plantArea.getProvinceId();
					currentPlantAreaReport.getCompanyBranches().add(companyBranch);
					currentCompanyBranch = companyBranch;
				}
			}
		}

		return plantAreaReports;
	}
}
