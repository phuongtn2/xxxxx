package vn.azteam.tabasupport.modules.cropsession.enums;

/**
 * package vn.azteam.tabasupport.modules.cropsession.enums
 * created 2/16/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public enum CropPhase {
	NURSERY("NURSERY"),
	CULTIVATION("CULTIVATION");
	private String phase;

	CropPhase(String str) {
		phase = str;
	}
}
