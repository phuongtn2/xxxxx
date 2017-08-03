package vn.azteam.tabasupport.core.object.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.io.Serializable;

/**
 * package vn.azteam.tabasupport.core.object.model
 * created 12/15/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @see Serializable
 * @see GrantedAuthority
 * @since 1.0.0
 */
@JsonIgnoreProperties({"createId", "updateId", "created", "updated", "lastErrors"})
public class Operation extends BaseModel implements GrantedAuthority, Serializable {
	private String name;
	private String moduleId;
	private String slug;

	public String getName() {
		return name;
	}

	public Operation setName(String name) {
		this.name = name;
		return this;
	}

	public String getModuleId() {
		return moduleId;
	}

	public Operation setModuleId(String moduleId) {
		this.moduleId = moduleId;
		return this;
	}

	public String getSlug() {
		return slug;
	}

	public Operation setSlug(String slug) {
		this.slug = slug;
		return this;
	}

	@Override
	@JsonIgnore
	public String getAuthority() {
		return slug;
	}

	@Override
	public String toString() {
		return "Operation{" +
				"name='" + name + '\'' +
				", moduleId='" + moduleId + '\'' +
				", slug='" + slug + '\'' +
				", createId=" + createId +
				", updateId=" + updateId +
				", created=" + created +
				", updated=" + updated +
				'}';
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
		ValidationUtils.rejectIfEmpty(errors, "name",
				PropertiesParserUtil.getProperty("validation.operation.name.empty.code"),
				PropertiesParserUtil.getProperty("validation.operation.name.empty.msg"));

		ValidationUtils.rejectIfEmpty(errors, "moduleId",
				PropertiesParserUtil.getProperty("validation.operation.moduleId.empty.code"),
				PropertiesParserUtil.getProperty("validation.operation.moduleId.empty.msg"));

		ValidationUtils.rejectIfEmpty(errors, "slug",
				PropertiesParserUtil.getProperty("validation.operation.slug.empty.code"),
				PropertiesParserUtil.getProperty("validation.operation.slug.empty.msg"));
	}
}
