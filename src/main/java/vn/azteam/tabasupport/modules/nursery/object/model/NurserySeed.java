package vn.azteam.tabasupport.modules.nursery.object.model;

import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.BaseModel;

/**
 * package vn.azteam.tabasupport.modules.modules.nursery.object.model
 * created 1/20/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @since 1.0.0
 */
public class NurserySeed extends BaseModel {
	private long nurseryId;
	private long seedId;

	public long getNurseryId() {
		return nurseryId;
	}

	public NurserySeed setNurseryId(long nurseryId) {
		this.nurseryId = nurseryId;
		return this;
	}

	public long getSeedId() {
		return seedId;
	}

	public NurserySeed setSeedId(long seedId) {
		this.seedId = seedId;
		return this;
	}

	@Override
	public String toString() {
		return "NurserySeed{" +
				"nurseryId=" + nurseryId +
				", seedId=" + seedId +
				'}';
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

	}
}
