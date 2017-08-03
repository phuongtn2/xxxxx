package vn.azteam.tabasupport.modules.report.object.model.seedtype;

/**
 * Package vn.azteam.tabasupport.modules.report.object.model.seedtype
 * Created 3/22/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class SeedReport {
	private long seedId;
	private float actualAcreage;

	public long getSeedId() {
		return seedId;
	}

	public SeedReport setSeedId(long seedId) {
		this.seedId = seedId;
		return this;
	}

	public float getActualAcreage() {
		return actualAcreage;
	}

	public SeedReport setActualAcreage(float actualAcreage) {
		this.actualAcreage = actualAcreage;
		return this;
	}
}
