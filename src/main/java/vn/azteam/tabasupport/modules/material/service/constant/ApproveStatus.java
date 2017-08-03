package vn.azteam.tabasupport.modules.material.service.constant;

/**
 * Package vn.azteam.tabasupport.modules.material.service.constant
 * Created 3/7/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public enum ApproveStatus {
	NEW_REQUEST(0),
	MIDDLE_APPROVED(5),
	LATEST_APPROVED(10);

	private int value;

	ApproveStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
