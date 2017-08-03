package vn.azteam.tabasupport.core.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.core.object.model.Operation;
import vn.azteam.tabasupport.core.object.model.Role;

import java.util.List;

/**
 * package vn.azteam.tabasupport.core.object.dao
 * created 12/15/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface OperationDao {

	/**
	 * Find all Operations
	 *
	 * @return a {@link List<Role>} Object | Null
	 */
	List<Operation> findAll();

	/**
	 * Find Operation by ids
	 *
	 * @param ids a long[]
	 * @return a {@link List<Role>} Object | Null
	 */
	List<Operation> findOperationByIds(@Param("ids") long[] ids);

	/**
	 * Find Operation by roleIds
	 *
	 * @param roleIds a {@link Long}
	 * @return a {@link List<Role>} Object | Null
	 */
	List<Operation> findOperationByRoleIds(@Param("roleIds") long[] roleIds);
}
