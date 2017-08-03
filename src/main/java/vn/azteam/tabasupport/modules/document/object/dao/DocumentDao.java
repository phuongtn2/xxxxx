package vn.azteam.tabasupport.modules.document.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.document.object.model.Document;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.document.object.dao
 * Created 12/29/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface DocumentDao {

	/**
	 * Insert document
	 *
	 * @param document a {@link Document} object.
	 * @return a long
	 */
	long insertDocument(@Param("document") Document document);

	/**
	 * Update document
	 * @param document a {@link Document} object.
	 */
	void updateDocument(@Param("document")Document document);

	/**
	 * Delete document
	 * @param documentId a long
	 */
	void deleteDocumentById(@Param("documentId") long documentId);

	/**
	 * Get all document
	 * @return a {@link List<Document>} object.
	 */
	List<Document> findAll();
}
