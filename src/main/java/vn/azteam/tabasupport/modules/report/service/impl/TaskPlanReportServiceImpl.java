package vn.azteam.tabasupport.modules.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.report.object.dao.ExportDao;
import vn.azteam.tabasupport.modules.report.object.model.TaskPlanReport;
import vn.azteam.tabasupport.modules.report.service.TaskPlanReportService;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.report.service.impl
 * Created 3/21/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@Service
public class TaskPlanReportServiceImpl extends BaseServiceImpl implements TaskPlanReportService {

	@Autowired
	private ExportDao exportDao;

	@Override
	public List<TaskPlanReport> getAllCurrentWeekTaskPlanReport(long assigneeId) {
		return exportDao.findAllCurrentWeekTaskPlanReport(assigneeId);
	}

	@Override
	public List<TaskPlanReport> getAllNextWeekTaskPlanReport(long assigneeId) {
		return exportDao.findAllNextWeekTaskPlanReport(assigneeId);
	}
}
