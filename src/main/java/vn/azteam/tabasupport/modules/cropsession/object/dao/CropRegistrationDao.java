package vn.azteam.tabasupport.modules.cropsession.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropRegistration;

import java.util.List;

/**
 * package vn.azteam.tabasupport.modules.cropsession.object.dao
 * created 1/16/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface CropRegistrationDao {
	/**
	 * Insert new crop registration.
	 *
	 * @param registration a {@link CropRegistration} object
	 */
	void insertCropRegistration(@Param("registration") CropRegistration registration);

	/**
	 * Update crop registration.
	 *
	 * @param registration a {@link CropRegistration} object
	 */
	void updateRegistration(@Param("registration") CropRegistration registration);

	/**
	 * Get all crop registration.
	 *
	 * @return a {@link List} object.
	 */
	List<CropRegistration> findAll();

	/**
	 * Get all crop registration belong to company.
	 *
	 * @param companyIds a {@link java.lang.reflect.Array}
	 * @return a {@link List} object.
	 */
	List<CropRegistration> findAllByCompanyIds(@Param("companyIds") long[] companyIds);

	/**
	 * Get all crop registration belong to company in cropSessionId.
	 *
	 * @param companyIds a {@link java.lang.reflect.Array}
	 * @return a {@link List} object.
	 */
	List<CropRegistration> findAllInCropByCompanyIds(@Param("companyIds") long[] companyIds, @Param("cropSessionId") long cropSessionId);
}