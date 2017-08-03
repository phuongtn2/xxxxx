package vn.azteam.tabasupport.modules.task.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.task.object.model.Task;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.task.object.dao
 * Created 2/13/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface TaskDao {

	List<Task> findAll();

	long insertTask(@Param("task") Task task);

	void updateTask(@Param("task") Task task);
}
