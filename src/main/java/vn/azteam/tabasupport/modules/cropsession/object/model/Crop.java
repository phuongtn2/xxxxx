package vn.azteam.tabasupport.modules.cropsession.object.model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

/**
 * package vn.azteam.tabasupport.modules.cropsession.object.model
 * created 1/16/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @since 1.0.0
 */
public class Crop extends BaseModel {
	private long id;
	private String name;
	private String description;

	public long getId() {
		return id;
	}

	public Crop setId(long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Crop setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Crop setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "crop", "name"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "name"));
	}

	@Override
	public String toString() {
		return "Crop{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
