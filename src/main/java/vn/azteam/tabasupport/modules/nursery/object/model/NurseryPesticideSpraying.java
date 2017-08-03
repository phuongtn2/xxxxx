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
public class NurseryPesticideSpraying extends BaseModel {
	private long id;
	private long actionId;
	private long pesticideId;
	private float quantity;

	public long getId() {
		return id;
	}

	public NurseryPesticideSpraying setId(long id) {
		this.id = id;
		return this;
	}

	public long getActionId() {
		return actionId;
	}

	public NurseryPesticideSpraying setActionId(long actionId) {
		this.actionId = actionId;
		return this;
	}

	public long getPesticideId() {
		return pesticideId;
	}

	public NurseryPesticideSpraying setPesticideId(long pesticideId) {
		this.pesticideId = pesticideId;
		return this;
	}

	public float getQuantity() {
		return quantity;
	}

	public NurseryPesticideSpraying setQuantity(float quantity) {
		this.quantity = quantity;
		return this;
	}

	@Override
	public String toString() {
		return "NurseryPesticideSpraying{" +
				"id=" + id +
				", actionId=" + actionId +
				", pesticideId=" + pesticideId +
				", quantity=" + quantity +
				'}';
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		NurseryPesticideSpraying target = (NurseryPesticideSpraying) object;

		if (target.getActionId() < 1) {
			errors.rejectValue("actionId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nurseryPesticideSpraying", "actionId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "actionId"));
		}

		if (target.getPesticideId() < 1) {
			errors.rejectValue("pesticideId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nurseryPesticideSpraying", "pesticideId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "pesticideId"));
		}

		if (target.getQuantity() < 1) {
			errors.rejectValue("quantity",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nurseryPesticideSpraying", "quantity"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "quantity"));
		}
	}
}
