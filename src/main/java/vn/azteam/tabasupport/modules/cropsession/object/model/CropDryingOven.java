package vn.azteam.tabasupport.modules.cropsession.object.model;

import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

/**
 * package vn.azteam.tabasupport.modules.cropsession.object.model
 * created 1/17/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @since 1.0.0
 */
public class CropDryingOven extends BaseModel {
	private long id;
	private long registrationId;
	private float length;
	private float width;
	private float height;
	private int purlins;
	private int useHusk;
	private int useNaturalFirewood;
	private int useGrownFirewood;
	private int useCoal;
	private int useOther;
	private int quantity;
	private int delFlag;

	public long getId() {
		return id;
	}

	public CropDryingOven setId(long id) {
		this.id = id;
		return this;
	}

	public long getRegistrationId() {
		return registrationId;
	}

	public CropDryingOven setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public float getLength() {
		return length;
	}

	public CropDryingOven setLength(float length) {
		this.length = length;
		return this;
	}

	public float getWidth() {
		return width;
	}

	public CropDryingOven setWidth(float width) {
		this.width = width;
		return this;
	}

	public float getHeight() {
		return height;
	}

	public CropDryingOven setHeight(float height) {
		this.height = height;
		return this;
	}

	public int getPurlins() {
		return purlins;
	}

	public CropDryingOven setPurlins(int purlins) {
		this.purlins = purlins;
		return this;
	}

	public int getUseHusk() {
		return useHusk;
	}

	public CropDryingOven setUseHusk(int useHusk) {
		this.useHusk = useHusk;
		return this;
	}

	public int getUseNaturalFirewood() {
		return useNaturalFirewood;
	}

	public CropDryingOven setUseNaturalFirewood(int useNaturalFirewood) {
		this.useNaturalFirewood = useNaturalFirewood;
		return this;
	}

	public int getUseGrownFirewood() {
		return useGrownFirewood;
	}

	public CropDryingOven setUseGrownFirewood(int useGrownFirewood) {
		this.useGrownFirewood = useGrownFirewood;
		return this;
	}

	public int getUseCoal() {
		return useCoal;
	}

	public CropDryingOven setUseCoal(int useCoal) {
		this.useCoal = useCoal;
		return this;
	}

	public int getUseOther() {
		return useOther;
	}

	public CropDryingOven setUseOther(int useOther) {
		this.useOther = useOther;
		return this;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public CropDryingOven setDelFlag(int delFlag) {
		this.delFlag = delFlag;
		return this;
	}

	public int getQuantity() {
		return quantity;
	}

	public CropDryingOven setQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CropDryingOven dryingOven = (CropDryingOven) target;

		if (dryingOven.getRegistrationId() < 1) {
			errors.reject(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "crop_drying_oven", "registrationId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "registrationId"));
		}

		if (dryingOven.getLength() < 1) {
			errors.reject(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "crop_drying_oven", "length"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "length"));
		}

		if (dryingOven.getWidth() < 1) {
			errors.reject(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "crop_drying_oven", "width"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "width"));
		}

		if (dryingOven.getHeight() < 1) {
			errors.reject(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "crop_drying_oven", "height"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "height"));
		}

		if (dryingOven.getQuantity() < 1) {
			errors.reject(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "crop_drying_oven", "quantity"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "quantity"));
		}
	}

	@Override
	public String toString() {
		return "CropDryingOven{" +
				"id=" + id +
				", registrationId=" + registrationId +
				", length=" + length +
				", width=" + width +
				", height=" + height +
				", purlins=" + purlins +
				", useHusk=" + useHusk +
				", useNaturalFirewood=" + useNaturalFirewood +
				", useGrownFirewood=" + useGrownFirewood +
				", useCoal=" + useCoal +
				", useOther=" + useOther +
				", quantity=" + quantity +
				", delFlag=" + delFlag +
				"} ";
	}
}
