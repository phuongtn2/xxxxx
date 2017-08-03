package vn.azteam.tabasupport.modules.cultivation.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.cultivation.object.model.FieldPlot;

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
public interface FieldPlotDao {

	void insertFieldPlot(@Param("fieldPlot") FieldPlot fieldPlot);

	void update(@Param("fieldPlot") FieldPlot fieldPlot);

	/**
	 * Find all by cultivationId
	 *
	 * @param cultivationId long
	 * @return a {@link List} obj.
	 */
	List<FieldPlot> findAllByCultivationId(@Param("cultivationId") long cultivationId);

	List<FieldPlot> getAllFieldPlotByRegistrationIds(@Param("registrationIds") long[] registrationIds);
}
