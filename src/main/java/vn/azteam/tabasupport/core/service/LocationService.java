package vn.azteam.tabasupport.core.service;

import org.apache.ibatis.binding.BindingException;
import vn.azteam.tabasupport.core.object.model.District;
import vn.azteam.tabasupport.core.object.model.Province;
import vn.azteam.tabasupport.core.object.model.Ward;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.core.service
 * created 1/23/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface LocationService {
	/** Get all province
	 * @return a {@link List<Province>}
	 * @throws BindingException
	 * @throws NullPointerException
	 */
	List<Province> getAllProvinces() throws BindingException, NullPointerException;

	/**
	 * Get province by Id
	 * @param provinceId long id
	 * @return a {@link Province} object.
	 * @throws NullPointerException
	 * @throws NoSuchElementException
	 */
	Province getProvinceById(long provinceId) throws NullPointerException, NoSuchElementException;

	/**
	 * Get all district
	 * @return a {@link Object}
	 * @throws BindingException
	 * @throws NullPointerException
	 */
	List<District> getAllDistricts() throws BindingException, NullPointerException;

	/**
	 * Get all district by provinceId
	 *
	 * @param provinceId a long
	 * @return a {@link List<District>};
	 * @throws NoSuchElementException
	 */
	List<District> getAllDistrictsByProvinceId(long provinceId) throws NoSuchElementException;

	/**
	 * Get district by Id
	 * @param id long id
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws NoSuchElementException
	 */
	District getDistrictById(long id) throws NullPointerException, NoSuchElementException;

	/**
	 * Get all ward
	 * @return a {@link Object}
	 * @throws BindingException
	 * @throws NullPointerException
	 */
	List<Ward> getAllWards() throws BindingException, NullPointerException;

	/**
	 * Get all ward by districtId
	 *
	 * @param districtId a long
	 * @return a {@link List<Ward>}
	 * @throws NoSuchElementException
	 */
	List<Ward> getAllWardsByDistrictId(long districtId) throws NoSuchElementException;

	/**
	 * @param id long id
	 * @return a {@link Object}
	 * @throws NullPointerException
	 * @throws NoSuchElementException
	 */
	Ward getWardById(long id) throws NullPointerException, NoSuchElementException;
}
