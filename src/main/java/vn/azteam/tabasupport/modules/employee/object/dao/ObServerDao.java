package vn.azteam.tabasupport.modules.employee.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.employee.object.model.ObServer;

import java.util.List;

/**
 * package vn.azteam.tabasupport.modules.employee.object.dao
 * created 1/25/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface ObServerDao {
	List<ObServer> findAll();

	void insert(@Param("obServer") ObServer obServer);

	void update(@Param("obServer") ObServer obServer);
}
