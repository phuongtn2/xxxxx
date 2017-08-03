package vn.azteam.tabasupport.modules.file.object.model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

/**
 * package vn.azteam.tabasupport.modules.file.object.model
 * created 12/28/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @since 1.0.0
 */
public class Repository extends BaseModel {
	private long id;
	private long companyId;
	private String path;

	public long getId() {
		return id;
	}

	public Repository setId(long id) {
		this.id = id;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public Repository setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public String getPath() {
		return path;
	}

	public Repository setPath(String path) {
		this.path = path;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return this.getClass().equals(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object target, Errors errors) {
		final Repository repository = (Repository) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"path",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "repository", "path"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "path"));

		if (repository.getCompanyId() < 1) {
			errors.rejectValue("companyId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "repository", "companyId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "companyId"));
		}
	}

	@Override
	public String toString() {
		return "Repository{" +
				"id=" + id +
				", companyId=" + companyId +
				", path='" + path + '\'' +
				'}';
	}
}
