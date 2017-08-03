package vn.azteam.tabasupport.modules.cultivation.object.model;

import org.springframework.beans.BeansException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.enums.DataBaseScenario;
import vn.azteam.tabasupport.modules.cultivation.service.CultivationService;
import vn.azteam.tabasupport.modules.cultivation.service.impl.CultivationServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.Date;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.object.model
 * Created 1/19/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @since 1.0.0
 */
public class CultivationHarvest extends BaseModel {

	private long id;
	private long registrationId;
	private long cultivationId;
	private int harvestIndex;
	private Date planDate;
	private Date harvestDate;
	private long leavesPinRatio;
	private long pinRatio;
	private float massRatio;
	private float productivity;

	private boolean harvestLast;
	private boolean beforeHarvestLast;

	public long getId() {
		return id;
	}

	public CultivationHarvest setId(long id) {
		this.id = id;
		return this;
	}

	public long getRegistrationId() {
		return registrationId;
	}

	public CultivationHarvest setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public long getCultivationId() {
		return cultivationId;
	}

	public CultivationHarvest setCultivationId(long cultivationId) {
		this.cultivationId = cultivationId;
		return this;
	}

	public int getHarvestIndex() {
		return harvestIndex;
	}

	public CultivationHarvest setHarvestIndex(int harvestIndex) {
		this.harvestIndex = harvestIndex;
		return this;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public CultivationHarvest setPlanDate(Date planDate) {
		this.planDate = planDate;
		return this;
	}

	public Date getHarvestDate() {
		return harvestDate;
	}

	public CultivationHarvest setHarvestDate(Date harvestDate) {
		this.harvestDate = harvestDate;
		return this;
	}

	public long getLeavesPinRatio() {
		return leavesPinRatio;
	}

	public CultivationHarvest setLeavesPinRatio(long leavesPinRatio) {
		this.leavesPinRatio = leavesPinRatio;
		return this;
	}

	public long getPinRatio() {
		return pinRatio;
	}

	public CultivationHarvest setPinRatio(long pinRatio) {
		this.pinRatio = pinRatio;
		return this;
	}

	public float getMassRatio() {
		return massRatio;
	}

	public CultivationHarvest setMassRatio(float massRatio) {
		this.massRatio = massRatio;
		return this;
	}

	public float getProductivity() {
		return productivity;
	}

	public CultivationHarvest setProductivity(float productivity) {
		this.productivity = productivity;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return this.getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"registrationId",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivation_harvest", "registrationId"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "registrationId"));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"cultivationId",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivation_harvest", "cultivationId"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "cultivationId"));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"harvestIndex",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivation_harvest", "harvestIndex"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "harvestIndex"));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"planDate",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivation_harvest", "planDate"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "planDate"));

		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"leavesPinRatio",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivation_harvest", "leavesPinRatio"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "leavesPinRatio"));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"pinRatio",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivation_harvest", "pinRatio"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "pinRatio"));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"massRatio",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivation_harvest", "massRatio"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "massRatio"));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"productivity",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivation_harvest", "productivity"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "productivity"));
	}

	public boolean isHarvestLast() {
		return harvestLast;
	}

	public CultivationHarvest setHarvestLast(boolean harvestLast) {
		this.harvestLast = harvestLast;
		return this;
	}

	public boolean isBeforeHarvestLast() {
		return beforeHarvestLast;
	}

	public CultivationHarvest setBeforeHarvestLast(boolean beforeHarvestLast) {
		this.beforeHarvestLast = beforeHarvestLast;
		return this;
	}

	@Override
	public String toString() {
		return "CultivationHarvest{" +
				"id=" + id +
				", registrationId=" + registrationId +
				", cultivationId=" + cultivationId +
				", harvestIndex=" + harvestIndex +
				", planDate=" + planDate +
				", harvestDate=" + harvestDate +
				", leavesPinRatio=" + leavesPinRatio +
				", pinRatio=" + pinRatio +
				", massRatio=" + massRatio +
				", productivity=" + productivity +
				", harvestLast=" + harvestLast +
				", beforeHarvestLast=" + beforeHarvestLast +
				'}';
	}

	private DataBaseScenario getDataScenario() {
		return id < 1 ? DataBaseScenario.INSERT : DataBaseScenario.UPDATE;
	}

	public void save(Account account) throws NullPointerException, BeansException, ValidationException {
		final CultivationService service = ApplicationContextProvider.getApplicationContext().getBean(CultivationServiceImpl.class);

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

	private void insert(CultivationService service, Account account) throws ValidationException {
		if (!getErrors().isEmpty())
			throw new ValidationException("cultivation_error_code", getErrors().get(0).getDefaultMessage());

		service.createHarvest(this, true);
	}

	private void update(CultivationService service, Account account) throws ValidationException {
		if (!getErrors().isEmpty())
			throw new ValidationException("cultivation_error_code", getErrors().get(0).getDefaultMessage());

		service.updateHarvest(this, true);
	}
}
