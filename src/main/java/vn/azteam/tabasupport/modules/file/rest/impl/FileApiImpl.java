package vn.azteam.tabasupport.modules.file.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.SimpleResponse;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.file.object.model.Directory;
import vn.azteam.tabasupport.modules.file.object.model.File;
import vn.azteam.tabasupport.modules.file.rest.FileApi;
import vn.azteam.tabasupport.modules.file.service.DirectoryService;
import vn.azteam.tabasupport.modules.file.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.file.rest.impl
 * created 2/15/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseRestImpl
 * @see FileApi
 * @since 1.0.0
 */
@Component
public class FileApiImpl extends BaseRestImpl implements FileApi {
	@Autowired
	DirectoryService directoryService;

	@Autowired
	FileService fileService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object uploadFiles(MultipartHttpServletRequest request, OAuth2Authentication auth) throws ValidationException, IOException {
		final List<MultipartFile> fileList = request.getFiles("files");

		if (fileList.isEmpty()) throw new ValidationException("file_error_code", "file can not be null.");

		final Account account = (Account) auth.getPrincipal();
		final List<File> fileModels = new ArrayList<>();
		final String accountDirName = String.format("%s_%s", account.getId(), account.getUserId());
		long accountDirId = 0;

		try {
			accountDirId = directoryService.getDirectoryByName(accountDirName, account.getCompanyId()).getId();
		} catch (NullPointerException | NoSuchElementException e) {
			logger.error(e);
			final Directory accountDir = new Directory();
			accountDir.setCompanyId(account.getCompanyId())
					.setParentId(directoryService.getCompanyRootDirectory(account.getCompanyId()).getId())
					.setRepositoryId(account.getCompanyId())
					.setOwner(account.getId())
					.setName(accountDirName)
					.setCreateId(account.getId())
					.setUpdateId(account.getId());
			accountDirId = directoryService.createDirectory(accountDir);
		}

		if (accountDirId < 1) throw new AccessDeniedException("Make you have correctly permission to access thi API.");

		for (MultipartFile multipartFile : fileList) {
			final File f = new File();

			f.setMultipartFile(multipartFile)
					.setCompanyId(account.getCompanyId())
					.setRepositoryId(account.getCompanyId())
					.setDirectoryId(accountDirId)
					.setOwner(account.getId())
					.parseMultipartFile()
					.setCreateId(account.getId())
					.setUpdateId(account.getId());
			fileService.createFile(f);
			fileModels.add(f);
		}

		return new SimpleResponse(fileModels.toArray());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void downloadFile(OAuth2Authentication auth, @PathVariable("fileId") long fileId, HttpServletResponse response) throws ValidationException, IOException {
		final File file = fileService.getFileById(fileId);
		if (file == null) throw new NullPointerException();
		responseToClient(response, file);
	}
}
