package vn.azteam.tabasupport.modules.employee.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.employee.object.model.Zone;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.employee.object.dao
 * Created 5/15/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface ZoneDao {

	List<Zone> findAll(@Param("employeeId") long employeeId);

	void insert(@Param("zone") Zone zone);

	void delete(@Param("zoneId") long zoneId);
}
