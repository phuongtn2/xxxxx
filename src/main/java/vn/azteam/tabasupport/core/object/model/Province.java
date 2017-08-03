package vn.azteam.tabasupport.core.object.model;

import org.springframework.validation.Errors;

/**
 * package vn.azteam.tabasupport.core.object.model
 * created 1/23/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @since 1.0.0
 */
public class Province extends BaseModel {
	private long id;
	private String name;
	private String type;

	public long getId() {
		return id;
	}

	public Province setId(long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Province setName(String name) {
		this.name = name;
		return this;
	}

	public String getType() {
		return type;
	}

	public Province setType(String type) {
		this.type = type;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

	}

	@Override
	public String toString() {
		return "Province{" +
				"id=" + id +
				", name='" + name + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}
