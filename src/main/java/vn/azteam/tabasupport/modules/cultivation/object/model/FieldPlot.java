package vn.azteam.tabasupport.modules.cultivation.object.model;

import org.springframework.beans.BeansException;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.enums.DataBaseScenario;
import vn.azteam.tabasupport.modules.cultivation.service.CultivationDetailService;
import vn.azteam.tabasupport.modules.cultivation.service.CultivationService;
import vn.azteam.tabasupport.modules.cultivation.service.impl.CultivationDetailServiceImpl;
import vn.azteam.tabasupport.modules.cultivation.service.impl.CultivationServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.Date;
import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.object.model
 * Created 1/18/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @since 1.0.0
 */
public class FieldPlot extends BaseModel {
	private long id;
	private String name;
	private float acreage;
	private long registrationId;
	private long cultivationId;
	private Date cultivationDate;
	private float densityRow;
	private float densityColumn;
	private float plantRowRatio;
	private float rowPlotRatio;
	private int leavesRatio;
	private int delFlag;

	private List<CultivationDetail> details;

	public List<CultivationDetail> getDetails() {
		if (details != null) return details;

		try {
			final CultivationDetailService service = ApplicationContextProvider.getApplicationContext().getBean(CultivationDetailServiceImpl.class);
			details = service.getAllByCultivationIdAndFieldPlotId(cultivationId, id);
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}
		return details;
	}

	public FieldPlot setDetails(List<CultivationDetail> details) {
		this.details = details;
		return this;
	}

	public long getId() {
		return id;
	}

	public FieldPlot setId(long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public FieldPlot setName(String name) {
		this.name = name;
		return this;
	}

	public float getAcreage() {
		return acreage;
	}

	public FieldPlot setAcreage(float acreage) {
		this.acreage = acreage;
		return this;
	}

	public long getRegistrationId() {
		return registrationId;
	}

	public FieldPlot setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public long getCultivationId() {
		return cultivationId;
	}

	public FieldPlot setCultivationId(long cultivationId) {
		this.cultivationId = cultivationId;
		return this;
	}

	public Date getCultivationDate() {
		return cultivationDate;
	}

	public FieldPlot setCultivationDate(Date cultivationDate) {
		this.cultivationDate = cultivationDate;
		return this;
	}

	public float getDensityRow() {
		return densityRow;
	}

	public FieldPlot setDensityRow(float densityRow) {
		this.densityRow = densityRow;
		return this;
	}

	public float getDensityColumn() {
		return densityColumn;
	}

	public FieldPlot setDensityColumn(float densityColumn) {
		this.densityColumn = densityColumn;
		return this;
	}

	public float getPlantRowRatio() {
		return plantRowRatio;
	}

	public FieldPlot setPlantRowRatio(float plantRowRatio) {
		this.plantRowRatio = plantRowRatio;
		return this;
	}

	public float getRowPlotRatio() {
		return rowPlotRatio;
	}

	public FieldPlot setRowPlotRatio(float rowPlotRatio) {
		this.rowPlotRatio = rowPlotRatio;
		return this;
	}

	public int getLeavesRatio() {
		return leavesRatio;
	}

	public FieldPlot setLeavesRatio(int leavesRatio) {
		this.leavesRatio = leavesRatio;
		return this;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public FieldPlot setDelFlag(int delFlag) {
		this.delFlag = delFlag;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return this.getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final FieldPlot fieldPlot = (FieldPlot) target;

		if (fieldPlot.getDensityRow() != 0 && !(fieldPlot.getDensityRow() <= 130 && fieldPlot.getDensityRow() >= 70)) {
			errors.rejectValue("densityRow",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "fieldPlot", "densityRow"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "densityRow"));
		}

		if (fieldPlot.getDensityColumn() != 0 && !(fieldPlot.getDensityColumn() <= 70 && fieldPlot.getDensityColumn() >= 30)) {
			errors.rejectValue("densityColumn",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "fieldPlot", "densityColumn"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "densityColumn"));
		}

		if (fieldPlot.getDetails() != null) {
			for (CultivationDetail detail : fieldPlot.getDetails()) {
				final List<ObjectError> errs = detail.getErrors();
				if (!errs.isEmpty()) {
					errors.rejectValue("details",
							PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "fieldPlot", "details"),
							errs.get(0).getDefaultMessage());
					break;
				}
			}
		}
	}

	@Override
	public String toString() {
		return "FieldPlot{" +
				"id=" + id +
				", name='" + name + '\'' +
				", acreage=" + acreage +
				", registrationId=" + registrationId +
				", cultivationId=" + cultivationId +
				", cultivationDate=" + cultivationDate +
				", densityRow=" + densityRow +
				", densityColumn=" + densityColumn +
				", plantRowRatio=" + plantRowRatio +
				", rowPlotRatio=" + rowPlotRatio +
				", leavesRatio=" + leavesRatio +
				", details=" + details +
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

		if (details != null && !details.isEmpty()) {
			for (CultivationDetail detail : details) {
				detail.setFieldPlotId(id)
						.setRegistrationId(registrationId)
						.setCultivationId(cultivationId)
						.setResponsibilityEmployeeId(account.getEmployee().getId());
				detail.save(account);
			}
		}
	}

	private void update(CultivationService service, Account account) throws ValidationException {
		service.updateFieldPlot(this, true);
	}

	private void insert(CultivationService service, Account account) throws ValidationException {
		if (!getErrors().isEmpty())
			throw new ValidationException("field_plot_error_code", getErrors().get(0).getDefaultMessage());

		service.createFieldPlot(this, true);
	}
}
