package vn.azteam.tabasupport.modules.task.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.task.object.model.TaskReport;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.task.object.dao
 * Created 2/15/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface TaskReportDao {

	List<TaskReport> findAll();

	long insertTaskReport(@Param("taskReport") TaskReport taskReport);

	void updateTaskReport(@Param("taskReport") TaskReport taskReport);
}
