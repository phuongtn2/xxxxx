package vn.azteam.tabasupport.modules.cropsession.service;

import org.apache.ibatis.binding.BindingException;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.modules.cropsession.object.model.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.cropsession.service
 * created 1/16/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface CropSessionService {
	/**
	 * Add new crop sesion
	 *
	 * @param crop a {@link Crop} object
	 * @return a {@link Long} cropId
	 * @throws ValidationException
	 */
	long createCropSession(Crop crop) throws ValidationException;


	/**
	 * Get all crop sesion
	 *
	 * @return a {@link List} object
	 */
	List<Crop> getAll();

	/**
	 * Get Current crop session.
	 *
	 * @return a {@link Crop} object.
	 * @throws NullPointerException
	 * @throws NoSuchElementException
	 */
	Crop getCurrentCropSession() throws NullPointerException, NoSuchElementException;

	/**
	 * Get Current crop session id.
	 *
	 * @return cropId
	 * @throws NullPointerException
	 * @throws NoSuchElementException
	 */
	long getCurrentCropSessionId() throws NullPointerException, NoSuchElementException;

	/**
	 * Filter crop by name.
	 *
	 * @param crops a {@link List} crops
	 * @param str   a {@link String} filter
	 */
	void filterByCropName(List<? extends Crop> crops, String str);

	/**
	 * Get copb by Id.
	 *
	 * @param cropId a {@link Long} cropId
	 * @return a {@link Crop} object
	 * @throws NoSuchElementException
	 */
	Crop getCropById(long cropId) throws NoSuchElementException;

	/**
	 * Update exists crop sesion
	 *
	 * @param crop a {@link Crop} object
	 * @return a {@link Long} cropId
	 * @throws ValidationException
	 */
	long updateCrop(Crop crop) throws ValidationException;

	/**
	 * Add new crop registration.
	 *
	 * @param registration a {@link Crop} object
	 * @return a {@link Long} registrationId
	 * @throws ValidationException
	 */
	long createCropRegistration(CropRegistration registration) throws ValidationException;

	/**
	 * Add new crop registration.
	 *
	 * @param registration     a {@link Crop} object
	 * @param withoutValidated a {@link Boolean} var
	 * @return a {@link Long} registrationId
	 * @throws ValidationException
	 */
	long createCropRegistration(CropRegistration registration, boolean withoutValidated) throws ValidationException;

	/**
	 * Get All crop registration.
	 *
	 * @return a {@link List} object
	 */
	List<CropRegistration> getAllCropRegistration();

	/**
	 * Get All crop registration belong to companys.
	 *
	 * @param companyIds {@link Long} companyIds
	 * @return a {@link List} object
	 * @throws NullPointerException
	 * @throws NoSuchElementException
	 */
	List<CropRegistration> getAllCropRegistrationInCurrentCropByCompanyIds(long[] companyIds) throws NullPointerException, NoSuchElementException;

	/**
	 * Get All crop registration belong to company.
	 *
	 * @param companyId {@link Long} companyId
	 * @return a {@link List} object
	 */
	List<CropRegistration> getAllCropRegistrationInCurrentCropByCompanyId(long companyId) throws NullPointerException, NoSuchElementException;

	/**
	 * Get All crop registration by Roles.
	 *
	 * @param companyId {@link Long} companyId
	 * @param account   {@link Account} account
	 * @return a {@link List} object
	 */
	List<CropRegistration> getAllCropRegistrationInCurrentCropByRoles(long companyId, Account account) throws NullPointerException, NoSuchElementException;

	/**
	 * Get crop registration by id.
	 *
	 * @param registrationId a {@link Long} registrationId
	 * @return a {@link CropRegistration} object
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	CropRegistration getCropRegistrationById(long registrationId) throws BindingException, NoSuchElementException;

	/**
	 * Get crop registration by id.
	 *
	 * @param registrationId a {@link Long} registrationId
	 * @param companyId      a {@link Long} companyId
	 * @return a {@link CropRegistration} object
	 * @throws NullPointerException
	 * @throws NoSuchElementException
	 */
	CropRegistration getCropRegistrationById(long registrationId, long companyId) throws BindingException, NoSuchElementException;

	/**
	 * Update exists crop registration.
	 *
	 * @param registration a {@link CropRegistration} object
	 * @return updateId
	 * @throws BindingException
	 * @throws ValidationException
	 */
	long updateCropRegistration(CropRegistration registration) throws BindingException, ValidationException;

	/**
	 * Update exists crop registration.
	 *
	 * @param registration     a {@link CropRegistration} object
	 * @param withoutValidated a {@link Boolean} object
	 * @return updateId
	 * @throws BindingException
	 * @throws ValidationException
	 */
	long updateCropRegistration(CropRegistration registration, boolean withoutValidated) throws BindingException, ValidationException;

	/**
	 * Create new Crop Material.
	 *
	 * @param material a {@link CropMaterial} object
	 * @return cropMaterialId
	 * @throws ValidationException
	 */
	long createCropMaterial(CropMaterial material) throws ValidationException;

	/**
	 * Create new Crop Material.
	 *
	 * @param material         a {@link CropMaterial} object
	 * @param withoutValidated a {@link Boolean} var
	 * @return cropMaterialId
	 * @throws ValidationException
	 */
	long createCropMaterial(CropMaterial material, boolean withoutValidated) throws ValidationException;

	/**
	 * Get all Crop Material by companyId.s
	 *
	 * @param companyIds companyIds
	 * @return cropMaterialId
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	List<CropMaterial> getAllCropMaterialByCompanyIds(long[] companyIds) throws BindingException, NoSuchElementException;

	/**
	 * Get all Crop Material by registrationIds.
	 *
	 * @param registrationIds registrationIds
	 * @return a {@link List} object.
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	List<CropMaterial> getAllCropMaterialByRegistrationIds(long[] registrationIds) throws BindingException, NoSuchElementException;

	/**
	 * Get all Crop Material by registrationId.
	 *
	 * @param registrationId registrationId
	 * @return a {@link List} object.
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	List<CropMaterial> getAllCropMaterialByRegistrationId(long registrationId) throws BindingException, NoSuchElementException;

	/**
	 * Get Crop Material by id.
	 *
	 * @param cropMaterialId cropMaterialId
	 * @param registrationId registrationId
	 * @return a {@link CropMaterial} object.
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	CropMaterial getCropMaterialById(long cropMaterialId, long registrationId) throws BindingException, NoSuchElementException;


	/**
	 * update Crop material.
	 *
	 * @param material {@link CropMaterial} object
	 * @return a long
	 * @throws BindingException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	long updateCropMaterial(CropMaterial material) throws BindingException, NoSuchElementException, ValidationException;

	/**
	 * update Crop material.
	 *
	 * @param material         {@link CropMaterial} object
	 * @param withoutValidated {@link Boolean} var
	 * @return a long
	 * @throws BindingException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	long updateCropMaterial(CropMaterial material, boolean withoutValidated) throws BindingException, NoSuchElementException, ValidationException;

	/**
	 * Create new crop drying oven.
	 *
	 * @param cropDryingOven a {@link CropDryingOven} object
	 * @return cropDryingOvenId
	 * @throws BindingException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	long createCropDryingOven(CropDryingOven cropDryingOven) throws BindingException, NoSuchElementException, ValidationException;

	/**
	 * Create new crop drying oven.
	 *
	 * @param cropDryingOven a {@link CropDryingOven} object
	 * @return cropDryingOvenId
	 * @throws BindingException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	long createCropDryingOven(CropDryingOven cropDryingOven, boolean withoutValidate) throws BindingException, NoSuchElementException, ValidationException;

	/**
	 * update Crop Drying Oven.
	 *
	 * @param cropDryingOven {@link CropDryingOven} object
	 * @return a long
	 * @throws BindingException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	long updateCropDryingOven(CropDryingOven cropDryingOven) throws BindingException, NoSuchElementException, ValidationException;

	/**
	 * update Crop Drying Oven.
	 *
	 * @param cropDryingOven   {@link CropDryingOven} object
	 * @param withoutValidated {@link Boolean} var
	 * @return a long
	 * @throws BindingException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	long updateCropDryingOven(CropDryingOven cropDryingOven, boolean withoutValidated) throws BindingException, NoSuchElementException, ValidationException;

	/**
	 * Get All crop drying oven by registrationIds
	 *
	 * @param registrationIds long array
	 * @return a {@link List} object
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	List<CropDryingOven> getAllCropDryingOvenByRegistraionIds(long[] registrationIds) throws BindingException, NoSuchElementException;

	/**
	 * Get All crop drying oven by registrationId
	 *
	 * @param registrationId long
	 * @return a {@link List} object
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	List<CropDryingOven> getAllCropDryingOvenByRegistraionId(long registrationId) throws BindingException, NoSuchElementException;

	/**
	 * Get Crop Material by id.
	 *
	 * @param registrationId registrationId
	 * @return a {@link CropDryingOven} object.
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	CropDryingOven getCropDryingOvenById(long cropDryingOvenId, long registrationId) throws BindingException, NoSuchElementException;

	/**
	 * Create new Crop Material supplement.
	 *
	 * @param material a {@link CropMaterialSupplement} object
	 * @return cropMaterialSupplementId
	 * @throws ValidationException
	 */
	long createCropMaterialSupplement(CropMaterialSupplement material) throws ValidationException;

	/**
	 * Create new Crop Material supplement.
	 *
	 * @param material         a {@link CropMaterialSupplement} object
	 * @param withoutValidated a {@link Boolean} var
	 * @return cropMaterialSupplementId
	 * @throws ValidationException
	 */
	long createCropMaterialSupplement(CropMaterialSupplement material, boolean withoutValidated) throws ValidationException;

	/**
	 * Create new Crop Material supplements.
	 *
	 * @param materials a {@link CropMaterialSupplement} object
	 * @throws NullPointerException
	 * @throws ValidationException
	 */
	void createCropMaterialSupplements(List<CropMaterialSupplement> materials) throws NullPointerException, ValidationException;

	/**
	 * update Crop material supplement.
	 *
	 * @param material {@link CropMaterialSupplement} object
	 * @return a long
	 * @throws BindingException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	long updateCropMaterialSupplement(CropMaterialSupplement material) throws BindingException, NoSuchElementException, ValidationException;

	/**
	 * update Crop material supplement.
	 *
	 * @param material         {@link CropMaterialSupplement} object
	 * @param withoutValidated {@link Boolean} var
	 * @return a long
	 * @throws BindingException
	 * @throws NoSuchElementException
	 * @throws ValidationException
	 */
	long updateCropMaterialSupplement(CropMaterialSupplement material, boolean withoutValidated) throws BindingException, NoSuchElementException, ValidationException;


	/**
	 * Get all Crop Material supplement by registrationIds.
	 *
	 * @param registrationIds registrationIds
	 * @return a {@link List} object.
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	List<CropMaterialSupplement> getAllCropMaterialSupplementByRegistrationIds(long[] registrationIds) throws BindingException, NoSuchElementException;

	/**
	 * Get all Crop Material supplement by registrationId.
	 *
	 * @param registrationId registrationId
	 * @return a {@link List} object.
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	List<CropMaterialSupplement> getAllCropMaterialSupplementByRegistrationId(long registrationId) throws BindingException, NoSuchElementException;

	/**
	 * Get Crop Material supplement by id.
	 *
	 * @param cropMaterialId cropMaterialId
	 * @param registrationId registrationId
	 * @return a {@link CropMaterial} object.
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	CropMaterialSupplement getCropMaterialSupplementById(long cropMaterialId, long registrationId);

	/**
	 * Get Crop material Supplements by actionId
	 *
	 * @param actionId long
	 * @param phase    String
	 * @return a {@link List}
	 */
	List<CropMaterialSupplement> getAllCropMaterialSupplementByActionId(long actionId, String phase);

	/**
	 * Create new crop media.
	 *
	 * @param media a {@link CropMedia}
	 * @return cropMediaId
	 * @throws ValidationException
	 */
	long createCropMedia(CropMedia media) throws ValidationException;

	/**
	 * Create new crop media.
	 *
	 * @param media            a {@link CropMedia}
	 * @param withoutValidated a {@link Boolean}
	 * @return cropMediaId
	 * @throws ValidationException
	 */
	long createCropMedia(CropMedia media, boolean withoutValidated) throws ValidationException;

	/**
	 * getMedia.
	 *
	 * @param actionId a {@link CropMedia}
	 * @param phase    a {@link Boolean}
	 * @param delFlag  int
	 * @return a {@link List} object
	 */
	List<CropMedia> getMedia(long actionId, String phase, int delFlag);

	/**
	 * Update Crop media.
	 *
	 * @param media a {@link CropMedia} obj
	 * @return cropMediaId
	 * @throws ValidationException
	 */
	long updateCropMedia(CropMedia media) throws ValidationException;

	/**
	 * Update Crop media.
	 *
	 * @param media            a {@link CropMedia} obj
	 * @param withoutValidated a {@link Boolean}
	 * @return cropMediaId
	 * @throws ValidationException
	 */
	long updateCropMedia(CropMedia media, boolean withoutValidated) throws ValidationException;

	void filterCropRegistrationIsCancel(List<CropRegistration> cropRegistrations, int isCancel);

	void filterCropRegistrationByFarmerName(List<CropRegistration> cropRegistrations, String farmerFullName);

	void filterCropRegistrationByPhone(List<CropRegistration> cropRegistrations, String phone);

	void filterCropRegistrationByProvinceId(List<CropRegistration> cropRegistrations, int provinceId);

	void filterCropRegistrationByDistrictId(List<CropRegistration> cropRegistrations, int districtId);

	void filterCropRegistrationByWardId(List<CropRegistration> cropRegistrations, int wardId);

	void filterCropRegistrationByFarmerCode(List<CropRegistration> cropRegistrations, String farmerCode);
}
