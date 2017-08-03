package vn.azteam.tabasupport.modules.file.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.file.object.model.File;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.file.object.dao
 * Created 12/22/2016
 *
 * @author hieunc
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface FileDao {

	/**
	 * Insert a File
	 *
	 * @param file a {@link File} object.
	 */
	void insertFile(@Param("file") File file);

	/**
	 * Update a file
	 *
	 * @param file   a {@link File} object.
	 */
	void updateFile(@Param("file") File file);

	/**
	 * Get file by id
	 *
	 * @param fileId a {@link Long} file id
	 * @return a {@link File} object.
	 */
	File findFileById(@Param("fileId") long fileId);

	/**
	 * Get all file
	 *
	 * @return a {@link List<File>} object.
	 */
	List<File> findAll();

	/**
	 * Count file with Id
	 *
	 * @param fileId long
	 * @return a long
	 */
	long count(@Param("fileId") long fileId);
}
