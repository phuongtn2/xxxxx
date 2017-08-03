package vn.azteam.tabasupport.modules.document.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import vn.azteam.tabasupport.core.object.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Package vn.azteam.tabasupport.modules.document.rest
 * Created 12/29/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@RestController
@RequestMapping("/document")
public interface DocumentApi {

	@PreAuthorize("hasPermission('DOCUMENT_MANAGER','ADD_NEW')")
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = {"multipart/mixed", "multipart/form-data"}, produces = {"application/json"})
	Object uploadDocument(MultipartHttpServletRequest request, OAuth2Authentication auth) throws ValidationException;

	@PreAuthorize("hasPermission('DOCUMENT_MANAGER','UPDATE')")
	@RequestMapping(value = "/update/{documentId:[0-9]+}", method = RequestMethod.POST, headers = "Accept=application/json;char-set=utf-8", produces = {"application/json;char-set=utf-8"})
	Object updateDocument(HttpServletRequest request, OAuth2Authentication auth, @PathVariable long documentId, @RequestBody MultiValueMap<String, String> mapper)
			throws ValidationException;

	@PreAuthorize("hasPermission('DOCUMENT_MANAGER','DOWNLOAD')")
	@RequestMapping(value = "/download/{documentId:[0-9]+}", method = RequestMethod.GET)
	void downloadDocument(HttpServletResponse response, OAuth2Authentication auth, @PathVariable long documentId) throws ValidationException;

	@PreAuthorize("hasPermission('DOCUMENT_MANAGER','DELETE')")
	@RequestMapping(value = "/delete/{documentId:[0-9]+}", method = RequestMethod.DELETE)
	Object deleteDocument(HttpServletResponse response, OAuth2Authentication auth, @PathVariable long documentId) throws ValidationException;

	@PreAuthorize("hasPermission('DOCUMENT_MANAGER','VIEW')")
	@RequestMapping(value = "/get", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
	Object getDocument(HttpServletRequest request, OAuth2Authentication auth);
}
