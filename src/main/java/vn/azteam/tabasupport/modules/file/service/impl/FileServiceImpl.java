package vn.azteam.tabasupport.modules.file.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.file.object.dao.FileDao;
import vn.azteam.tabasupport.modules.file.object.model.File;
import vn.azteam.tabasupport.modules.file.service.FileService;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.file.service.impl
 * Created 12/19/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseServiceImpl
 * @see FileService
 * @since 1.0.0
 */
@Service
public class FileServiceImpl extends BaseServiceImpl implements FileService {

	@Autowired
	private FileDao fileDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createFile(File file) throws ValidationException {
		final List<ObjectError> errors = file.getErrors();
		if (errors.isEmpty()) {
			fileDao.insertFile(file);
			return file.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateFile(File file) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<File> getAll() {
		return fileDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public File getFileById(long fileId) {
		return fileDao.findFileById(fileId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean fileExist(long fileId) {
		return fileDao.count(fileId) > 0;
	}
}
