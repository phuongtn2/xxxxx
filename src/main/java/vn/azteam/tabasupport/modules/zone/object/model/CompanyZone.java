package vn.azteam.tabasupport.modules.zone.object.model;

import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.BaseModel;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.object.model
 * Created 3/6/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class CompanyZone extends BaseModel {

	private long id;
	private long companyId;
	private long provinceId;
	private long districtId;
	private long wardId;

	public long getId() {
		return id;
	}

	public CompanyZone setId(long id) {
		this.id = id;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public CompanyZone setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public CompanyZone setProvinceId(long provinceId) {
		this.provinceId = provinceId;
		return this;
	}

	public long getDistrictId() {
		return districtId;
	}

	public CompanyZone setDistrictId(long districtId) {
		this.districtId = districtId;
		return this;
	}

	public long getWardId() {
		return wardId;
	}

	public CompanyZone setWardId(long wardId) {
		this.wardId = wardId;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

	}
}
