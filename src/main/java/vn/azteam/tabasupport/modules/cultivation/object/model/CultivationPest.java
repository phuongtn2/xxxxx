package vn.azteam.tabasupport.modules.cultivation.object.model;

import org.springframework.beans.BeansException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.enums.DataBaseScenario;
import vn.azteam.tabasupport.modules.cultivation.service.CultivationDetailService;
import vn.azteam.tabasupport.modules.cultivation.service.impl.CultivationDetailServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.object.model
 * Created 1/18/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class CultivationPest extends BaseModel {

	private long id;
	private long cultivationId;
	private long actionId;
	private long fieldPlotId;
	private long pestId;
	private float ratio;

	public long getId() {
		return id;
	}

	public CultivationPest setId(long id) {
		this.id = id;
		return this;
	}

	public long getCultivationId() {
		return cultivationId;
	}

	public CultivationPest setCultivationId(long cultivationId) {
		this.cultivationId = cultivationId;
		return this;
	}

	public long getActionId() {
		return actionId;
	}

	public CultivationPest setActionId(long actionId) {
		this.actionId = actionId;
		return this;
	}

	public long getFieldPlotId() {
		return fieldPlotId;
	}

	public CultivationPest setFieldPlotId(long fieldPlotId) {
		this.fieldPlotId = fieldPlotId;
		return this;
	}

	public long getPestId() {
		return pestId;
	}

	public CultivationPest setPestId(long pestId) {
		this.pestId = pestId;
		return this;
	}

	public float getRatio() {
		return ratio;
	}

	public CultivationPest setRatio(float ratio) {
		this.ratio = ratio;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return this.getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"pestId",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivation_pest", "pestId"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "pestId"));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"ratio",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivation_pest", "ratio"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "ratio"));
	}

	private DataBaseScenario getDataScenario() {
		return id < 1 ? DataBaseScenario.INSERT : DataBaseScenario.UPDATE;
	}

	public void save(Account account) throws NullPointerException, BeansException, ValidationException {
		final CultivationDetailService service = ApplicationContextProvider.getApplicationContext().getBean(CultivationDetailServiceImpl.class);

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

	private void update(CultivationDetailService service, Account account) throws ValidationException {
		if (!getErrors().isEmpty())
			throw new ValidationException("cultivation_detail_error_code", getErrors().get(0).getDefaultMessage());

		service.updatePest(this, true);
	}

	private void insert(CultivationDetailService service, Account account) throws ValidationException {
		if (!getErrors().isEmpty())
			throw new ValidationException("cultivation_detail_error_code", getErrors().get(0).getDefaultMessage());

		service.createPest(this, true);
	}
}
