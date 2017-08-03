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
public class CloverCutting extends BaseModel {

	private long id;
	private long actionId;
	private float densityFlower;

	public long getId() {
		return id;
	}

	public CloverCutting setId(long id) {
		this.id = id;
		return this;
	}

	public long getActionId() {
		return actionId;
	}

	public CloverCutting setActionId(long actionId) {
		this.actionId = actionId;
		return this;
	}

	public float getDensityFlower() {
		return densityFlower;
	}

	public CloverCutting setDensityFlower(float densityFlower) {
		this.densityFlower = densityFlower;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return this.getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"densityFlower",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "clover_cutting_action", "densityFlower"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "densityFlower"));
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
		service.updateCloverCutting(this);
	}

	private void insert(CultivationDetailService service, Account account) throws ValidationException {
		service.createCloverCutting(this);
	}

	private DataBaseScenario getDataScenario() {
		return id < 1 ? DataBaseScenario.INSERT : DataBaseScenario.UPDATE;
	}
}