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
public class CultivationManuring extends BaseModel {

	private long id;
	private long actionId;
	private long fieldPlotId;
	private long materialId;
	private float quantity;

	public long getId() {
		return id;
	}

	public CultivationManuring setId(long id) {
		this.id = id;
		return this;
	}

	public long getActionId() {
		return actionId;
	}

	public CultivationManuring setActionId(long actionId) {
		this.actionId = actionId;
		return this;
	}

	public long getFieldPlotId() {
		return fieldPlotId;
	}

	public CultivationManuring setFieldPlotId(long fieldPlotId) {
		this.fieldPlotId = fieldPlotId;
		return this;
	}

	public long getMaterialId() {
		return materialId;
	}

	public CultivationManuring setMaterialId(long materialId) {
		this.materialId = materialId;
		return this;
	}

	public float getQuantity() {
		return quantity;
	}

	public CultivationManuring setQuantity(float quantity) {
		this.quantity = quantity;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return this.getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"materialId",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "manuringAction", "materialId"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "materialId"));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"quantity",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "manuringAction", "quantity"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "quantity"));
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

	private void insert(CultivationDetailService service, Account account) throws ValidationException {
		if (!getErrors().isEmpty())
			throw new ValidationException("cultivation_detail_error_code", getErrors().get(0).getDefaultMessage());

		service.createManuring(this, true);
	}

	private void update(CultivationDetailService service, Account account) throws ValidationException {
		if (!getErrors().isEmpty())
			throw new ValidationException("cultivation_detail_error_code", getErrors().get(0).getDefaultMessage());

		service.updateManuring(this, true);
	}
}
