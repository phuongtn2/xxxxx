package vn.azteam.tabasupport.modules.cultivation.service.constant;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.service.constant
 * Created 1/18/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public enum ActionCultivation {
	PLANTING("PLANTING"),
	MANURING("MANURING"),
	IRRIGATION("IRRIGATION"),
	CLOVER_CUTTING("CLOVER_CUTTING"),
	PEST_UPDATE("PEST_UPDATE"),
	PESTICIDE_SPRAYING("PESTICIDE_SPRAYING"),
	HARVEST("HARVEST");
	private String value;

	ActionCultivation(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
