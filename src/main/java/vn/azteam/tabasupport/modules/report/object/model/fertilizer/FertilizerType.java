package vn.azteam.tabasupport.modules.report.object.model.fertilizer;

import org.springframework.beans.BeansException;
import vn.azteam.tabasupport.core.service.CompanyService;
import vn.azteam.tabasupport.core.service.LocationService;
import vn.azteam.tabasupport.core.service.impl.CompanyServiceImpl;
import vn.azteam.tabasupport.core.service.impl.LocationServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;

/**
 * Package vn.azteam.tabasupport.modules.report.object.model.fertilizer
 * Created 4/4/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class FertilizerType {
	private long registrationId;
	private long companyId;
	private long provinceId;
	private long districtId;
	private long cultivationId;
	private long fieldPlotId;
	private long materialId;
	private float materialQuantity;
	private float actualAcreage;
	private float fieldPlotTreeQuantity;

	private String companyName;
	private String provinceName;
	private String districtName;

	public long getRegistrationId() {
		return registrationId;
	}

	public FertilizerType setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public FertilizerType setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public FertilizerType setProvinceId(long provinceId) {
		this.provinceId = provinceId;
		return this;
	}

	public long getDistrictId() {
		return districtId;
	}

	public FertilizerType setDistrictId(long districtId) {
		this.districtId = districtId;
		return this;
	}

	public long getCultivationId() {
		return cultivationId;
	}

	public FertilizerType setCultivationId(long cultivationId) {
		this.cultivationId = cultivationId;
		return this;
	}

	public long getFieldPlotId() {
		return fieldPlotId;
	}

	public FertilizerType setFieldPlotId(long fieldPlotId) {
		this.fieldPlotId = fieldPlotId;
		return this;
	}

	public long getMaterialId() {
		return materialId;
	}

	public FertilizerType setMaterialId(long materialId) {
		this.materialId = materialId;
		return this;
	}

	public float getMaterialQuantity() {
		return materialQuantity;
	}

	public FertilizerType setMaterialQuantity(float materialQuantity) {
		this.materialQuantity = materialQuantity;
		return this;
	}

	public float getActualAcreage() {
		return actualAcreage;
	}

	public FertilizerType setActualAcreage(float actualAcreage) {
		this.actualAcreage = actualAcreage;
		return this;
	}

	public float getFieldPlotTreeQuantity() {
		return fieldPlotTreeQuantity;
	}

	public FertilizerType setFieldPlotTreeQuantity(float fieldPlotTreeQuantity) {
		this.fieldPlotTreeQuantity = fieldPlotTreeQuantity;
		return this;
	}

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

	public FertilizerType setCompanyName(String companyName) {
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

	public FertilizerType setProvinceName(String provinceName) {
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

	public FertilizerType setDistrictName(String districtName) {
		this.districtName = districtName;
		return this;
	}
}
