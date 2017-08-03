package vn.azteam.tabasupport.modules.file.service;

import vn.azteam.tabasupport.modules.file.object.model.Repository;

import java.util.List;

/**
 * package vn.azteam.tabasupport.modules.file.service
 * created 12/28/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface RepositoryService {
	/**
	 * Get all repositories
	 *
	 * @return a {@link List} object
	 */
	List<Repository> getAll();

	/**
	 * Get Repository by id
	 *
	 * @param repoId a {@link Long} repository id
	 * @return a {@link Repository} object
	 */
	Repository getRepositoryById(long repoId);
}
