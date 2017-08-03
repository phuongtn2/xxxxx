package vn.azteam.tabasupport.modules.report.object.model;

import org.springframework.beans.BeansException;
import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.core.service.CompanyService;
import vn.azteam.tabasupport.core.service.LocationService;
import vn.azteam.tabasupport.core.service.impl.CompanyServiceImpl;
import vn.azteam.tabasupport.core.service.impl.LocationServiceImpl;
import vn.azteam.tabasupport.modules.cultivation.object.model.CultivationHarvest;
import vn.azteam.tabasupport.modules.cultivation.object.model.FieldPlot;
import vn.azteam.tabasupport.modules.cultivation.service.CultivationService;
import vn.azteam.tabasupport.modules.cultivation.service.impl.CultivationServiceImpl;
import vn.azteam.tabasupport.modules.nursery.object.model.Nursery;
import vn.azteam.tabasupport.modules.nursery.service.NurseryService;
import vn.azteam.tabasupport.modules.nursery.service.impl.NurseryServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.report.object.model
 * Created 1/9/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public class PlantArea extends BaseModel {
	private long companyId;
	private String companyName;
	private long provinceId;
	private String provinceName;
	private long districtId;
	private String districtName;
	private long registrationId;
	private long farmerId;
	private long nurseryId;
	private long cultivationId;
	private int soilType;
	private long responsibilityEmployeeId;
	private float registrationAcreage;
	private float actualAcreage;
	private long nurseryStandardQuantity;
	private int isCancel;
	private List<FieldPlot> fieldPlots;
	private List<CultivationHarvest> harvests;

	private float averageLeaveInTree = 0; // trung binh so la /cay
	private float averageDriedLeaveInTree = 0; // trung binh so la kho /cay
	private float averageDensity = 0; //mat do trung binh

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

	public PlantArea setDistrictName(String districtName) {
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

	public PlantArea setProvinceName(String provinceName) {
		this.provinceName = provinceName;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public PlantArea setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public String getCompanyName() {
		CompanyService companyService;
		try {
			companyService = ApplicationContextProvider.getApplicationContext().getBean(CompanyServiceImpl.class);
			companyName = companyService.getCompanyById(companyId).getName();
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}
		return companyName;
	}

	public PlantArea setCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}

	public long getDistrictId() {
		return districtId;
	}

	public PlantArea setDistrictId(long districtId) {
		this.districtId = districtId;
		return this;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public PlantArea setProvinceId(long provinceId) {
		this.provinceId = provinceId;
		return this;
	}

	public long getRegistrationId() {
		return registrationId;
	}

	public PlantArea setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public long getFarmerId() {
		return farmerId;
	}

	public PlantArea setFarmerId(long farmerId) {
		this.farmerId = farmerId;
		return this;
	}

	public long getNurseryId() {
		return nurseryId;
	}

	public PlantArea setNurseryId(long nurseryId) {
		this.nurseryId = nurseryId;
		return this;
	}

	public long getCultivationId() {
		return cultivationId;
	}

	public PlantArea setCultivationId(long cultivationId) {
		this.cultivationId = cultivationId;
		return this;
	}

	public int getSoilType() {
		return soilType;
	}

	public PlantArea setSoilType(int soilType) {
		this.soilType = soilType;
		return this;
	}

	public long getResponsibilityEmployeeId() {
		return responsibilityEmployeeId;
	}

	public PlantArea setResponsibilityEmployeeId(long responsibilityEmployeeId) {
		this.responsibilityEmployeeId = responsibilityEmployeeId;
		return this;
	}

	public float getRegistrationAcreage() {
		return registrationAcreage;
	}

	public PlantArea setRegistrationAcreage(float registrationAcreage) {
		this.registrationAcreage = registrationAcreage;
		return this;
	}

	public float getActualAcreage() {
		return actualAcreage;
	}

	public PlantArea setActualAcreage(float actualAcreage) {
		this.actualAcreage = actualAcreage;
		return this;
	}

	public long getNurseryStandardQuantity() {
		if (nurseryId == 0)
			return 0;

		NurseryService nurseryService;
		try {
			nurseryService = ApplicationContextProvider.getApplicationContext().getBean(NurseryServiceImpl.class);
			Nursery nursery = nurseryService.getNurseryById(nurseryId, registrationId);
			nurseryStandardQuantity = nursery.getStandardQuantity();
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}
		return nurseryStandardQuantity;
	}

	public PlantArea setNurseryStandardQuantity(long nurseryStandardQuantity) {
		this.nurseryStandardQuantity = nurseryStandardQuantity;
		return this;
	}

	public int getIsCancel() {
		return isCancel;
	}

	public PlantArea setIsCancel(int isCancel) {
		this.isCancel = isCancel;
		return this;
	}

	public List<FieldPlot> getFieldPlots() {
		if (cultivationId == 0) return null;
		try {
			final CultivationService service = ApplicationContextProvider.getApplicationContext().getBean(CultivationServiceImpl.class);
			fieldPlots = service.getAllFieldPlotByCultivationId(cultivationId);
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
			return null;
		}
		return fieldPlots;
	}

	public PlantArea setFieldPlots(List<FieldPlot> fieldPlots) {
		this.fieldPlots = fieldPlots;
		return this;
	}

	public List<CultivationHarvest> getHarvests() {
		if (cultivationId == 0) return null;
		try {
			final CultivationService service = ApplicationContextProvider.getApplicationContext().getBean(CultivationServiceImpl.class);
			harvests = service.getAllHarvestByCultivationId(cultivationId);
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
			return null;
		}
		return harvests;
	}

	public PlantArea setHarvests(List<CultivationHarvest> harvests) {
		this.harvests = harvests;
		return this;
	}

	public float getAverageLeaveInTree() {
		fieldPlots = getFieldPlots();
		//Tinh trung binh so la / cay
		if (fieldPlots != null && fieldPlots.size() != 0) {
			float averageLeaveInTree = 0;
			float acreageCultivation = 0;
			for (FieldPlot fieldPlot : fieldPlots) {
				averageLeaveInTree += (fieldPlot.getLeavesRatio() * fieldPlot.getAcreage());
				acreageCultivation += fieldPlot.getAcreage();
			}
			if (acreageCultivation != 0) {
				averageLeaveInTree = averageLeaveInTree / acreageCultivation;
			}
		}

		return averageLeaveInTree;
	}

	public PlantArea setAverageLeaveInTree(float averageLeaveInTree) {
		this.averageLeaveInTree = averageLeaveInTree;
		return this;
	}

	public float getAverageDriedLeaveInTree() {

		harvests = getHarvests();
		//Trung binh so la kho tren cay
		if (harvests != null && harvests.size() != 0) {
			float sumMassRatio = 0;
			for (CultivationHarvest harvest : harvests) {
				sumMassRatio += harvest.getMassRatio();
			}
			averageDriedLeaveInTree = sumMassRatio / harvests.size();
		}

		return averageDriedLeaveInTree;
	}

	public PlantArea setAverageDriedLeaveInTree(float averageDriedLeaveInTree) {
		this.averageDriedLeaveInTree = averageDriedLeaveInTree;
		return this;
	}

	public float getAverageDensity() {
		fieldPlots = getFieldPlots();
		//Tinh trung binh so la / cay
		if (fieldPlots != null && fieldPlots.size() != 0) {
			float acreageCultivation = 0;
			float sumTree = 0;
			for (FieldPlot fieldPlot : fieldPlots) {
				acreageCultivation += fieldPlot.getAcreage();
				sumTree += (fieldPlot.getPlantRowRatio() * fieldPlot.getRowPlotRatio());
			}
			if (acreageCultivation != 0) {
				averageDensity = sumTree / acreageCultivation;
			}
		}
		return averageDensity;
	}

	public PlantArea setAverageDensity(float averageDensity) {
		this.averageDensity = averageDensity;
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
