package vn.azteam.tabasupport.modules.farmer.object.model;

import org.springframework.beans.BeansException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.User;
import vn.azteam.tabasupport.enums.DataBaseScenario;
import vn.azteam.tabasupport.modules.farmer.service.FarmerService;
import vn.azteam.tabasupport.modules.farmer.service.impl.FarmerServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Package vn.azteam.tabasupport.modules.farmer.object.model
 * Created 12/26/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public class Farmer extends User {

	private long uId;
	private String farmerCode;
	private boolean inCurrentCrop;

	private List<FarmerRelative> farmerRelatives;

	public List<FarmerRelative> getFarmerRelatives() {
		try {
			FarmerService service = ApplicationContextProvider.getApplicationContext().getBean(FarmerServiceImpl.class);
			farmerRelatives = service.getAllByFarmerId(getId());
		} catch (NullPointerException | BeansException | NoSuchElementException e) {
			logger.error(e);
		}

		return farmerRelatives;
	}

	public Farmer setFarmerRelatives(List<FarmerRelative> farmerRelatives) {
		this.farmerRelatives = farmerRelatives;
		return this;
	}

	public String getFarmerCode() {
		return farmerCode;
	}

	public Farmer setFarmerCode(String farmerCode) {
		this.farmerCode = farmerCode;
		return this;
	}

	public long getuId() {
		return uId;
	}

	public Farmer setuId(long uId) {
		this.uId = uId;
		return this;
	}

	public boolean isInCurrentCrop() {
		return inCurrentCrop;
	}

	public Farmer setInCurrentCrop(boolean inCurrentCrop) {
		this.inCurrentCrop = inCurrentCrop;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return this.getClass().equals(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object object, Errors errors) {
		super.validate(object, errors);
		final Farmer target = (Farmer) object;

		// check empty
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "farmerCode",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "Farmer", "farmerCode"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "farmerCode"));

		if (target.getPhone() == null || target.getPhone().isEmpty()) {
			errors.rejectValue("phone",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "Farmer", "phone"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "phone"));
		}
	}

	@Override
	public String toString() {
		return "Farmer{" +
				"id=" + getId() +
				"uId=" + uId +
				"farmerCode=" + farmerCode +
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
		final FarmerService service = ApplicationContextProvider.getApplicationContext().getBean(FarmerServiceImpl.class);

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

		if (farmerRelatives != null && !farmerRelatives.isEmpty()) {
			for (FarmerRelative relative : farmerRelatives) {
				Farmer farmer = service.getFarmerById(getId());
				relative.setFullName(String.format("%s %s", relative.getFirstName(), relative.getLastName()))
						.setProvinceId(farmer.getProvinceId())
						.setDistrictId(farmer.getDistrictId())
						.setWardId(farmer.getWardId())
						.setAddress(farmer.getAddress());
				relative.setFarmerId(farmer.getId());
				relative.save(account);
			}
		}
	}

	private void update(FarmerService service, Account account) throws ValidationException {
		if (!getErrors().isEmpty())
			throw new ValidationException("cultivation_error_code", getErrors().get(0).getDefaultMessage());

		service.updateFarmer(this);
	}

	private void insert(FarmerService service, Account account) throws ValidationException {
		if (!getErrors().isEmpty())
			throw new ValidationException("cultivation_error_code", getErrors().get(0).getDefaultMessage());

		service.createFarmer(this);
	}

	private DataBaseScenario getDataScenario() {
		return getId() < 1 ? DataBaseScenario.INSERT : DataBaseScenario.UPDATE;
	}
}
