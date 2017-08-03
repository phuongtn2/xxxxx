package vn.azteam.tabasupport.modules.report.service;

import vn.azteam.tabasupport.modules.report.object.model.fertilizer.FertilizerType;
import vn.azteam.tabasupport.modules.report.object.model.fertilizer.FertilizerTypeReport;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.report.service
 * Created 4/4/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface FertilizerService {

	List<FertilizerType> getAllFertilizerReport();

	List<FertilizerType> getAllFertilizerReportByCompanyIds(List<Long> companyIds);

	List<FertilizerTypeReport> getAllFertilizerTypeReportExcel(List<Long> companyIds);
}
