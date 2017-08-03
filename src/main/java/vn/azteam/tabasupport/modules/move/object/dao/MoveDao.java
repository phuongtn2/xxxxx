package vn.azteam.tabasupport.modules.move.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.move.object.model.Move;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.move.object.dao
 * Created 5/15/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface MoveDao {

	List<Move> findAllMove(@Param("employeeId") long employeeId);

	long insertMove(@Param("move") Move move);

	void updateMove(@Param("move") Move move);
}
