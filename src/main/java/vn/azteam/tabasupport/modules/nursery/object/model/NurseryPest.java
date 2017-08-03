package vn.azteam.tabasupport.modules.nursery.object.model;

import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

/**
 * package vn.azteam.tabasupport.modules.nursery.object.model
 * created 1/20/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @since 1.0.0
 */
public class NurseryPest extends BaseModel {
	private long id;
	private long nurseryId;
	private long actionId;
	private long pestId;
	private float ratio;

	public long getId() {
		return id;
	}

	public NurseryPest setId(long id) {
		this.id = id;
		return this;
	}

	public long getNurseryId() {
		return nurseryId;
	}

	public NurseryPest setNurseryId(long nurseryId) {
		this.nurseryId = nurseryId;
		return this;
	}

	public long getActionId() {
		return actionId;
	}

	public NurseryPest setActionId(long actionId) {
		this.actionId = actionId;
		return this;
	}

	public long getPestId() {
		return pestId;
	}

	public NurseryPest setPestId(long pestId) {
		this.pestId = pestId;
		return this;
	}

	public float getRatio() {
		return ratio;
	}

	public NurseryPest setRatio(float ratio) {
		this.ratio = ratio;
		return this;
	}

	@Override
	public String toString() {
		return "NurseryPest{" +
				"id=" + id +
				", nurseryId=" + nurseryId +
				", actionId=" + actionId +
				", pestId=" + pestId +
				", ratio=" + ratio +
				'}';
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		NurseryPest target = (NurseryPest) object;

		if (target.getActionId() < 1) {
			errors.rejectValue("actionId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nurseryPest", "actionId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "actionId"));
		}

		if (target.getPestId() < 1) {
			errors.rejectValue("pesticideId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nurseryPest", "pestId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "pestId"));
		}

		if (target.getRatio() < 1) {
			errors.rejectValue("quantity",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nurseryPest", "ratio"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "ratio"));
		}
	}
}
