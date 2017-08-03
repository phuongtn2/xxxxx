package vn.azteam.tabasupport.modules.cultivation.object.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.BeansException;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.enums.DataBaseScenario;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropRegistration;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;
import vn.azteam.tabasupport.modules.cropsession.service.impl.CropSessionServiceImpl;
import vn.azteam.tabasupport.modules.cultivation.service.CultivationService;
import vn.azteam.tabasupport.modules.cultivation.service.impl.CultivationServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.List;
import java.util.NoSuchElementException;

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
public class Cultivation extends BaseModel {
	private long id;
	private long registrationId;
	private long seedId;
	private float acreage;
	private String address;
	private int provinceId;
	private int districtId;
	private int wardId;
	private float densityRow;
	private float densityColumn;
	private float plantAcreageRatio;
	private int leavesRatio;
	private int delFlag;

	@JsonIgnore
	private int plantQuantity;
	@JsonIgnore
	private int leavesQuantity;
	@JsonIgnore
	private float massRatio;


	private List<FieldPlot> fieldPlots;

	private List<CultivationHarvest> harvests;

	public List<CultivationHarvest> getHarvests() {
		if (harvests != null) return harvests;

		try {
			final CultivationService service = ApplicationContextProvider.getApplicationContext().getBean(CultivationServiceImpl.class);
			harvests = service.getAllHarvestByCultivationId(id);
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}

		return harvests;
	}

	public Cultivation setHarvests(List<CultivationHarvest> harvests) {
		this.harvests = harvests;
		return this;
	}

	public List<FieldPlot> getFieldPlots() {
		if (fieldPlots != null) return fieldPlots;
		try {
			final CultivationService service = ApplicationContextProvider.getApplicationContext().getBean(CultivationServiceImpl.class);
			fieldPlots = service.getAllFieldPlotByCultivationId(id);
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}
		return fieldPlots;
	}

	public Cultivation setFieldPlots(List<FieldPlot> fieldPlots) {
		this.fieldPlots = fieldPlots;
		return this;
	}

	public long getId() {
		return id;
	}

	public Cultivation setId(long id) {
		this.id = id;
		return this;
	}

	public long getRegistrationId() {
		return registrationId;
	}

	public Cultivation setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public long getSeedId() {
		return seedId;
	}

	public Cultivation setSeedId(long seedId) {
		this.seedId = seedId;
		return this;
	}

	public float getAcreage() {
		return acreage;
	}

	public Cultivation setAcreage(float acreage) {
		this.acreage = acreage;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public Cultivation setAddress(String address) {
		this.address = address;
		return this;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public Cultivation setProvinceId(int provinceId) {
		this.provinceId = provinceId;
		return this;
	}

	public int getDistrictId() {
		return districtId;
	}

	public Cultivation setDistrictId(int districtId) {
		this.districtId = districtId;
		return this;
	}

	public int getWardId() {
		return wardId;
	}

	public Cultivation setWardId(int wardId) {
		this.wardId = wardId;
		return this;
	}

	public float getDensityRow() {
		return densityRow;
	}

	public Cultivation setDensityRow(float densityRow) {
		this.densityRow = densityRow;
		return this;
	}

	public float getDensityColumn() {
		return densityColumn;
	}

	public Cultivation setDensityColumn(float densityColumn) {
		this.densityColumn = densityColumn;
		return this;
	}

	public float getPlantAcreageRatio() {
		return plantAcreageRatio;
	}

	public Cultivation setPlantAcreageRatio(float plantAcreageRatio) {
		this.plantAcreageRatio = plantAcreageRatio;
		return this;
	}

	public int getLeavesRatio() {
		return leavesRatio;
	}

	public Cultivation setLeavesRatio(int leavesRatio) {
		this.leavesRatio = leavesRatio;
		return this;
	}

	public int getPlantQuantity() {
		plantQuantity = (int) (getAcreage() * getPlantAcreageRatio());
		return plantQuantity;
	}

	public Cultivation setPlantQuantity(int plantQuantity) {
		this.plantQuantity = plantQuantity;
		return this;
	}

	public int getLeavesQuantity() {
		leavesQuantity = getLeavesRatio() * getPlantQuantity();
		return leavesQuantity;
	}

	public Cultivation setLeavesQuantity(int leafsQuantity) {
		this.leavesQuantity = leafsQuantity;
		return this;
	}

	public float getMassRatio() {
		try {
			List<CultivationHarvest> harvests = getHarvests();

			if (harvests != null && !harvests.isEmpty()) {
				int count = 0;
				for (CultivationHarvest h : harvests) {
					if (h.getHarvestDate() != null) {
						massRatio += h.getMassRatio();
						count++;
					}
				}

				massRatio = massRatio / count;
			}

		} catch (NullPointerException | BeansException | NoSuchElementException e) {
			logger.error(e);
		}
		return massRatio;
	}

	public Cultivation setMassRatio(float massRatio) {
		this.massRatio = massRatio;
		return this;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public Cultivation setDelFlag(int delFlag) {
		this.delFlag = delFlag;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Cultivation target = (Cultivation) obj;

		if (target.getRegistrationId() < 1) {
			errors.rejectValue("registrationId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivation", "registrationId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "registrationId"));
		}

		if (target.getFieldPlots() != null) {
			for (FieldPlot field : target.getFieldPlots()) {
				final List<ObjectError> errs = field.getErrors();
				if (!errs.isEmpty()) {
					errors.rejectValue("fieldPlots",
							PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "cultivation", "fieldPlots"),
							errs.get(0).getDefaultMessage());
					break;
				}
			}
		}
	}

	@Override
	public String toString() {
		return "Cultivation{" +
				"id=" + id +
				", registrationId=" + registrationId +
				", seedId=" + seedId +
				", acreage=" + acreage +
				", provinceId=" + provinceId +
				", districtId=" + districtId +
				", wardId=" + wardId +
				", densityRow=" + densityRow +
				", densityColumn=" + densityColumn +
				", plantAcreageRatio=" + plantAcreageRatio +
				", leavesRatio=" + leavesRatio +
				", plantQuantity=" + plantQuantity +
				", leavesQuantity=" + leavesQuantity +
				", massRatio=" + massRatio +
				", fieldPlots=" + fieldPlots +
				", harvests=" + harvests +
				'}';
	}

	/**
	 * Save data to database.
	 *
	 * @param account {@link Account} obj.
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws ValidationException
	 */
	public void save(Account account) throws NullPointerException, BeansException, ValidationException {
		final CultivationService service = ApplicationContextProvider.getApplicationContext().getBean(CultivationServiceImpl.class);
		final CropSessionService sessionService = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
		CropRegistration registration = sessionService.getCropRegistrationById(registrationId);
		// FE can not delete cultivation
		if (delFlag != 0) {
			this.delFlag = 1;
		}

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

		registration.setCultivationId(id);

		if (fieldPlots != null && !fieldPlots.isEmpty()) {
			for (FieldPlot fieldPlot : fieldPlots) {
				fieldPlot.setRegistrationId(registrationId)
						.setCultivationId(id);
				fieldPlot.save(account);
			}
		}

		if (harvests != null && !harvests.isEmpty()) {
			for (CultivationHarvest harvest : harvests) {
				harvest.setRegistrationId(registrationId)
						.setCultivationId(id);
				harvest.save(account);
			}
		}
		calculateAcreageCultivation();

		registration.setCultivationAcreage(acreage);
		service.updateCultivation(this);
		registration.setUpdateId(account.getId());

		sessionService.updateCropRegistration(registration);
	}

	private void update(CultivationService service, Account account) throws ValidationException {
		service.updateCultivation(this);
	}

	private void insert(CultivationService service, Account account) throws ValidationException {
		if (!getErrors().isEmpty())
			throw new ValidationException("cultivation_error_code", getErrors().get(0).getDefaultMessage());

		service.insertCultivation(this);
	}

	private DataBaseScenario getDataScenario() {
		return id < 1 ? DataBaseScenario.INSERT : DataBaseScenario.UPDATE;
	}

	private void calculateAcreageCultivation() throws ValidationException {
		final CultivationService cultivationService = ApplicationContextProvider.getApplicationContext().getBean(CultivationServiceImpl.class);
		List<FieldPlot> fieldPlots = cultivationService.getAllFieldPlotByCultivationId(id);
		float currentAcreage = 0;
		float currentDensityRow = 0;
		float currentDensityColumn = 0;
		float currentPlantAcreageRatio = 0;
		int currentLeavesRatio = 0;
		for (FieldPlot fieldPlot1 : fieldPlots) {
			currentAcreage += fieldPlot1.getAcreage();
			currentDensityRow += fieldPlot1.getDensityRow();
			currentDensityColumn += fieldPlot1.getDensityColumn();
			currentPlantAcreageRatio += (fieldPlot1.getPlantRowRatio() * fieldPlot1.getRowPlotRatio());
			currentLeavesRatio += (fieldPlot1.getAcreage() * fieldPlot1.getLeavesRatio());
		}

		acreage = currentAcreage;
		densityRow = 0;
		densityColumn = 0;
		plantAcreageRatio = 0;
		leavesRatio = 0;
		if (fieldPlots.size() > 0) {
			densityRow = currentDensityRow / fieldPlots.size();
			densityColumn = currentDensityColumn / fieldPlots.size();
		}
		if (currentAcreage > 0) {
			plantAcreageRatio = currentPlantAcreageRatio / currentAcreage;
			leavesRatio = (int) ((float) currentLeavesRatio / currentAcreage);
		}
	}
}
