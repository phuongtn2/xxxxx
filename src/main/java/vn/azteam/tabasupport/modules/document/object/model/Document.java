package vn.azteam.tabasupport.modules.document.object.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.BeansException;
import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.service.AccountService;
import vn.azteam.tabasupport.core.service.impl.AccountServiceImpl;
import vn.azteam.tabasupport.modules.file.object.model.File;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.document.object.model
 * Created 12/29/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@JsonIgnoreProperties({"hidden", "fileId"})
public class Document extends File {
	private long id;
	private long fileId;
	private long hidden;
	private String documentName;
	private String documentOwner;
	private Date uploadedDate;

	@Override
	public long getId() {
		return id;
	}

	@Override
	public Document setId(long id) {
		this.id = id;
		return this;

	}

	public long getFileId() {
		return fileId;
	}

	public Document setFileId(long fileId) {
		this.fileId = fileId;
		return this;
	}

	public long getHidden() {
		return hidden;
	}

	public Document setHidden(long hidden) {
		this.hidden = hidden;
		return this;
	}

	public String getDocumentName() {
		return documentName;
	}

	public Document setDocumentName(String documentName) {
		this.documentName = documentName;
		return this;
	}

	public String getDocumentOwner() {
		return documentOwner;
	}

	public Document setDocumentOwner(String documentOwner) {
		this.documentOwner = documentOwner;
		return this;
	}

	public Date getUploadedDate() {
		return uploadedDate;
	}

	public Document setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
		return this;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return this.getClass().equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		super.validate(o, errors);
		Document document = (Document) o;
		AccountService accountService;
		try {
			accountService = ApplicationContextProvider.getApplicationContext().getBean(AccountServiceImpl.class);
			document.setDocumentOwner(accountService.getAccountById(createId).getEmployee().getFullName());
		} catch (NullPointerException | BeansException e) {
			logger.error(e.getMessage());
		}
		uploadedDate = created;
		String fileAcceptString = PropertiesParserUtil.getProperty("default.document.type");
		List<String> fileType = Arrays.asList(fileAcceptString.trim().split(","));
		if (!fileType.contains(document.getType())) {
			errors.rejectValue("type", PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "document", "fileType"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.fileType", document.getType()));
		}

		if (document.getSize() < 1 || document.getSize() > PropertiesParserUtil.getLongProperty("default.document.maxSize")) {
			errors.rejectValue("size",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "file", "size"),
					PropertiesParserUtil.getProperty("validation.model.default.msg.size"));
		}
	}

	@Override
	public String toString() {
		return "Document{" +
				"id=" + id +
				", fileId=" + fileId +
				", hidden=" + hidden +
				'}';
	}
}
