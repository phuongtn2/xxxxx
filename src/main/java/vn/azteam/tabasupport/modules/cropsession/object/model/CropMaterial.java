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
public class CropMaterial extends BaseModel {
	private long id;
	private long companyId;
	private long registrationId;
	private long materialId;
	private float quantity;
	private int status;
	private int delFlag;

	public long getId() {
		return id;
	}

	public CropMaterial setId(long id) {
		this.id = id;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public CropMaterial setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public long getRegistrationId() {
		return registrationId;
	}

	public CropMaterial setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public long getMaterialId() {
		return materialId;
	}

	public CropMaterial setMaterialId(long materialId) {
		this.materialId = materialId;
		return this;
	}

	public float getQuantity() {
		return quantity;
	}

	public CropMaterial setQuantity(float quantity) {
		this.quantity = quantity;
		return this;
	}

	public int getStatus() {
		return status;
	}

	public CropMaterial setStatus(int status) {
		this.status = status;
		return this;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public CropMaterial setDelFlag(int delFlag) {
		this.delFlag = delFlag;
		return this;
	}

	@Override
	protected void beforeValidate() {
		super.beforeValidate();
		status = status < 1 ? STATUS.INVESTMENT.stt() : status;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final CropMaterial material = (CropMaterial) target;

		if (material.getRegistrationId() < 1) {
			errors.reject(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "crop_material", "registrationId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "registrationId"));
		}

		if (material.getMaterialId() < 1) {
			errors.reject(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "crop_material", "materialId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "materialId"));
		}

		if (material.getQuantity() < 1) {
			errors.reject(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "crop_material", "quantity"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "quantity"));
		}
	}

	@Override
	public String toString() {
		return "CropMaterial{" +
				"id=" + id +
				", companyId=" + companyId +
				", registrationId=" + registrationId +
				", materialId=" + materialId +
				", quantity=" + quantity +
				", status=" + status +
				'}';
	}

	public enum STATUS {
		UNKNOWN(0),
		INVESTMENT(1),
		SELF_SUPPLY(2);

		private int stt;

		STATUS(int i) {
			stt = i;
		}

		public int stt() {
			return stt;
		}
	}
}
