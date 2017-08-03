package vn.azteam.tabasupport.modules.cropsession.object.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.BeansException;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.BaseModel;
import vn.azteam.tabasupport.core.object.model.Company;
import vn.azteam.tabasupport.core.service.CompanyService;
import vn.azteam.tabasupport.core.service.impl.CompanyServiceImpl;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;
import vn.azteam.tabasupport.modules.cropsession.service.impl.CropSessionServiceImpl;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.modules.employee.object.model.ObServer;
import vn.azteam.tabasupport.modules.employee.service.EmployeeService;
import vn.azteam.tabasupport.modules.employee.service.ObServerService;
import vn.azteam.tabasupport.modules.employee.service.impl.EmployeeServiceImpl;
import vn.azteam.tabasupport.modules.employee.service.impl.ObServerServiceImpl;
import vn.azteam.tabasupport.modules.farmer.object.model.Farmer;
import vn.azteam.tabasupport.modules.farmer.service.FarmerService;
import vn.azteam.tabasupport.modules.farmer.service.impl.FarmerServiceImpl;
import vn.azteam.tabasupport.modules.nursery.object.model.Nursery;
import vn.azteam.tabasupport.modules.nursery.service.NurseryService;
import vn.azteam.tabasupport.modules.nursery.service.impl.NurseryServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Package vn.azteam.tabasupport.modules.farmer.object.model
 * Created 1/6/2017
 *
 * @author hieunc
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @since 1.0.0
 */
public class CropRegistration extends BaseModel {
	private long id;
	private long farmerId;
	private long companyId;
	private long departmentId;
	private long divisionId;
	private long cropId;
	private long zoneId;
	private long nurseryId;
	private long cultivationId;
	private long responsibilityEmployeeId;
	private float registrationAcreage;
	private int householdMembers;
	private int exp;
	private int labors;
	private int soilType;
	private float cultivationAcreage;
	private String memo;

	private int isCancel;

	private Farmer farmer;
	private Company company;
	private Employee responsibilityEmployee;
	private ObServer observer;

	private List<CropMaterial> cropMaterials;
	private List<CropMaterialSupplement> cropMaterialSupplements;

	private List<CropDryingOven> dryingOvens;

	public List<CropMaterial> getCropMaterials() {
		if (cropMaterials != null) {
			return cropMaterials;
		}
		CropSessionService service;
		try {
			service = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
			cropMaterials = service.getAllCropMaterialByRegistrationId(id)
					.stream()
					.filter(cropMaterial -> cropMaterial.getDelFlag() == 0)
					.collect(Collectors.toList());
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}
		return cropMaterials;
	}

	public CropRegistration setCropMaterials(List<CropMaterial> cropMaterials) {
		this.cropMaterials = cropMaterials;
		return this;
	}

	public Farmer getFarmer() {
		FarmerService service;
		try {
			service = ApplicationContextProvider.getApplicationContext().getBean(FarmerServiceImpl.class);
			farmer = service.getFarmerById(farmerId);
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}
		return farmer;
	}

	public CropRegistration setFarmer(Farmer farmer) {
		this.farmer = farmer;
		return this;
	}

	public Company getCompany() {
		try {
			final CompanyService service = ApplicationContextProvider.getApplicationContext().getBean(CompanyServiceImpl.class);
			company = service.getCompanyById(companyId);
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}
		return company;
	}

	public CropRegistration setCompany(Company company) {
		this.company = company;
		return this;
	}

	public Employee getResponsibilityEmployee() {
		try {
			final EmployeeService service = ApplicationContextProvider.getApplicationContext().getBean(EmployeeServiceImpl.class);
			responsibilityEmployee = service.getEmployeeById(responsibilityEmployeeId);
		} catch (NullPointerException | BeansException | BindingException | NoSuchElementException e) {
			logger.error(e);
		}
		return responsibilityEmployee;
	}

	public CropRegistration setResponsibilityEmployee(Employee responsibilityEmployee) {
		this.responsibilityEmployee = responsibilityEmployee;
		return this;
	}

	public ObServer getObserver() {
		try {
			final ObServerService service = ApplicationContextProvider.getApplicationContext().getBean(ObServerServiceImpl.class);
			observer = service.getObServerByCompanyIdAndCultivationId(companyId, zoneId);
		} catch (NullPointerException | BeansException | BindingException | NoSuchElementException e) {
			logger.error(e);
		}
		return observer;
	}

	public CropRegistration setObserver(ObServer observer) {
		this.observer = observer;
		return this;
	}

	public List<CropMaterialSupplement> getCropMaterialSupplements() {
		if (cropMaterialSupplements != null) return cropMaterialSupplements;

		CropSessionService service;
		try {
			service = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
			cropMaterialSupplements = service.getAllCropMaterialSupplementByRegistrationId(id)
					.stream()
					.filter(cropMaterialSupplement -> cropMaterialSupplement.getDelFlag() == 0)
					.collect(Collectors.toList());
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}
		return cropMaterialSupplements;
	}

	public CropRegistration setCropMaterialSupplements(List<CropMaterialSupplement> cropMaterialSupplements) {
		this.cropMaterialSupplements = cropMaterialSupplements;
		return this;
	}

	public List<CropDryingOven> getDryingOvens() {
		if (dryingOvens != null) return dryingOvens;

		CropSessionService service;
		try {
			service = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
			dryingOvens = service.getAllCropDryingOvenByRegistraionId(id)
					.stream()
					.collect(Collectors.toList());
		} catch (NullPointerException | BeansException e) {
			logger.error(e);
		}
		return dryingOvens;
	}

	public CropRegistration setDryingOvens(List<CropDryingOven> dryingOvens) {
		this.dryingOvens = dryingOvens;
		return this;
	}

	public long getId() {
		return id;
	}

	public CropRegistration setId(long id) {
		this.id = id;
		return this;
	}

	public long getFarmerId() {
		return farmerId;
	}

	public CropRegistration setFarmerId(long farmerId) {
		this.farmerId = farmerId;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public CropRegistration setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public long getDepartmentId() {
		return departmentId;
	}

	public CropRegistration setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
		return this;
	}

	public long getDivisionId() {
		return divisionId;
	}

	public CropRegistration setDivisionId(long divisionId) {
		this.divisionId = divisionId;
		return this;
	}

	public long getCropId() {
		return cropId;
	}

	public CropRegistration setCropId(long cropId) {
		this.cropId = cropId;
		return this;
	}

	public long getZoneId() {
		return zoneId;
	}

	public CropRegistration setZoneId(long zoneId) {
		this.zoneId = zoneId;
		return this;
	}

	public long getNurseryId() {
		return nurseryId;
	}

	public CropRegistration setNurseryId(long nurseryId) {
		this.nurseryId = nurseryId;
		return this;
	}

	public long getCultivationId() {
		return cultivationId;
	}

	public CropRegistration setCultivationId(long cultivationId) {
		this.cultivationId = cultivationId;
		return this;
	}

	public int getExp() {
		return exp;
	}

	public CropRegistration setExp(int exp) {
		this.exp = exp;
		return this;
	}

	public long getResponsibilityEmployeeId() {
		return responsibilityEmployeeId;
	}

	public CropRegistration setResponsibilityEmployeeId(long responsibilityEmployeeId) {
		this.responsibilityEmployeeId = responsibilityEmployeeId;
		return this;
	}

	public int getSoilType() {
		return soilType;
	}

	public CropRegistration setSoilType(int soilType) {
		this.soilType = soilType;
		return this;
	}

	public float getRegistrationAcreage() {
		return registrationAcreage;
	}

	public CropRegistration setRegistrationAcreage(float registrationAcreage) {
		this.registrationAcreage = registrationAcreage;
		return this;
	}

	public int getHouseholdMembers() {
		return householdMembers;
	}

	public CropRegistration setHouseholdMembers(int householdMembers) {
		this.householdMembers = householdMembers;
		return this;
	}

	public int getLabors() {
		return labors;
	}

	public CropRegistration setLabors(int labors) {
		this.labors = labors;
		return this;
	}

	public float getCultivationAcreage() {
		return cultivationAcreage;
	}

	public CropRegistration setCultivationAcreage(float cultivationAcreage) {
		this.cultivationAcreage = cultivationAcreage;
		return this;
	}

	public String getMemo() {
		return memo;
	}

	public CropRegistration setMemo(String memo) {
		this.memo = memo;
		return this;
	}

	public int getIsCancel() {
		return isCancel;
	}

	public CropRegistration setIsCancel(int isCancel) {
		this.isCancel = isCancel;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CropRegistration registration = (CropRegistration) target;

		if (registration.getFarmerId() < 1) {
			errors.reject(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "crop_registration", "farmerId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "farmerId"));
		}

		if (registration.getCompanyId() < 1) {
			errors.reject(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "crop_registration", "companyId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "companyId"));
		}

		if (registration.getCropId() < 1) {
			errors.reject(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "crop_registration", "cropId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "cropId"));
		}

		if (registration.getZoneId() < 1) {
			errors.reject(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "crop_registration", "zoneId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "zoneId"));
		}

		if (registration.getResponsibilityEmployeeId() < 1) {
			errors.reject(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "crop_registration", "responsibilityEmployeeId"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "responsibilityEmployeeId"));
		}

		if (registration.getRegistrationAcreage() <= 0) {
			errors.reject(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "crop_registration", "registrationAcreage"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "registrationAcreage"));
		}
		if (registration.getHouseholdMembers() < 1) {
			errors.reject(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "crop_registration", "householdMembers"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "householdMembers"));
		}
		if (registration.getLabors() < 1) {
			errors.reject(PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "crop_registration", "labors"),
					PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "labors"));
		}
	}

	@Override
	public String toString() {
		return "CropRegistration{" +
				"id=" + id +
				", farmerId=" + farmerId +
				", companyId=" + companyId +
				", departmentId=" + departmentId +
				", divisionId=" + divisionId +
				", cropId=" + cropId +
				", zoneId=" + zoneId +
				", responsibilityEmployeeId=" + responsibilityEmployeeId +
				", registrationAcreage=" + registrationAcreage +
				", householdMembers=" + householdMembers +
				", exp=" + exp +
				", labors=" + labors +
				", soilType=" + soilType +
				", cultivationAcreage=" + cultivationAcreage +
				", memo='" + memo + '\'' +
				'}';
	}

	/**
	 * Insert new crop material.
	 *
	 * @param mapper a {@link MultiValueMap} object.
	 * @return a {@link CropMaterial} object.
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws ValidationException
	 */
	public CropMaterial insertNewCropMaterial(MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, ValidationException {
		final CropSessionService service = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
		final CropMaterial material = new CropMaterial();

		material.setRegistrationId(id)
				.setCompanyId(companyId)
				.setCreateId(responsibilityEmployeeId)
				.setUpdateId(responsibilityEmployeeId)
				.copyPropertiesInListFromMapper(mapper, "registrationId", "materialId", "status", "quantity");

		service.createCropMaterial(material);

		return material;
	}

	/**
	 * Update crop material.
	 *
	 * @param mapper a {@link MultiValueMap} object.
	 * @return a {@link CropMaterial} object.
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws ValidationException
	 */
	public CropMaterial updateCropMaterial(long cropMaterialId, MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, ValidationException {
		final CropSessionService service = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
		final CropMaterial material = service.getCropMaterialById(cropMaterialId, id);

		material.setRegistrationId(id)
				.setUpdateId(responsibilityEmployeeId)
				.copyPropertiesInListFromMapper(mapper, "status", "quantity");

		service.updateCropMaterial(material);
		return material;
	}

	/**
	 * Insert new crop drying oven.
	 *
	 * @param mapper a {@link MultiValueMap} object.
	 * @return a {@link CropMaterial} object.
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws ValidationException
	 */
	public CropDryingOven insertNewDryingOven(MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, ValidationException {
		final CropSessionService service = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
		final CropDryingOven cropDryingOven = new CropDryingOven();

		cropDryingOven.setRegistrationId(id)
				.setCreateId(responsibilityEmployeeId)
				.setUpdateId(responsibilityEmployeeId)
				.copyPropertiesInListFromMapper(mapper, "length", "width", "height", "purlins",
						"useHusk", "useNaturalFirewood", "useGrownFirewood", "useCoal", "useOther", "quantity");
		service.createCropDryingOven(cropDryingOven);
		service.updateCropRegistration(this);
		return cropDryingOven;
	}

	/**
	 * Update exist crop drying oven.
	 *
	 * @param cropDryingOvenId a {@link Long} cropDryingOvenId.
	 * @param mapper           a {@link MultiValueMap} object.
	 * @return a {@link CropMaterial} object.
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws ValidationException
	 */
	public CropDryingOven updateCropDryingOven(long cropDryingOvenId, MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, ValidationException {
		final CropSessionService service = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
		final CropDryingOven cropDryingOven = service.getCropDryingOvenById(cropDryingOvenId, id);

		cropDryingOven.setRegistrationId(id)
				.setUpdateId(responsibilityEmployeeId)
				.copyPropertiesInListFromMapper(mapper, "length", "width", "height", "purlins",
						"useHusk", "useNaturalFirewood", "useGrownFirewood", "useCoal", "useOther", "quantity");

		service.updateCropDryingOven(cropDryingOven);

		return cropDryingOven;
	}

	/**
	 * Insert new crop material supplement.
	 *
	 * @param mapper a {@link MultiValueMap} object.
	 * @return a {@link CropMaterial} object.
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws ValidationException
	 */
	public CropMaterialSupplement insertNewCropMaterialSupplement(MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, ValidationException {
		final CropSessionService service = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
		final CropMaterialSupplement material = new CropMaterialSupplement();

		material.setRegistrationId(id)
				.setCreateId(responsibilityEmployeeId)
				.setUpdateId(responsibilityEmployeeId)
				.copyPropertiesInListFromMapper(mapper, "materialId", "actionId", "quantity", "phase", "memo");

		service.createCropMaterialSupplement(material);

		return material;
	}

	/**
	 * Insert new crop material supplements.
	 *
	 * @param materials a {@link MultiValueMap} object.
	 * @return a {@link CropMaterial} object.
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws ValidationException
	 */
	public List<CropMaterialSupplement> insertNewCropMaterialSupplements(List<CropMaterialSupplement> materials) throws NullPointerException, BeansException, ValidationException {
		final CropSessionService service = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);

		materials.stream().forEach(m -> m.setRegistrationId(id)
				.setCreateId(responsibilityEmployeeId)
				.setUpdateId(responsibilityEmployeeId)
		);

		service.createCropMaterialSupplements(materials);
		return materials;
	}

	/**
	 * Update crop material.
	 *
	 * @param mapper a {@link MultiValueMap} object.
	 * @return a {@link CropMaterial} object.
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws ValidationException
	 */
	public CropMaterialSupplement updateCropMaterialSupplement(long cropMaterialId, MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, ValidationException {
		final CropSessionService service = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
		final CropMaterialSupplement material = service.getCropMaterialSupplementById(cropMaterialId, id);

		material.setRegistrationId(id)
				.setUpdateId(responsibilityEmployeeId)
				.copyPropertiesInListFromMapper(mapper, "actionId", "quantity", "phase", "memo");

		service.updateCropMaterialSupplement(material);
		return material;
	}

	/**
	 * Update crop materials.
	 *
	 * @param materials a {@link MultiValueMap} object.
	 * @return a {@link CropMaterial} object.
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws ValidationException
	 */
	public List<CropMaterialSupplement> updateCropMaterialSupplements(List<CropMaterialSupplement> materials) throws NullPointerException, BeansException, ValidationException {
		final CropSessionService service = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);

		for (CropMaterialSupplement m : materials) {
			m.setUpdateId(responsibilityEmployeeId);
			service.updateCropMaterialSupplement(m);
		}

		return materials;
	}

	/**
	 * Insert new Nursery.
	 *
	 * @param nursery a {@link Nursery} object.
	 * @param account a {@link Account} object.
	 * @return a {@link Nursery} object.
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws ValidationException
	 */
	public Nursery insertNewNursery(Nursery nursery, Account account) throws NullPointerException, BeansException, ValidationException {
		if (nurseryId > 0) throw new ValidationException("nursery_exists_code", "Nursery have been created.");

		final NurseryService service = ApplicationContextProvider.getApplicationContext().getBean(NurseryServiceImpl.class);
		final CropSessionService sessionService = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);

		nursery.setRegistrationId(id)
				.setScenario(Nursery.SCENARIO.INSERT)
				.setCreateId(account.getId())
				.setUpdateId(account.getId());

		service.createNursery(nursery);

		this.nurseryId = nursery.getId();
		sessionService.updateCropRegistration(this, false);
		return nursery;
	}

	/**
	 * Update new Nursery.
	 *
	 * @param mapper  a {@link MultiValueMap} object.
	 * @param account a {@link Account} object.
	 * @return a {@link Nursery} object.
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws ValidationException
	 */
	public Object updateNursery(MultiValueMap<String, String> mapper, Account account) throws NullPointerException, BeansException, ValidationException {
		if (nurseryId < 1) throw new NoSuchElementException();

		final NurseryService service = ApplicationContextProvider.getApplicationContext().getBean(NurseryServiceImpl.class);
		final CropSessionService sessionService = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);
		final Nursery nursery = service.getNurseryById(nurseryId, id);

		nursery.setRegistrationId(id)
				.setScenario(Nursery.SCENARIO.UPDATE)
				.setUpdateId(account.getId())
				.copyPropertiesInListFromMapper(mapper,
						"acreage", "density", "address", "provinceId",
						"districtId", "wardId", "seedingDate", "exchangeDate",
						"exchangeRatio", "skinRatio", "trayRatio", "quantity", "delFlag", "seedId"
				);

		if (nursery.getDelFlag() != 0 && nursery.getAcreage() > 0) {
			throw new ValidationException("can_not_delete_nursery", "Can not delete nursery");
		}
		service.updateNursery(nursery);
		if (nursery.getDelFlag() != 0) {
			this.nurseryId = 0;
		}
		sessionService.updateCropRegistration(this, false);

		return nursery;
	}

	/**
	 * Find nursery.
	 *
	 * @param nurseryId long
	 * @return a {@link Nursery} object
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws ValidationException
	 */
	@JsonIgnore
	public Nursery findNursery(long nurseryId) throws NullPointerException, BeansException, ValidationException {
		final NurseryService service = ApplicationContextProvider.getApplicationContext().getBean(NurseryServiceImpl.class);
		return service.getNurseryById(nurseryId, id);
	}

	/**
	 * Server side sync all registration detail info.
	 *
	 * @param account an {@link Account} object
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws BindingException
	 * @throws ValidationException
	 */
	public void serverSyncRegistrationData(Account account) throws NullPointerException, BindingException, BeansException, ValidationException {
		final List<ObjectError> errors = new ArrayList<>();

		try {
			errors.addAll(this.getErrors());
			errors.addAll(farmer.getErrors());

			cropMaterials.forEach(cropMaterial -> {
				if (cropMaterial.getId() < 1) {
					cropMaterial.setRegistrationId(id)
							.setCompanyId(companyId)
							.setCreateId(account.getId())
							.setUpdateId(account.getId());
				} else {
					cropMaterial.setUpdateId(account.getId());
				}
				errors.addAll(cropMaterial.getErrors());
			});

			dryingOvens.forEach(dryingOven -> {
				dryingOven.setUpdateId(account.getId());
				if (dryingOven.getId() < 1) {
					dryingOven.setRegistrationId(id)
							.setCreateId(account.getId());
				}
				errors.addAll(dryingOven.getErrors());
			});

			cropMaterialSupplements.forEach(materialSupplement -> {
				if (materialSupplement.getId() < 1) {
					materialSupplement.setRegistrationId(id)
							.setCreateId(account.getId())
							.setUpdateId(account.getId());
				} else {
					materialSupplement.setUpdateId(account.getId());
				}
				errors.addAll(materialSupplement.getErrors());
			});
		} catch (NullPointerException | NoSuchElementException e) {
			logger.error(e);
		}

		if (!errors.isEmpty()) {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}

		// Do update
		farmer.save(account);

		final CropSessionService service = ApplicationContextProvider.getApplicationContext().getBean(CropSessionServiceImpl.class);

		service.updateCropRegistration(this, false);

		for (CropMaterial material : cropMaterials) {
			if (material.getId() < 1) {
				service.createCropMaterial(material, true);
			} else {
				service.updateCropMaterial(material, false);
			}
		}

		if (dryingOvens != null) {
			for (CropDryingOven dryingOven : dryingOvens) {
				if (dryingOven.getDelFlag() != 0) {
					if (dryingOven.getId() > 0) {
						service.updateCropDryingOven(dryingOven, false);
					}
				}
			}
			for (CropDryingOven dryingOven : dryingOvens) {
				if (dryingOven.getDelFlag() == 0) {
					if (dryingOven.getId() < 1) {
						service.createCropDryingOven(dryingOven, false);
					} else {
						service.updateCropDryingOven(dryingOven, false);
					}
				}
			}
		}

		if (cropMaterialSupplements != null)
			for (CropMaterialSupplement materialSupplement : cropMaterialSupplements) {
				if (materialSupplement.getId() < 1) {
					service.createCropMaterialSupplement(materialSupplement, false);
				} else {
					service.updateCropMaterialSupplement(materialSupplement, false);
				}
			}
	}
}