package vn.azteam.tabasupport.modules.nursery.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.nursery.object.model.Nursery;

import java.util.List;

/**
 * package vn.azteam.tabasupport.modules.nursery.object.dao
 * created 1/20/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @since 1.0.0
 */
public interface NurseryDao {
	/**
	 * Insert new Nursery.
	 *
	 * @param nursery {@link Nursery} object
	 */
	void insert(@Param("nursery") Nursery nursery);

	/**
	 * Insert exist Nursery.
	 *
	 * @param nursery {@link Nursery} object
	 */
	void update(@Param("nursery") Nursery nursery);

	/**
	 * Get all Nursery by registrationIds
	 *
	 * @param registrationIds long[]
	 * @return a {@link List} object
	 */
	List<Nursery> getAllNurseryByRegistrationIds(@Param("registrationIds") long[] registrationIds);

	/**
	 * Get all nursery by responsibilityEmployeeId.
	 *
	 * @param responsibilityEmployeeId long
	 * @return a {@link List} object
	 */
	List<Nursery> findAllNurseryByResponsibilityEmployeeId(@Param("responsibilityEmployeeId") long responsibilityEmployeeId);
}
