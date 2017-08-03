package vn.azteam.tabasupport.modules.file.object.dao;

import vn.azteam.tabasupport.modules.file.object.model.Repository;

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
public interface RepositoryDao {
	/**
	 * Find all repositories
	 *
	 * @return a {@link List} object
	 */
	List<Repository> findAll();
}
