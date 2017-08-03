package vn.azteam.tabasupport.modules.farmer.service;

import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.farmer.object.model.Farmer;
import vn.azteam.tabasupport.modules.farmer.object.model.FarmerRelative;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.farmer.service
 * Created 12/26/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see vn.azteam.tabasupport.modules.farmer.service.impl.FarmerServiceImpl
 * @since 1.0.0
 */
public interface FarmerService {

	/**
	 * Insert a farmer
	 *
	 * @param farmer a {@link Farmer} object
	 * @return a long
	 * @throws ValidationException
	 */
	long createFarmer(Farmer farmer) throws ValidationException;

	/**
	 * Update a farmer
	 *
	 * @param farmer a {@link Farmer} object
	 * @throws ValidationException
	 */
	void updateFarmer(Farmer farmer) throws ValidationException;

	/**
	 * Get all farmer
	 *
	 * @return a {@link List<Farmer>} object
	 */
	List<Farmer> getAllFarmer();

	/**
	 * Get a farmer by id
	 *
	 * @param farmerId a long
	 * @return a {@link Farmer} object
	 */
	Farmer getFarmerById(long farmerId);

	/**
	 * Insert a farmer relative
	 *
	 * @param relative         a {@link FarmerRelative}
	 * @param withoutValidated a {@link boolean}
	 * @return long
	 * @throws ValidationException
	 */
	long createRelative(FarmerRelative relative, boolean withoutValidated) throws ValidationException;

	/**
	 * Insert a farmer relative
	 *
	 * @param relative a {@link FarmerRelative}
	 * @return long
	 * @throws ValidationException
	 */
	long createRelative(FarmerRelative relative) throws ValidationException;

	/**
	 * update a farmer relative
	 *
	 * @param relative         a {@link FarmerRelative}
	 * @param withoutValidated a {@link boolean}
	 * @return long
	 * @throws ValidationException
	 */
	long updateRelative(FarmerRelative relative, boolean withoutValidated) throws ValidationException;

	/**
	 * update a farmer relative
	 *
	 * @param relative a {@link FarmerRelative}
	 * @return long
	 * @throws ValidationException
	 */
	long updateRelative(FarmerRelative relative) throws ValidationException;

	/**
	 * get all farmer relative
	 *
	 * @param farmerId long
	 * @return a {@link FarmerRelative} object
	 */
	List<FarmerRelative> getAllByFarmerId(long farmerId);

	void filterFarmerByFullName(List<Farmer> farmers, String fullName);

	void filterFarmerByPhone(List<Farmer> farmers, String phone);

	void filterFarmerByProvinceId(List<Farmer> farmers, long provinceId);

	void filterFarmerByDistrictId(List<Farmer> farmers, long districtId);

	void filterFarmerByWardId(List<Farmer> farmers, long wardId);
}
