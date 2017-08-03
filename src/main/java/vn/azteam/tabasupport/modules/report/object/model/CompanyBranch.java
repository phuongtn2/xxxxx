package vn.azteam.tabasupport.modules.report.object.model;

import org.springframework.beans.BeansException;
import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.core.service.LocationService;
import vn.azteam.tabasupport.core.service.impl.LocationServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;

/**
 * Package vn.azteam.tabasupport.modules.report.object.model
 * Created 1/10/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public class CompanyBranch extends BaseModel {
	private long districtId;
	private String districtName;
	private long provinceId;
	private String provinceName;
	private float actualAcreage;
	private float registAcreage;
	private long farmerQuantity;
	private long nurseryStandardQuantity;
	private float averageLeaveInTree; // trung binh so la / cay
	private float averageDriedLeaveInTree; // trung binh so la kho / cay
	private float averageDensity; // mat do trung binh
	private int cultivationQuantity;

	public long getDistrictId() {
		return districtId;
	}

	public CompanyBranch setDistrictId(long districtId) {
		this.districtId = districtId;
		return this;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public CompanyBranch setProvinceId(long provinceId) {
		this.provinceId = provinceId;
		return this;
	}

	public float getActualAcreage() {
		return actualAcreage;
	}

	public CompanyBranch setActualAcreage(float actualAcreage) {
		this.actualAcreage = actualAcreage;
		return this;
	}

	public float getRegistAcreage() {
		return registAcreage;
	}

	public CompanyBranch setRegistAcreage(float registAcreage) {
		this.registAcreage = registAcreage;
		return this;

	}

	public String getDistrictName() {
		LocationService locationService;
		try {
			locationService = ApplicationContextProvider.getApplicationContext().getBean(LocationServiceImpl.class);
			districtName = locationService.getDistrictById(districtId).getName();
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}
		return districtName;
	}

	public CompanyBranch setDistrictName(String districtName) {
		this.districtName = districtName;
		return this;
	}

	public String getProvinceName() {
		LocationService locationService;
		try {
			locationService = ApplicationContextProvider.getApplicationContext().getBean(LocationServiceImpl.class);
			provinceName = locationService.getProvinceById(provinceId).getName();
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}
		return provinceName;
	}

	public CompanyBranch setProvinceName(String provinceName) {
		this.provinceName = provinceName;
		return this;
	}

	public long getFarmerQuantity() {
		return farmerQuantity;
	}

	public CompanyBranch setFarmerQuantity(long farmerQuantity) {
		this.farmerQuantity = farmerQuantity;
		return this;
	}

	public long getNurseryStandardQuantity() {
		return nurseryStandardQuantity;
	}

	public CompanyBranch setNurseryStandardQuantity(long nurseryStandardQuantity) {
		this.nurseryStandardQuantity = nurseryStandardQuantity;
		return this;
	}

	public float getAverageLeaveInTree() {
		return averageLeaveInTree;
	}

	public CompanyBranch setAverageLeaveInTree(float averageLeaveInTree) {
		this.averageLeaveInTree = averageLeaveInTree;
		return this;
	}

	public float getAverageDriedLeaveInTree() {
		return averageDriedLeaveInTree;
	}

	public CompanyBranch setAverageDriedLeaveInTree(float averageDriedLeaveInTree) {
		this.averageDriedLeaveInTree = averageDriedLeaveInTree;
		return this;
	}

	public float getAverageDensity() {
		return averageDensity;
	}

	public CompanyBranch setAverageDensity(float averageDensity) {
		this.averageDensity = averageDensity;
		return this;
	}

	public int getCultivationQuantity() {
		return cultivationQuantity;
	}

	public CompanyBranch setCultivationQuantity(int cultivationQuantity) {
		this.cultivationQuantity = cultivationQuantity;
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
