package vn.azteam.tabasupport.modules.file.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.file.object.dao.RepositoryDao;
import vn.azteam.tabasupport.modules.file.object.model.Repository;
import vn.azteam.tabasupport.modules.file.service.RepositoryService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.file.service.impl
 * created 12/28/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseServiceImpl
 * @see RepositoryService
 * @since 1.0.0
 */
@Service
public class RepositoryServiceImpl extends BaseServiceImpl implements RepositoryService {
	@Autowired
	private RepositoryDao repositoryDao;

	@Override
	public List<Repository> getAll() {
		return repositoryDao.findAll();
	}

	@Override
	public Repository getRepositoryById(long repoId) {
		Repository repository = null;
		try {
			repository = getAll().stream().filter(repo -> repo.getId() == repoId).findFirst().get();
		} catch (NoSuchElementException e) {
			logger.error(e);
		}
		return repository;
	}
}