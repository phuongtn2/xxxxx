package vn.azteam.tabasupport.modules.report.object.dao;

import vn.azteam.tabasupport.modules.report.object.model.PlantArea;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.report.object.dao
 * Created 1/9/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface PlantAreaDao {
	List<PlantArea> findAll();
}
