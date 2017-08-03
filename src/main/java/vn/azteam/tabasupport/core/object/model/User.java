package vn.azteam.tabasupport.core.object.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.BeansException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import vn.azteam.tabasupport.core.service.LocationService;
import vn.azteam.tabasupport.core.service.impl.LocationServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.core.object.model
 * created 12/21/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @since 1.0.0
 */
@JsonIgnoreProperties({"createId", "updateId", "created", "updated", "lastErrors"})
public class User extends BaseModel {
	private long id;
	private long companyId;
	private String identityCard;
	private String firstName;
	private String lastName;
	private String fullName;
	private String address;
	private int gender;
	private String phone;
	private String email;
	private long provinceId;
	private long wardId;
	private long districtId;

	private Province province;
	private District district;
	private Ward ward;

	public Province getProvince() {
		LocationService service;
		try {
			service = ApplicationContextProvider.getApplicationContext().getBean(LocationServiceImpl.class);
			province = service.getProvinceById(provinceId);
		} catch (NullPointerException | BeansException | NoSuchElementException e) {
			logger.error(e);
		}
		return province;
	}

	public User setProvince(Province province) {
		this.province = province;
		return this;
	}

	public District getDistrict() {
		LocationService service;
		try {
			service = ApplicationContextProvider.getApplicationContext().getBean(LocationServiceImpl.class);
			district = service.getDistrictById(districtId);
		} catch (NullPointerException | BeansException | NoSuchElementException e) {
			logger.error(e);
		}
		return district;
	}

	public User setDistrict(District district) {
		this.district = district;
		return this;
	}

	public Ward getWard() {
		LocationService service;
		try {
			service = ApplicationContextProvider.getApplicationContext().getBean(LocationServiceImpl.class);
			ward = service.getWardById(wardId);
		} catch (NullPointerException | BeansException | NoSuchElementException e) {
			logger.error(e);
		}
		return ward;
	}

	public User setWard(Ward ward) {
		this.ward = ward;
		return this;
	}

	public long getId() {
		return id;
	}

	public User setId(long id) {
		this.id = id;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public User setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public User setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public User setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public User setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getFullName() {
		return fullName;
	}

	public User setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public User setAddress(String address) {
		this.address = address;
		return this;
	}

	public int getGender() {
		return gender;
	}

	public User setGender(int gender) {
		this.gender = gender;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public User setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public User setProvinceId(long provinceId) {
		this.provinceId = provinceId;
		return this;
	}

	public long getWardId() {
		return wardId;
	}

	public User setWardId(long wardId) {
		this.wardId = wardId;
		return this;
	}

	public long getDistrictId() {
		return districtId;
	}

	public User setDistrictId(long districtId) {
		this.districtId = districtId;
		return this;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", companyId=" + companyId +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", fullName='" + fullName + '\'' +
				", address='" + address + '\'' +
				", phone='" + phone + '\'' +
				", email='" + email + '\'' +
				", provinceId=" + provinceId +
				", wardId=" + wardId +
				", districtId=" + districtId +
				'}';
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
	public void validate(Object target, Errors errors) {
		final User user = (User) target;

		// check empty
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "user", "firstName"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "firstName"));

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "user", "lastName"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "lastName"));

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "user", "address"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "address"));

		// check number
		if (user.getProvinceId() < 1) {
			errors.rejectValue("provinceId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "user", "provinceId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "ProvinceId"));
		}
		if (user.getDistrictId() < 1) {
			errors.rejectValue("districtId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "user", "districtId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "DistrictId"));
		}
		if (user.getWardId() < 1) {
			errors.rejectValue("wardId",
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "user", "wardId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "WardId"));
		}
	}
}
