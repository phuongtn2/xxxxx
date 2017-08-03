package vn.azteam.tabasupport.modules.pest.object.model;

import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.BaseModel;

/**
 * Package vn.azteam.tabasupport.modules.pest.object.model
 * Created 1/20/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class Pest extends BaseModel {

	private long id;
	private String name;
	private String description;
	private String type;

	public long getId() {
		return id;
	}

	public Pest setId(long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Pest setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Pest setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getType() {
		return type;
	}

	public Pest setType(String type) {
		this.type = type;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return this.getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
	}
}
