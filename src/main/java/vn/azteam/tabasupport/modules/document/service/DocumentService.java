package vn.azteam.tabasupport.modules.document.service;

import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.document.object.model.Document;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.document.service
 * Created 12/29/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface DocumentService {

	long uploadDocument(Document document) throws ValidationException;

	void updateDocument(Document document) throws ValidationException;

	void deleteDocumentById(long documentId) throws ValidationException;

	List<Document> getAll();

	List<Document> getAllDocumentByCompanyId(long companyId);

	Document getDocumentById(long documentId);

	void filterDocumentByName(List<Document> documents, String name);

	void filterDocumentByType(List<Document> documents, String type);
}
