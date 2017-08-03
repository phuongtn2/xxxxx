package vn.azteam.tabasupport.modules.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.material.object.model.Material;
import vn.azteam.tabasupport.modules.material.service.MaterialService;
import vn.azteam.tabasupport.modules.report.object.dao.ExportDao;
import vn.azteam.tabasupport.modules.report.object.model.seedtype.SeedReport;
import vn.azteam.tabasupport.modules.report.object.model.seedtype.SeedType;
import vn.azteam.tabasupport.modules.report.object.model.seedtype.SeedTypeCompanyBranch;
import vn.azteam.tabasupport.modules.report.object.model.seedtype.SeedTypeReport;
import vn.azteam.tabasupport.modules.report.service.SeedTypeReportService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Package vn.azteam.tabasupport.modules.report.service.impl
 * Created 3/22/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@Service
public class SeedTypeReportServiceImpl extends BaseServiceImpl implements SeedTypeReportService {
	@Autowired
	private ExportDao exportDao;
	@Autowired
	private MaterialService materialService;

	@Override
	public List<SeedType> getAllSeedTypeReport() {
		return exportDao.findAllSeedType();
	}

	@Override
	public List<SeedTypeReport> getAllSeedTypeReportExcel(List<Long> companyIds) {

		List<Material> materials = materialService.getAllMaterials();
		materials.removeIf(
				material -> !material.getSubType().equalsIgnoreCase("seed")
		);

		List<SeedType> seedTypes;
		seedTypes = getAllSeedTypeReportByCompanyIds(companyIds);
		List<SeedTypeReport> seedTypeReports = new ArrayList<>();
		List<SeedReport> currentSeedReports = null;

		long currentCompany = 0;
		long currentProvinceId = 0;
		long currentDistrictId = 0;
		SeedTypeReport currentSeedTypeReport = new SeedTypeReport();
		SeedTypeCompanyBranch currentSeedTypeCompanyBranch = new SeedTypeCompanyBranch();

		for (SeedType seedType : seedTypes) {
			//khac cong ty
			if (currentCompany != seedType.getCompanyId()) {

				//array 1
				currentSeedTypeReport = new SeedTypeReport();
				currentSeedTypeReport.setCompanyId(seedType.getCompanyId())
						.setCompanyName(seedType.getCompanyName());

				//array 2
				SeedTypeCompanyBranch seedTypeCompanyBranch = new SeedTypeCompanyBranch();
				seedTypeCompanyBranch.setProvinceName(seedType.getProvinceName())
						.setProvinceId(seedType.getProvinceId())
						.setDistrictId(seedType.getDistrictId())
						.setDistrictName(seedType.getDistrictName());
				currentSeedTypeCompanyBranch = seedTypeCompanyBranch;

				// array 3
				currentSeedReports = new ArrayList<>();
				for (Material material : materials) {
					SeedReport seedReport = new SeedReport();
					seedReport.setSeedId(material.getId());
					if (material.getId() == seedType.getSeedId()) {
						seedReport.setActualAcreage(seedType.getActualAcreage());
					}
					currentSeedReports.add(seedReport);
					currentSeedTypeCompanyBranch.getSeedReports().add(seedReport);
				}

				currentSeedTypeReport.getSeedTypeCompanyBranches().add(currentSeedTypeCompanyBranch);
				seedTypeReports.add(currentSeedTypeReport);

				currentCompany = seedType.getCompanyId();
				currentProvinceId = seedType.getProvinceId();
				currentDistrictId = seedType.getDistrictId();
			} else {// cung cong ty
				//cung vung trong
				if (currentDistrictId == seedType.getDistrictId()
						&& currentProvinceId == seedType.getProvinceId()) {
					// array 3
					if (currentSeedReports != null) {
						for (int i = 0; i < currentSeedReports.size(); i++) {
							if (currentSeedReports.get(i).getSeedId() == seedType.getSeedId()) {
								float increaseQuantity = currentSeedReports.get(i).getActualAcreage() + seedType.getActualAcreage();
								currentSeedReports.get(i).setActualAcreage(increaseQuantity);
							}
						}
					}
				} else { //khac vung trong
					//array 2
					SeedTypeCompanyBranch seedTypeCompanyBranch = new SeedTypeCompanyBranch();
					seedTypeCompanyBranch.setProvinceName(seedType.getProvinceName())
							.setProvinceId(seedType.getProvinceId())
							.setDistrictId(seedType.getDistrictId())
							.setDistrictName(seedType.getDistrictName());
					currentSeedTypeCompanyBranch = seedTypeCompanyBranch;

					// array 3
					currentSeedReports = new ArrayList<>();
					for (Material material : materials) {
						SeedReport seedReport = new SeedReport();
						seedReport.setSeedId(material.getId());
						if (material.getId() == seedType.getSeedId()) {
							seedReport.setActualAcreage(seedType.getActualAcreage());
						}
						currentSeedReports.add(seedReport);
						currentSeedTypeCompanyBranch.getSeedReports().add(seedReport);
					}
					currentSeedTypeReport.getSeedTypeCompanyBranches().add(currentSeedTypeCompanyBranch);

					currentCompany = seedType.getCompanyId();
					currentProvinceId = seedType.getProvinceId();
					currentDistrictId = seedType.getDistrictId();
				}
			}
		}
		return seedTypeReports;
	}

	@Override
	public List<SeedType> getAllSeedTypeReportByCompanyIds(List<Long> companyIds) {
		return getAllSeedTypeReport().stream().filter(
				seedType -> companyIds.contains(seedType.getCompanyId())
		).collect(Collectors.toList());
	}
}
