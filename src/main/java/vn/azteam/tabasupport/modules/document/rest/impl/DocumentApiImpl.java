package vn.azteam.tabasupport.modules.document.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.SimpleResponse;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.document.object.model.Document;
import vn.azteam.tabasupport.modules.document.rest.DocumentApi;
import vn.azteam.tabasupport.modules.document.service.DocumentService;
import vn.azteam.tabasupport.util.PropertiesParserUtil;
import vn.azteam.tabasupport.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Package vn.azteam.tabasupport.modules.document.rest.impl
 * Created 12/29/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see DocumentApi
 * @see BaseRestImpl
 * @since 1.0.0
 */
@Component
public class DocumentApiImpl extends BaseRestImpl implements DocumentApi {

	@Autowired
	private DocumentService documentService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object uploadDocument(MultipartHttpServletRequest request, OAuth2Authentication auth) throws ValidationException {
		Account account = (Account) auth.getPrincipal();
		final Document document = Document.newInstanceFromHttpServlet(Document.class, request);

		//TODO Check permission directoryId ?= companyId
		document.setDirectoryId(account.getCompanyId());
		document.insertAuthor(auth);
		document.setCompanyId(account.getCompanyId());
		document.setDocumentName(request.getParameter("name"));
		long documentId = documentService.uploadDocument(document);
		document.setId(documentId);
		return new SimpleResponse(new Object[]{document});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object updateDocument(HttpServletRequest request, OAuth2Authentication auth, @PathVariable long documentId, @RequestBody MultiValueMap<String, String> mapper)
			throws ValidationException {
		final Document document = documentService.getDocumentById(documentId);
		document.copyPropertiesFromMapper(mapper, "id", "fileId");
		document.insertAuthor(auth);
		documentService.updateDocument(document);
		return new SimpleResponse(new Object[]{document});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void downloadDocument(HttpServletResponse response, OAuth2Authentication auth, @PathVariable long documentId)
			throws ValidationException {
		final Document document = documentService.getDocumentById(documentId);
		responseToClient(response, document);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object deleteDocument(HttpServletResponse response, OAuth2Authentication auth, @PathVariable long documentId) throws ValidationException {
		if (documentService.getDocumentById(documentId) != null) {
			documentService.deleteDocumentById(documentId);
			return new SimpleResponse();
		} else {
			throw new ValidationException(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "Document", "id"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "Document"));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getDocument(HttpServletRequest request, OAuth2Authentication auth) {
		final Map<String, String[]> paramsMapper = request.getParameterMap();
		Account account = (Account) auth.getPrincipal();
		final List<Document> documentList = documentService.getAllDocumentByCompanyId(account.getCompanyId());

		// filter result
		if (paramsMapper.containsKey("name")) {
			String documentName = request.getParameter("name").toLowerCase();
			if (!StringUtil.isEmpty(documentName)) {
				documentService.filterDocumentByName(documentList, documentName);
			}
		}

		if (paramsMapper.containsKey("type")) {
			String type = request.getParameter("type").toLowerCase();
			if (!StringUtil.isEmpty(type)) {
				documentService.filterDocumentByType(documentList, type);
			}
		}

		return paging(request, documentList);
	}
}
