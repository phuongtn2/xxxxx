package vn.azteam.tabasupport.modules.file.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.file.object.dao.DirectoryDao;
import vn.azteam.tabasupport.modules.file.object.model.Directory;
import vn.azteam.tabasupport.modules.file.service.DirectoryService;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
 * @see DirectoryService
 * @since 1.0.0
 */
@Service
public class DirectoryServiceImpl extends BaseServiceImpl implements DirectoryService {
	public static final String basePath = PropertiesParserUtil.getProperty("default.file.basePath");

	@Autowired
	DirectoryDao directoryDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Directory> getAll() {
		return directoryDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAbsoluteStringPath(Directory directory) {
		String strPath = System.getProperty("file.separator") + directory.getName();
		long parentId = directory.getParentId();

		final List<Directory> directories = getAll();

		while (parentId > 0) {
			final Directory parent = directories.stream()
					.filter(dir -> dir.getId() == directory.getParentId())
					.findFirst()
					.get();
			strPath = System.getProperty("file.separator") + parent.getName() + strPath;
			parentId = parent.getParentId();
		}

		return String.format("%s%s", directory.getRepositoryPath(), strPath);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createDirectory(Directory dir) throws IOException, ValidationException {
		final List<ObjectError> errors = dir.getErrors();
		if (errors.isEmpty()) {
			final Path path = Paths.get(getAbsoluteStringPath(dir));
			Files.createDirectories(path);
			directoryDao.insertDirectory(dir);
			return dir.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Directory getDirectoryById(long directoryId) {
		Directory directory = null;
		try {
			directory = getAll().stream().filter(dir -> dir.getId() == directoryId).findFirst().get();
		} catch (NoSuchElementException e) {
			logger.error(e);
		}
		return directory;
	}

	/**
	 * {@inheritDoc}
	 */
	public Directory getDirectoryByName(String dirName) {
		return directoryDao.findByName(dirName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Directory getDirectoryByName(String dirName, long repositoryId)
			throws NullPointerException, NoSuchElementException {
		final Directory dir = getDirectoryByName(dirName);
		if (dir.getRepositoryId() == repositoryId)
			return dir;
		throw new NoSuchElementException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Directory getCompanyRootDirectory(long companyId) throws NullPointerException, NoSuchElementException {
		return getAll()
				.stream()
				.filter(directory -> directory.getCompanyId() == companyId && directory.getParentId() == 0)
				.findFirst()
				.get();
	}
}
