package vn.azteam.tabasupport.core.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.core.object.model.User;

import java.util.List;

/**
 * package vn.azteam.tabasupport.core.object.dao
 * created 12/21/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface UserDao {
	void insertUser(@Param("user") User u);

	List<User> findAll();

	void updateUser(@Param("user") User u);
}
