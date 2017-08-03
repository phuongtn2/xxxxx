package vn.azteam.tabasupport.modules.report.service;

import vn.azteam.tabasupport.modules.report.object.model.TaskPlanReport;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.report.service
 * Created 3/21/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface TaskPlanReportService {

	List<TaskPlanReport> getAllCurrentWeekTaskPlanReport(long assigneeId);

	List<TaskPlanReport> getAllNextWeekTaskPlanReport(long assigneeId);
}
