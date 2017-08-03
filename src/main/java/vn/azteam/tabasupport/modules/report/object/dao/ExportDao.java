package vn.azteam.tabasupport.modules.report.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.report.object.model.MoveReport;
import vn.azteam.tabasupport.modules.report.object.model.TaskPlanReport;
import vn.azteam.tabasupport.modules.report.object.model.cost.CostFarmerDetailReport;
import vn.azteam.tabasupport.modules.report.object.model.cost.DataCostReport;
import vn.azteam.tabasupport.modules.report.object.model.fertilizer.FertilizerType;
import vn.azteam.tabasupport.modules.report.object.model.seedtype.SeedType;
import vn.azteam.tabasupport.modules.report.object.model.zoneobserver.ZoneObserver;

import java.util.Date;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.report.object.dao
 * Created 3/14/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface ExportDao {
	List<ZoneObserver> findAllZoneObserverReport();

	List<TaskPlanReport> findAllNextWeekTaskPlanReport(long assigneeId);

	List<TaskPlanReport> findAllCurrentWeekTaskPlanReport(long assigneeId);

	List<SeedType> findAllSeedType();

	List<DataCostReport> findAllDataCostReport();

	List<CostFarmerDetailReport> findAllCostFarmerDetailReport();

	List<MoveReport> findAllMoveReport(@Param("companyId") long companyId, @Param("employeeId") long employeeId, @Param("startDateReport") Date startDateReport, @Param("endDateReport") Date endDateReport);

	List<FertilizerType> findAllFertilizerReport();
}
