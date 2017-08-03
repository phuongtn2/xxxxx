package vn.azteam.tabasupport.modules.memo.object.model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

/**
 * Package vn.azteam.tabasupport.modules.memo.object.model
 * Created 12/22/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public class Memo extends BaseModel {

	private long id;
	private String title;
	private String description;

	public long getId() {
		return id;
	}

	public Memo setId(long id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Memo setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Memo setDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> aClass) {
		return this.getClass().equals(aClass);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"title",
				"validation.memo.field.empty.code",
				PropertiesParserUtil.getProperty("validation.memo.field.empty.msg"));
	}

	@Override
	public String toString() {
		return "Memo{" +
				"id=" + id +
				", title=" + title +
				", description=" + description +
				'}';
	}
}
