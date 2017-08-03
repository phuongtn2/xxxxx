package vn.azteam.tabasupport.modules.report.object.model.seedtype;

import org.springframework.beans.BeansException;
import vn.azteam.tabasupport.core.service.CompanyService;
import vn.azteam.tabasupport.core.service.LocationService;
import vn.azteam.tabasupport.core.service.impl.CompanyServiceImpl;
import vn.azteam.tabasupport.core.service.impl.LocationServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;

/**
 * Package vn.azteam.tabasupport.modules.report.object.model
 * Created 3/22/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class SeedType {
	private long registrationId;
	private long companyId;
	private float actualAcreage;
	private long provinceId;
	private long districtId;
	private long seedId;

	private String companyName;
	private String provinceName;
	private String districtName;

	public String getCompanyName() {
		CompanyService companyService;
		try {
			companyService = ApplicationContextProvider.getApplicationContext().getBean(CompanyServiceImpl.class);
			companyName = companyService.getCompanyById(companyId).getName();
		} catch (NullPointerException | BeansException e) {
			e.printStackTrace();
		}
		return companyName;
	}

	public SeedType setCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}

	public String getProvinceName() {
		LocationService locationService;
		try {
			locationService = ApplicationContextProvider.getApplicationContext().getBean(LocationServiceImpl.class);
			provinceName = locationService.getProvinceById(provinceId).getName();
		} catch (NullPointerException | BeansException e) {
			e.printStackTrace();
		}
		return provinceName;
	}

	public SeedType setProvinceName(String provinceName) {
		this.provinceName = provinceName;
		return this;
	}

	public String getDistrictName() {
		LocationService locationService;
		try {
			locationService = ApplicationContextProvider.getApplicationContext().getBean(LocationServiceImpl.class);
			districtName = locationService.getDistrictById(districtId).getName();
		} catch (NullPointerException | BeansException e) {
			e.printStackTrace();
		}
		return districtName;
	}

	public SeedType setDistrictName(String districtName) {
		this.districtName = districtName;
		return this;
	}

	public long getRegistrationId() {
		return registrationId;
	}

	public SeedType setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public SeedType setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public float getActualAcreage() {
		return actualAcreage;
	}

	public SeedType setActualAcreage(float actualAcreage) {
		this.actualAcreage = actualAcreage;
		return this;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public SeedType setProvinceId(long provinceId) {
		this.provinceId = provinceId;
		return this;
	}

	public long getDistrictId() {
		return districtId;
	}

	public SeedType setDistrictId(long districtId) {
		this.districtId = districtId;
		return this;
	}

	public long getSeedId() {
		return seedId;
	}

	public SeedType setSeedId(long seedId) {
		this.seedId = seedId;
		return this;
	}
}
