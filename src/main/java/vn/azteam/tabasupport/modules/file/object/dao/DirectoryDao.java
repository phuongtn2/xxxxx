package vn.azteam.tabasupport.modules.file.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.file.object.model.Directory;

import java.util.List;

/**
 * package vn.azteam.tabasupport.modules.file.object.dao
 * created 12/28/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface DirectoryDao {
	/**
	 * Final all directories
	 *
	 * @return a {@link List} object
	 */
	List<Directory> findAll();

	/**
	 * Insert new directory
	 *
	 * @param dir a {@link Directory} object
	 */
	void insertDirectory(@Param("dir") Directory dir);

	Directory findByName(@Param("name") String name);
}
