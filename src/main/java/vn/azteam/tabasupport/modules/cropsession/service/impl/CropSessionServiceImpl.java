package vn.azteam.tabasupport.modules.cropsession.service.impl;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.cropsession.object.dao.*;
import vn.azteam.tabasupport.modules.cropsession.object.model.*;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;
import vn.azteam.tabasupport.modules.employee.object.model.Division;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.modules.employee.service.DepartmentService;
import vn.azteam.tabasupport.modules.employee.service.DivisionService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * package vn.azteam.tabasupport.modules.cropsession.service.impl
 * created 1/16/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseServiceImpl
 * @see CropSessionService
 * @since 1.0.0
 */
@Service
public class CropSessionServiceImpl extends BaseServiceImpl implements CropSessionService {
	@Autowired
	private CropDao cropDao;

	@Autowired
	private CropRegistrationDao cropRegistrationDao;

	@Autowired
	private CropMaterialSupplementDao cropMaterialSupplementDao;

	@Autowired
	private CropMaterialDao cropMaterialDao;

	@Autowired
	private CropDryingOvenDao cropDryingOvenDao;

	@Autowired
	private CropMediaDao cropMediaDao;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private DivisionService divisionService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createCropSession(Crop crop) throws ValidationException {
		final List<ObjectError> errors = crop.getErrors();
		if (errors.isEmpty()) {
			cropDao.insertCrop(crop);
			return crop.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Crop> getAll() {
		return cropDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Crop getCurrentCropSession() throws NullPointerException, NoSuchElementException {
		return Collections.max(getAll(), new Comparator<Crop>() {
			@Override
			public int compare(Crop o1, Crop o2) {
				return Long.compare(o1.getId(), o2.getId());
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getCurrentCropSessionId() throws NullPointerException, NoSuchElementException {
		return getCurrentCropSession().getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void filterByCropName(List<? extends Crop> crops, String str) {
		crops.removeIf(crop -> !crop.getName().toLowerCase().matches(String.format(".*%s.*", str)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Crop getCropById(long cropId) throws NoSuchElementException {
		return getAll().stream().filter(crop -> crop.getId() == cropId).findFirst().get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateCrop(Crop crop) throws ValidationException {
		final List<ObjectError> errors = crop.getErrors();
		if (errors.isEmpty()) {
			cropDao.updateCrop(crop);
			return crop.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createCropRegistration(CropRegistration registration) throws ValidationException {
		return createCropRegistration(registration, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createCropRegistration(CropRegistration registration, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			cropRegistrationDao.insertCropRegistration(registration);
			return registration.getId();
		}
		final List<ObjectError> errors = registration.getErrors();
		if (errors.isEmpty()) {
			cropRegistrationDao.insertCropRegistration(registration);
			return registration.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CropRegistration> getAllCropRegistration() {
		return cropRegistrationDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CropRegistration> getAllCropRegistrationInCurrentCropByCompanyIds(long[] companyIds)
			throws NullPointerException, NoSuchElementException {
		return cropRegistrationDao.findAllInCropByCompanyIds(companyIds, getCurrentCropSessionId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CropRegistration> getAllCropRegistrationInCurrentCropByCompanyId(long companyId) {
		return getAllCropRegistrationInCurrentCropByCompanyIds(new long[]{companyId});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CropRegistration> getAllCropRegistrationInCurrentCropByRoles(long companyId, Account account) throws NullPointerException, NoSuchElementException {
		final Employee employee = account.getEmployee();

		if (employee.isDepartmentManager(companyId, employee.getDepartmentId())) {

			List<Long> departmentIds = employee.getManagedDepartment(companyId, employee.getDepartmentId());
			departmentIds.add(employee.getDepartmentId());

			return getAllCropRegistrationInCurrentCropByCompanyId(companyId)
					.stream()
					.filter(
							cropRegistration -> departmentIds.contains(cropRegistration.getDepartmentId())
					)
					.collect(Collectors.toList());
		}

		if (employee.isDivisionManager(companyId, employee.getDivisionId())) {

			List<Long> departmentIdtList = departmentService.getChildrenId(employee.getCompanyId(), employee.getDepartmentId());
			departmentIdtList.add(employee.getDepartmentId());

			List<Division> divisions = new ArrayList<>();

			long[] departmentArr = departmentIdtList.stream().mapToLong(l -> l).toArray();
			try {
				divisions = divisionService.getAllByDepartmentIds(companyId, departmentArr);
			} catch (NullPointerException | NoSuchElementException | BindingException e) {
				logger.error(e);
			}

			List<Long> divisionIdList = divisions.stream().map(Division::getId).collect(Collectors.toList());

			return getAllCropRegistrationInCurrentCropByCompanyId(companyId)
					.stream()
					.filter(
							cropRegistration -> divisionIdList.contains(cropRegistration.getDivisionId())
					)
					.collect(Collectors.toList());
		}

		return getAllCropRegistrationInCurrentCropByCompanyId(companyId)
				.stream()
				.filter(registration -> registration.getResponsibilityEmployeeId() == employee.getId())
				.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CropRegistration getCropRegistrationById(long registrationId) throws BindingException, NoSuchElementException {
		return getAllCropRegistration().stream().filter(
				registration -> registration.getId() == registrationId && registration.getIsCancel() == 0
		).findFirst().get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CropRegistration getCropRegistrationById(long registrationId, long companyId) throws BindingException, NoSuchElementException {
		return getAllCropRegistrationInCurrentCropByCompanyId(companyId).stream().filter(registration -> registration.getId() == registrationId).findFirst().get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateCropRegistration(CropRegistration registration) throws BindingException, ValidationException {
		return updateCropRegistration(registration, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateCropRegistration(CropRegistration registration, boolean withoutValidated) throws BindingException, ValidationException {
		if (withoutValidated) {
			cropRegistrationDao.updateRegistration(registration);
			return registration.getId();
		}

		final List<ObjectError> errors = registration.getErrors();
		if (errors.isEmpty()) {
			cropRegistrationDao.updateRegistration(registration);
			return registration.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createCropMaterial(CropMaterial material) throws ValidationException {
		final List<ObjectError> errors = material.getErrors();
		if (errors.isEmpty()) {
			cropMaterialDao.insertCropMaterial(material);
			return material.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createCropMaterial(CropMaterial material, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			cropMaterialDao.insertCropMaterial(material);
			return material.getId();
		}
		final List<ObjectError> errors = material.getErrors();
		if (errors.isEmpty()) {
			cropMaterialDao.insertCropMaterial(material);
			return material.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CropMaterial> getAllCropMaterialByCompanyIds(long[] companyIds) throws BindingException, NoSuchElementException {
		return cropMaterialDao.findAllByCompanyIds(companyIds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CropMaterial> getAllCropMaterialByRegistrationIds(long[] registrationIds) throws BindingException, NoSuchElementException {
		return cropMaterialDao.findAllByRegistrationIds(registrationIds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CropMaterial> getAllCropMaterialByRegistrationId(long registrationId) throws BindingException, NoSuchElementException {
		return getAllCropMaterialByRegistrationIds(new long[]{registrationId});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CropMaterial getCropMaterialById(long cropMaterialId, long registrationId) throws BindingException, NoSuchElementException {
		return getAllCropMaterialByRegistrationId(registrationId)
				.stream()
				.filter(cm -> cm.getId() == cropMaterialId && cm.getRegistrationId() == registrationId)
				.findFirst()
				.get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateCropMaterial(CropMaterial material) throws BindingException, NoSuchElementException, ValidationException {
		return updateCropMaterial(material, false);
	}

	@Override
	public long updateCropMaterial(CropMaterial material, boolean withoutValidated) throws BindingException, NoSuchElementException, ValidationException {
		if (withoutValidated) {
			cropMaterialDao.updateCropMaterial(material);
			return material.getId();
		}
		final List<ObjectError> errors = material.getErrors();
		if (errors.isEmpty()) {
			cropMaterialDao.updateCropMaterial(material);
			return material.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createCropDryingOven(CropDryingOven cropDryingOven) throws BindingException, NoSuchElementException, ValidationException {
		return createCropDryingOven(cropDryingOven, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createCropDryingOven(CropDryingOven cropDryingOven, boolean withoutValidate) throws BindingException, NoSuchElementException, ValidationException {
		if (withoutValidate) {
			cropDryingOvenDao.insertCropDryingOven(cropDryingOven);
			return cropDryingOven.getId();
		}
		final List<ObjectError> errors = cropDryingOven.getErrors();
		if (errors.isEmpty()) {
			cropDryingOvenDao.insertCropDryingOven(cropDryingOven);
			return cropDryingOven.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateCropDryingOven(CropDryingOven cropDryingOven) throws BindingException, NoSuchElementException, ValidationException {
		return updateCropDryingOven(cropDryingOven, false);
	}

	@Override
	public long updateCropDryingOven(CropDryingOven cropDryingOven, boolean withoutValidated) throws BindingException, NoSuchElementException, ValidationException {
		if (withoutValidated) {
			if (cropDryingOven.getDelFlag() == 0) {
				cropDryingOvenDao.updateCropDryingOven(cropDryingOven);
			} else {
				cropDryingOvenDao.deleteCropDryingOven(cropDryingOven);
			}
			return cropDryingOven.getId();
		}
		final List<ObjectError> errors = cropDryingOven.getErrors();
		if (errors.isEmpty()) {
			if (cropDryingOven.getDelFlag() == 0) {
				cropDryingOvenDao.updateCropDryingOven(cropDryingOven);
			} else {
				cropDryingOvenDao.deleteCropDryingOven(cropDryingOven);
			}
			return cropDryingOven.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CropDryingOven> getAllCropDryingOvenByRegistraionIds(long[] registrationIds) throws BindingException, NoSuchElementException {
		return cropDryingOvenDao.findAllByRegistrationIds(registrationIds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CropDryingOven> getAllCropDryingOvenByRegistraionId(long registrationId) throws BindingException, NoSuchElementException {
		return getAllCropDryingOvenByRegistraionIds(new long[]{registrationId});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CropDryingOven getCropDryingOvenById(long cropDryingOvenId, long registrationId) throws BindingException, NoSuchElementException {
		return getAllCropDryingOvenByRegistraionId(registrationId)
				.stream()
				.filter(cdo -> cdo.getId() == cropDryingOvenId && cdo.getRegistrationId() == registrationId)
				.findFirst()
				.get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createCropMaterialSupplement(CropMaterialSupplement material) throws ValidationException {
		return createCropMaterialSupplement(material, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createCropMaterialSupplement(CropMaterialSupplement material, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			cropMaterialSupplementDao.insert(material);
			return material.getId();
		}
		final List<ObjectError> errors = material.getErrors();
		if (errors.isEmpty()) {
			cropMaterialSupplementDao.insert(material);
			return material.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createCropMaterialSupplements(List<CropMaterialSupplement> materials) throws NullPointerException, ValidationException {
		final List<ObjectError> errors = new ArrayList<>();
		materials.stream().forEach(m -> {
			errors.addAll(m.getErrors());
		});

		if (errors.isEmpty()) {
			materials.stream().forEach(m -> {
				cropMaterialSupplementDao.insert(m);
			});
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateCropMaterialSupplement(CropMaterialSupplement material) throws BindingException, NoSuchElementException, ValidationException {
		return updateCropMaterialSupplement(material, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateCropMaterialSupplement(CropMaterialSupplement material, boolean withoutValidated) throws BindingException, NoSuchElementException, ValidationException {
		if (withoutValidated) {
			cropMaterialSupplementDao.updateCropMaterialSupplement(material);
			return material.getId();
		}
		final List<ObjectError> errors = material.getErrors();
		if (errors.isEmpty()) {
			cropMaterialSupplementDao.updateCropMaterialSupplement(material);
			return material.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CropMaterialSupplement> getAllCropMaterialSupplementByRegistrationIds(long[] registrationIds) throws BindingException, NoSuchElementException {
		return cropMaterialSupplementDao.findAllByRegistrationIds(registrationIds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CropMaterialSupplement> getAllCropMaterialSupplementByRegistrationId(long registrationId) throws BindingException, NoSuchElementException {
		return getAllCropMaterialSupplementByRegistrationIds(new long[]{registrationId});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CropMaterialSupplement getCropMaterialSupplementById(long cropMaterialId, long registrationId) {
		return getAllCropMaterialSupplementByRegistrationId(registrationId)
				.stream()
				.filter(cms -> cms.getId() == cropMaterialId && cms.getRegistrationId() == registrationId)
				.findFirst()
				.get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CropMaterialSupplement> getAllCropMaterialSupplementByActionId(long actionId, String phase) {
		return cropMaterialSupplementDao.findAllCropMaterialSupplementByActionId(actionId, phase);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createCropMedia(CropMedia media) throws ValidationException {
		return createCropMedia(media, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createCropMedia(CropMedia media, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			cropMediaDao.insert(media);
			return media.getId();
		}
		final List<ObjectError> errors = media.getErrors();
		if (errors.isEmpty()) {
			cropMediaDao.insert(media);
			return media.getId();
		}
		throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CropMedia> getMedia(long actionId, String phase, int delFlag) {
		return cropMediaDao.findAllByAction(actionId, phase, delFlag);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateCropMedia(CropMedia media) throws ValidationException {
		return updateCropMedia(media, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateCropMedia(CropMedia media, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			cropMediaDao.update(media);
			return media.getId();
		}

		final List<ObjectError> errors = media.getErrors();
		if (errors.isEmpty()) {
			cropMediaDao.update(media);
			return media.getId();
		}
		throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	@Override
	public void filterCropRegistrationIsCancel(List<CropRegistration> cropRegistrations, int isCancel) {
		cropRegistrations.removeIf(
				cropRegistration -> cropRegistration.getIsCancel() != isCancel
		);
	}

	@Override
	public void filterCropRegistrationByFarmerName(List<CropRegistration> cropRegistrations, String farmerFullName) {
		cropRegistrations.removeIf(
				cropRegistration -> !cropRegistration.getFarmer().getFullName().toLowerCase().matches(String.format(".*%s.*", farmerFullName).toString())
		);
	}

	@Override
	public void filterCropRegistrationByPhone(List<CropRegistration> cropRegistrations, String phone) {
		cropRegistrations.removeIf(
				cropRegistration -> !cropRegistration.getFarmer().getPhone().toLowerCase().matches(String.format(".*%s.*", phone).toString())
		);
	}

	@Override
	public void filterCropRegistrationByProvinceId(List<CropRegistration> cropRegistrations, int provinceId) {
		cropRegistrations.removeIf(
				cropRegistration -> cropRegistration.getFarmer().getProvinceId() != provinceId
		);
	}

	@Override
	public void filterCropRegistrationByDistrictId(List<CropRegistration> cropRegistrations, int districtId) {
		cropRegistrations.removeIf(
				cropRegistration -> cropRegistration.getFarmer().getDistrictId() != districtId
		);
	}

	@Override
	public void filterCropRegistrationByWardId(List<CropRegistration> cropRegistrations, int wardId) {
		cropRegistrations.removeIf(
				cropRegistration -> cropRegistration.getFarmer().getWardId() != wardId
		);
	}

	@Override
	public void filterCropRegistrationByFarmerCode(List<CropRegistration> cropRegistrations, String farmerCode) {
		cropRegistrations.removeIf(
				cropRegistration -> !cropRegistration.getFarmer().getFarmerCode().toLowerCase().matches(String.format(".*%s.*", farmerCode).toString())
		);
	}
}
