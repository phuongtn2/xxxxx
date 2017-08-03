package vn.azteam.tabasupport.modules.document.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.document.object.dao.DocumentDao;
import vn.azteam.tabasupport.modules.document.object.model.Document;
import vn.azteam.tabasupport.modules.document.service.DocumentService;
import vn.azteam.tabasupport.modules.file.object.model.File;
import vn.azteam.tabasupport.modules.file.service.FileService;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Package vn.azteam.tabasupport.modules.document.service.impl
 * Created 12/29/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see DocumentService
 * @since 1.0.0
 */
@Service
public class DocumentServiceImpl extends BaseServiceImpl implements DocumentService {

	@Autowired
	private DocumentDao documentDao;

	@Autowired
	private FileService fileService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long uploadDocument(Document document) throws ValidationException {
		List<ObjectError> errorList = document.getErrors();
		if (errorList.isEmpty()){
			try {
				createTransaction();
				long fileId = fileService.createFile(document);
				document.setFileId(fileId);
				documentDao.insertDocument(document);
				transactionManager.commit(status);
			} catch (Exception e) {
				logger.error(e);
				transactionManager.rollback(status);
				throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"),
						PropertiesParserUtil.getProperty("validation.request.default.msg"));
			}
			return document.getId();
		} else throw new ValidationException(errorList.get(0).getCode(),errorList.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateDocument(Document document) throws ValidationException {
		List<ObjectError> errorList = document.getErrors();
		if (errorList.isEmpty()){
			try {
				createTransaction();
				File file = File.newInstanceFromChild(document, File.class);
				fileService.updateFile(file);
				documentDao.updateDocument(document);
				transactionManager.commit(status);
			} catch (Exception e) {
				logger.error(e);
				transactionManager.rollback(status);
				throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"),
						PropertiesParserUtil.getProperty("validation.request.default.msg"));
			}
		} else throw new ValidationException(errorList.get(0).getCode(),errorList.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteDocumentById(long documentId) throws ValidationException {
		if (getDocumentById(documentId) != null) {
			documentDao.deleteDocumentById(documentId);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Document> getAll() {
		return documentDao.findAll();
	}

	@Override
	public List<Document> getAllDocumentByCompanyId(long companyId) {
		List<Document> documents = null;
		try {
			documents = getAll().stream().filter(
					document1 -> document1.getCompanyId() == companyId
			).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error(e);
		}
		return documents;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document getDocumentById(long documentId) {
		Document document = null;
		try {
			document = getAll().stream().filter(
					document1 -> document1.getId() == documentId
			).findFirst().get();
		} catch (Exception e) {
			logger.error(e);
		}
		return document;
	}

	@Override
	public void filterDocumentByName(List<Document> documents, String name) {
		documents.removeIf(
				document -> !document.getDocumentName().toLowerCase().matches(String.format(".*%s.*", name).toString())
		);
	}

	@Override
	public void filterDocumentByType(List<Document> documents, String type) {
		documents.removeIf(
				document -> !document.getType().toLowerCase().matches(String.format(".*%s.*", type).toString())
		);
	}
}
