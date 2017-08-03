package vn.azteam.tabasupport.modules.report.service;

import vn.azteam.tabasupport.modules.report.object.model.seedtype.SeedType;
import vn.azteam.tabasupport.modules.report.object.model.seedtype.SeedTypeReport;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.report.service
 * Created 3/22/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface SeedTypeReportService {

	List<SeedType> getAllSeedTypeReport();

	List<SeedType> getAllSeedTypeReportByCompanyIds(List<Long> companyIds);

	List<SeedTypeReport> getAllSeedTypeReportExcel(List<Long> companyIds);
}
