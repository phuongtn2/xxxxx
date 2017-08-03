package vn.azteam.tabasupport.modules.file.service;

import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.file.object.model.File;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.file.service
 * Created 12/19/2016
 *
 * @author hieunc
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface FileService {

	/**
	 * Create a new file
	 *
	 * @param file a {@link File} object.
	 * @return a {@link Long} file id
	 * @throws ValidationException
	 */
	long createFile(File file) throws ValidationException;

	/**
	 * Update a file
	 *
	 * @param file a {@link File} object.
	 */
	void updateFile(File file);

	/**
	 * Get all file
	 *
	 * @return a {@link List<File>} object.
	 */
	List<File> getAll();

	/**
	 * Get file by id
	 *
	 * @param fileId {@link Long} fileId
	 * @return a {@link File} object | Null
	 */
	File getFileById(long fileId);

	/**
	 * Check file exist.
	 *
	 * @param fileId long
	 * @return boolean
	 */
	boolean fileExist(long fileId);
}
