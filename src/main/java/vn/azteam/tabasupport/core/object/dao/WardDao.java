package vn.azteam.tabasupport.core.object.dao;

import vn.azteam.tabasupport.core.object.model.Ward;

import java.util.List;

/**
 * package vn.azteam.tabasupport.core.object.dao
 * created 1/23/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface WardDao {
	List<Ward> findAll();
}
