package vn.azteam.tabasupport.modules.file.service;

import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.file.object.model.Directory;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.file.service
 * created 12/28/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface DirectoryService {
	/**
	 * Get all directories
	 *
	 * @return a {@link List} object
	 */
	List<Directory> getAll();

	String getAbsoluteStringPath(Directory directory);

	/**
	 * Create new directory
	 *
	 * @param dir a {@link Directory} object
	 * @return a {@link Long} directory id
	 * @throws IOException
	 * @throws ValidationException
	 */
	long createDirectory(Directory dir) throws IOException, ValidationException;

	/**
	 * Get directory by id
	 *
	 * @param directoryId a {@link Long} directory id
	 * @return a {@link Directory} object | null
	 */
	Directory getDirectoryById(long directoryId);

	/**
	 * Ger directory by Name
	 *
	 * @param dirName a {@link String}
	 * @return a {@link Directory} object | null
	 */
	Directory getDirectoryByName(String dirName);

	/**
	 * Ger directory by Name and repositoryId.
	 *
	 * @param dirName      a {@link String}
	 * @param repositoryId a {@link Long} repositoryId
	 * @return a {@link Directory} object | null
	 * @throws NullPointerException
	 * @throws NoSuchElementException
	 */
	Directory getDirectoryByName(String dirName, long repositoryId) throws NullPointerException, NoSuchElementException;

	/**
	 * Get company root directory by Name and repositoryId.
	 *
	 * @param companyId a {@link Long} repositoryId or companyId
	 * @return a {@link Directory} object | null
	 * @throws NullPointerException
	 * @throws NoSuchElementException
	 */
	Directory getCompanyRootDirectory(long companyId) throws NullPointerException, NoSuchElementException;
}
