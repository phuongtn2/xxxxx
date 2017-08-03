package vn.azteam.tabasupport.modules.material.service.constant;

/**
 * Package vn.azteam.tabasupport.modules.material.service.constant
 * Created 1/23/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public enum StatusMaterial {

	INVESTMENT("INVESTMENT"),
	SUPPLY("SUPPLY");
	private String value;

	StatusMaterial(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
