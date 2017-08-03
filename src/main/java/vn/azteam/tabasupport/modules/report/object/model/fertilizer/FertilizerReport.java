package vn.azteam.tabasupport.modules.report.object.model.fertilizer;

/**
 * Package vn.azteam.tabasupport.modules.report.object.model.fertilizer
 * Created 4/4/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class FertilizerReport {
	private long materialId;
	private float materialQuantity;

	public long getMaterialId() {
		return materialId;
	}

	public FertilizerReport setMaterialId(long materialId) {
		this.materialId = materialId;
		return this;
	}

	public float getMaterialQuantity() {
		return materialQuantity;
	}

	public FertilizerReport setMaterialQuantity(float materialQuantity) {
		this.materialQuantity = materialQuantity;
		return this;
	}
}
