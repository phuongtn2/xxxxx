package vn.azteam.tabasupport.modules.cropsession.object.model;

import org.springframework.beans.BeansException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.enums.DataBaseScenario;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;
import vn.azteam.tabasupport.modules.cropsession.service.impl.CropSessionServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

/**
 * package vn.azteam.tabasupport.modules.cropsession.object.model
 * created 1/19/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @since 1.0.0
 */
public class CropMaterialSupplement extends BaseModel {
	private long id;
	private long registrationId;
	private String phase;
	private long phaseId;
	private long actionId;
	private String memo;
	private long materialId;
	private int quantity;
	private int delFlag;

	public long getId() {
		return id;
	}

	public CropMaterialSupplement setId(long id) {
		this.id = id;
		return this;
	}

	public long getRegistrationId() {
		return registrationId;
	}

	public CropMaterialSupplement setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public long getPhaseId() {
		return phaseId;
	}

	public CropMaterialSupplement setPhaseId(long phaseId) {
		this.phaseId = phaseId;
		return this;
	}

	public long getActionId() {
		return actionId;
	}

	public CropMaterialSupplement setActionId(long actionId) {
		this.actionId = actionId;
		return this;
	}

	public String getPhase() {
		return phase;
	}

	public CropMaterialSupplement setPhase(String phase) {
		this.phase = phase;
		return this;
	}

	public String getMemo() {
		return memo;
	}

	public CropMaterialSupplement setMemo(String memo) {
		this.memo = memo;
		return this;
	}

	public long getMaterialId() {
		return materialId;
	}

	public CropMaterialSupplement setMaterialId(long materialId) {
		this.materialId = materialId;
		return this;
	}

	public int getQuantity() {
		return quantity;
	}

	public CropMaterialSupplement setQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public CropMaterialSupplement setDelFlag(int delFlag) {
		this.delFlag = delFlag;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	protected void beforeValidate() {
		super.beforeValidate();
	}

	@Override
	public void validate(Object obj, Errors errors) {
		CropMaterialSupplement target = (CropMaterialSupplement) obj;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phase",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "materialSupplement", "phase"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "phase"));

		if (target.getRegistrationId() < 1) {
			if (target.getRegistrationId() < 1) {
				errors.rejectValue("registrationId",
						PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "materialSupplement", "registrationId"),
						PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "registrationId"));
			}
		}

		if (target.getMaterialId() < 1) {
			if (target.getRegistrationId() < 1) {
				errors.rejectValue("materialId",
						PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "materialSupplement", "materialId"),
						PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "materialId"));
			}
		}

		if (target.getMaterialId() < 1) {
			if (target.getQuantity() < 1) {
				errors.rejectValue("quantity",
						PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "materialSupplement", "quantity"),
						PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "quantity"));
			}
		}

		if (target.getActionId() < 1) {
			if (target.getQuantity() < 1) {
				errors.rejectValue("actionId",
						PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "materialSupplement", "actionId"),
						PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "actionId"));
			}
		}

	}

	@Override
	public String toString() {
		return "CropMaterialSupplement{" +
				"id=" + id +
				", registrationId=" + registrationId +
				", phase='" + phase + '\'' +
				", phaseId=" + phaseId +
				", actionId=" + actionId +
				", memo='" + memo + '\'' +
				", materialId=" + materialId +
				", quantity=" + quantity +
				", delFlag=" + delFlag +
				'}';
	}

	private DataBaseScenario getDataScenario() {
		return id < 1 ? DataBaseScenario.INSERT : DataBaseScenario.UPDATE;
	}

	public void save(Account account) throws NullPointerException, BeansException, ValidationException {
		final CropSessionService service = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
		switch (getDataScenario()) {
			case INSERT:
				updateId = createId = account.getId();
				insert(service, account);
				break;
			case UPDATE:
				updateId = account.getId();
				update(service, account);
				break;
		}
	}

	private void insert(CropSessionService service, Account account) throws ValidationException {
		if (!getErrors().isEmpty())
			throw new ValidationException("cultivation_detail_error_code", getErrors().get(0).getDefaultMessage());

		service.createCropMaterialSupplement(this, true);
	}

	private void update(CropSessionService service, Account account) throws ValidationException {
		if (!getErrors().isEmpty())
			throw new ValidationException("cultivation_detail_error_code", getErrors().get(0).getDefaultMessage());

		service.updateCropMaterialSupplement(this, true);
	}

	public enum PHASE {
		CULTIVATION("CULTIVATION"),
		NURSERY("NURSERY");
		private String phase;

		PHASE(String s) {
			phase = s;
		}

		public String getPhase() {
			return phase;
		}
	}
}
