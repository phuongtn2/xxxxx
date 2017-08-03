package vn.azteam.tabasupport.modules.photo.object.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.validation.Errors;
import vn.azteam.tabasupport.modules.file.object.model.File;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.photo.object.model
 * Created 1/4/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@JsonIgnoreProperties({"hidden", "fileId"})
public class Photo extends File {
	private long id;
	private long fileId;
	private long hidden;

	@Override
	public long getId() {
		return id;
	}

	@Override
	public Photo setId(long id) {
		this.id = id;
		return this;
	}

	public long getFileId() {
		return fileId;
	}

	public Photo setFileId(long fileId) {
		this.fileId = fileId;
		return this;
	}

	public long getHidden() {
		return hidden;
	}

	public Photo setHidden(long hidden) {
		this.hidden = hidden;
		return this;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return this.getClass().equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		super.validate(o, errors);
		Photo photo = (Photo) o;
		String fileAcceptString = PropertiesParserUtil.getProperty("default.photo.type");
		List<String> fileType = Arrays.asList(fileAcceptString.trim().split(","));
		if (!fileType.contains(photo.getType())) {
			errors.rejectValue("type", PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "document", "fileType"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.fileType", photo.getType()));
		}
		if (photo.getSize() < 1 || photo.getSize() > PropertiesParserUtil.getLongProperty("default.photo.maxSize")) {
			errors.rejectValue("size",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "file", "size"),
					PropertiesParserUtil.getProperty("validation.model.default.msg.size"));
		}
	}

	@Override
	public String toString() {
		return "photo{" +
				"id=" + id +
				", fileId=" + fileId +
				", hidden=" + hidden +
				'}';
	}
}
