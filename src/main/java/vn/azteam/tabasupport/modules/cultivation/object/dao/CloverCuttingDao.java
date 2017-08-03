package vn.azteam.tabasupport.modules.cultivation.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.cultivation.object.model.CloverCutting;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.object.dao
 * Created 1/18/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public interface CloverCuttingDao {

	long insertCloverCutting(@Param("cloverCutting") CloverCutting cloverCutting);

	/**
	 * Update CloverCutting
	 *
	 * @param cloverCutting a {@link CloverCutting} obj
	 */
	void update(@Param("cloverCutting") CloverCutting cloverCutting);

	/**
	 * Find all by actionId
	 *
	 * @param detailId long
	 * @return a {@link List} obj
	 */
	List<CloverCutting> findAllByActionId(@Param("detailId") long detailId);
}
