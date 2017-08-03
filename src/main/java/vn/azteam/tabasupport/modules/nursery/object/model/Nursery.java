package vn.azteam.tabasupport.modules.nursery.object.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.BeansException;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropRegistration;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;
import vn.azteam.tabasupport.modules.cropsession.service.impl.CropSessionServiceImpl;
import vn.azteam.tabasupport.modules.nursery.service.NurseryDetailService;
import vn.azteam.tabasupport.modules.nursery.service.NurseryService;
import vn.azteam.tabasupport.modules.nursery.service.impl.NurseryDetailServiceImpl;
import vn.azteam.tabasupport.modules.nursery.service.impl.NurseryServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

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
public class Nursery extends BaseModel {
	private long id;
	private long registrationId;
	private float acreage;
	private int density;
	private String address;
	private int provinceId;
	private int districtId;
	private int wardId;
	private Date seedingDate;
	private Date exchangeDate;
	private float exchangeRatio;
	private float skinRatio;
	private float trayRatio;
	private long quantity;
	private long seedId;
	private int delFlag;

	private List<NurseryDetail> details;

	@JsonIgnore
	private SCENARIO scenario;
	private long standardQuantity;
	private boolean hasAbility;

	public Nursery() {

	}

	public List<NurseryDetail> getDetails() {
		if (details != null) return details;

		try {
			final NurseryDetailService service = ApplicationContextProvider.getApplicationContext().getBean(NurseryDetailServiceImpl.class);
			details = service.getAllByNurseryId(id);
		} catch (NullPointerException | NoSuchElementException | BeansException e) {
			logger.error(e);
		}
		return details;
	}

	public Nursery setDetails(List<NurseryDetail> details) {
		this.details = details;
		return this;
	}

	public long getStandardQuantity() {
		try {
			final NurseryDetailService service = ApplicationContextProvider.getApplicationContext().getBean(NurseryDetailServiceImpl.class);
			float pestRatio = 0;
			for (NurseryPest pest : service.getAllPestByNurseryId(id)) {
				pestRatio += pest.getRatio();
			}
			standardQuantity = (long) (quantity - quantity * (pestRatio / 100));
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}

		return standardQuantity;
	}

	public Nursery setStandardQuantity(long standardQuantity) {
		this.standardQuantity = standardQuantity;
		return this;
	}

	public boolean isHasAbility() {
		try {
			final CropSessionService service = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
			final CropRegistration registration = service.getCropRegistrationById(registrationId);
			hasAbility = (standardQuantity / (registration.getRegistrationAcreage())) > 24000;
		} catch (NullPointerException | BeansException | NoSuchElementException e) {
			logger.error(e);
		}
		return hasAbility;
	}

	public Nursery setHasAbility(boolean hasAbility) {
		this.hasAbility = hasAbility;
		return this;
	}

	public long getId() {
		return id;
	}

	public Nursery setId(long id) {
		this.id = id;
		return this;
	}

	public long getRegistrationId() {
		return registrationId;
	}

	public Nursery setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public float getAcreage() {
		return acreage;
	}

	public Nursery setAcreage(float acreage) {
		this.acreage = acreage;
		return this;
	}

	public int getDensity() {
		return density;
	}

	public Nursery setDensity(int density) {
		this.density = density;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public Nursery setAddress(String address) {
		this.address = address;
		return this;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public Nursery setProvinceId(int provinceId) {
		this.provinceId = provinceId;
		return this;
	}

	public int getDistrictId() {
		return districtId;
	}

	public Nursery setDistrictId(int districtId) {
		this.districtId = districtId;
		return this;
	}

	public int getWardId() {
		return wardId;
	}

	public Nursery setWardId(int wardId) {
		this.wardId = wardId;
		return this;
	}

	public Date getSeedingDate() {
		return seedingDate;
	}

	public Nursery setSeedingDate(Date seedingDate) {
		this.seedingDate = seedingDate;
		return this;
	}

	public Date getExchangeDate() {
		return exchangeDate;
	}

	public Nursery setExchangeDate(Date exchangeDate) {
		this.exchangeDate = exchangeDate;
		return this;
	}

	public float getExchangeRatio() {
		return exchangeRatio;
	}

	public Nursery setExchangeRatio(float exchangeRatio) {
		this.exchangeRatio = exchangeRatio;
		return this;
	}

	public float getSkinRatio() {
		return skinRatio;
	}

	public Nursery setSkinRatio(float skinRatio) {
		this.skinRatio = skinRatio;
		return this;
	}

	public float getTrayRatio() {
		return trayRatio;
	}

	public Nursery setTrayRatio(float trayRatio) {
		this.trayRatio = trayRatio;
		return this;
	}

	public long getQuantity() {
		return quantity;
	}

	public Nursery setQuantity(long quantity) {
		this.quantity = quantity;
		return this;
	}

	public long getSeedId() {
		return seedId;
	}

	public Nursery setSeedId(long seedId) {
		this.seedId = seedId;
		return this;
	}

	public SCENARIO getScenario() {
		return scenario;
	}

	public Nursery setScenario(SCENARIO scenario) {
		this.scenario = scenario;
		return this;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public Nursery setDelFlag(int delFlag) {
		this.delFlag = delFlag;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Nursery nursery = (Nursery) target;
		switch (nursery.getScenario()) {
			case INSERT:
				validateOnInsert(nursery, errors);
				break;
			case UPDATE:
				validateOnInsert(nursery, errors);
				break;
		}
	}

	/**
	 * Validate on insert action.
	 *
	 * @param target {@link Nursery}
	 * @param errors {@link Errors}
	 */
	private void validateOnInsert(Nursery target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nursery", "address"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "address"));

		if (target.getSeedId() < 1) {
			errors.rejectValue("seedId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nursery", "seedId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "seedId"));
		}

		if (target.getProvinceId() < 1) {
			errors.rejectValue("provinceId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nursery", "provinceId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "provinceId"));
		}
		if (target.getDistrictId() < 1) {
			errors.rejectValue("districtId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nursery", "districtId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "districtId"));
		}
		if (target.getWardId() < 1) {
			errors.rejectValue("wardId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "nursery", "wardId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "wardId"));
		}
	}

	public void save(Account account) throws NullPointerException, BeansException, ValidationException {
		final NurseryService service = ApplicationContextProvider.getApplicationContext().getBean(NurseryServiceImpl.class);
		final CropSessionService sessionService = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
		final CropRegistration registration = sessionService.getCropRegistrationById(registrationId);

		//FE can not delete nursery
		if (delFlag != 0) {
			this.delFlag = 0;
		}

		if (this.id < 1) {
			scenario = SCENARIO.INSERT;
			updateId = createId = account.getId();
			service.createNursery(this);

			// Update nurseryId for registration
			registration.setNurseryId(id)
					.setUpdateId(account.getId());

			sessionService.updateCropRegistration(registration);

		} else {
			scenario = SCENARIO.UPDATE;
			updateId = account.getId();
			service.updateNursery(this);
		}

		if (details != null && !details.isEmpty()) {
			for (NurseryDetail detail : details) {
				detail.setNurseryId(id);
				detail.save(account);
			}
		}

		if (details != null && !details.isEmpty()) {
			for (NurseryDetail detail : details) {
				detail.setNurseryId(id);
				detail.save(account);
			}
		}
	}

	@Override
	public String toString() {
		return "Nursery{" +
				"id=" + id +
				", registrationId=" + registrationId +
				", acreage=" + acreage +
				", density=" + density +
				", address='" + address + '\'' +
				", provinceId=" + provinceId +
				", districtId=" + districtId +
				", wardId=" + wardId +
				", seedingDate=" + seedingDate +
				", exchangeDate=" + exchangeDate +
				", exchangeRatio=" + exchangeRatio +
				", skinRatio=" + skinRatio +
				", trayRatio=" + trayRatio +
				", quantity=" + quantity +
				'}';
	}

	public NurseryDetail insertNewDetailLeafCutting(Account account, MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, NoSuchElementException, ValidationException {
		final NurseryDetailService service = ApplicationContextProvider.getApplicationContext().getBean(NurseryDetailServiceImpl.class);
		final NurseryDetail detail = new NurseryDetail();

		detail.setNurseryId(id)
				.setAction(NurseryDetail.ACTION.LEAF_CUTTING.getAction())
				.setCreateId(account.getId())
				.setUpdateId(account.getId())
				.copyPropertiesInListFromMapper(mapper, "actionname", "actionIndex", "actionDate", "target");

		service.createNurseryDetail(detail);
		return detail;
	}

	public NurseryDetail updateDetailLeafCutting(Account account, long detailId, MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, NoSuchElementException, ValidationException {
		final NurseryDetailService service = ApplicationContextProvider.getApplicationContext().getBean(NurseryDetailServiceImpl.class);
		final NurseryDetail detail = service.getDetailLeafCutting(detailId, id);

		detail.setNurseryId(id)
				.setAction(NurseryDetail.ACTION.LEAF_CUTTING.getAction())
				.setUpdateId(account.getId())
				.copyPropertiesInListFromMapper(mapper, "actionname", "actionIndex", "actionDate", "target");

		service.updateNurseryDetail(detail);
		return detail;
	}

	public NurseryDetail findDetail(long detailId) throws NullPointerException, NoSuchElementException {
		final NurseryDetailService service = ApplicationContextProvider.getApplicationContext().getBean(NurseryDetailServiceImpl.class);
		return getDetails()
				.stream()
				.filter(detail -> detail.getId() == detailId)
				.findFirst()
				.get();
	}

	public enum SCENARIO {
		INSERT,
		UPDATE
	}
}
