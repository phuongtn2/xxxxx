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
public class District extends BaseModel {
	private long id;
	private long provinceId;
	private String name;
	private String type;

	public long getId() {
		return id;
	}

	public District setId(long id) {
		this.id = id;
		return this;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public District setProvinceId(long provinceId) {
		this.provinceId = provinceId;
		return this;
	}

	public String getName() {
		return name;
	}

	public District setName(String name) {
		this.name = name;
		return this;
	}

	public String getType() {
		return type;
	}

	public District setType(String type) {
		this.type = type;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {

	}

	@Override
	public String toString() {
		return "District{" +
				"id=" + id +
				", provinceId=" + provinceId +
				", name='" + name + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}
