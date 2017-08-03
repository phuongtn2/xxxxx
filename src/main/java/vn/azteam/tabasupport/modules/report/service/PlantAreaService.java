package vn.azteam.tabasupport.modules.report.service;

import vn.azteam.tabasupport.modules.report.object.model.PlantArea;
import vn.azteam.tabasupport.modules.report.object.model.PlantAreaReport;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.report.service
 * Created 1/9/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see vn.azteam.tabasupport.modules.report.service.impl
 * @since 1.0.0
 */
public interface PlantAreaService {
	List<PlantArea> getAll();

	List<PlantArea> getAllPlantAreaByCompanyIds(List<Long> companyIds);

	List<PlantArea> getAllActualPlantAreaByCompanyIds(List<Long> companyIds);

	List<PlantAreaReport> getAllPlantAreaReportByCompanyIds(List<Long> companyIds, boolean isRegistActual);
}
