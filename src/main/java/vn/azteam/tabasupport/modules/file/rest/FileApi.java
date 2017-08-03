package vn.azteam.tabasupport.modules.file.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import vn.azteam.tabasupport.core.object.exception.ValidationException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * package vn.azteam.tabasupport.modules.file.rest
 * created 2/15/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@RestController
@RequestMapping("file/")
public interface FileApi {
	/**
	 * Upload multiple files.
	 *
	 * @param request {@link MultipartHttpServletRequest}
	 * @param auth    {@link OAuth2Authentication}
	 * @return object
	 * @throws ValidationException
	 * @throws IOException
	 */
	@PreAuthorize("hasPermission('DOCUMENT_MANAGER','ADD_NEW')")
	@RequestMapping(value = "/upload", method = RequestMethod.POST,
			consumes = {"multipart/mixed", "multipart/form-data"},
			produces = {"application/json;char-set=utf-8"})
	Object uploadFiles(MultipartHttpServletRequest request, OAuth2Authentication auth) throws ValidationException, IOException;

	@PreAuthorize("hasPermission('DOCUMENT_MANAGER','VIEW')")
	@RequestMapping(value = "/download/{fileId:[0-9]+}", method = RequestMethod.GET)
	void downloadFile(OAuth2Authentication auth, @PathVariable("fileId") long fileId, HttpServletResponse response) throws ValidationException, IOException;
}
