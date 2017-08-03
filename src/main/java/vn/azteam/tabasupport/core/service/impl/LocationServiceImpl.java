package vn.azteam.tabasupport.core.service.impl;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.azteam.tabasupport.core.object.dao.DistrictDao;
import vn.azteam.tabasupport.core.object.dao.ProvinceDao;
import vn.azteam.tabasupport.core.object.dao.WardDao;
import vn.azteam.tabasupport.core.object.model.District;
import vn.azteam.tabasupport.core.object.model.Province;
import vn.azteam.tabasupport.core.object.model.Ward;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.core.service.LocationService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * package vn.azteam.tabasupport.core.service.impl
 * created 1/23/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseRestImpl
 * @see LocationService
 * @since 1.0.0
 */
@Service
public class LocationServiceImpl extends BaseRestImpl implements LocationService {
	@Autowired
	private ProvinceDao provinceDao;

	@Autowired
	private DistrictDao districtDao;

	@Autowired
	private WardDao wardDao;


	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Province> getAllProvinces() throws BindingException, NullPointerException {
		return provinceDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Province getProvinceById(long id) throws NullPointerException, NoSuchElementException {
		return getAllProvinces()
				.stream()
				.filter(p -> p.getId() == id)
				.findFirst()
				.get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<District> getAllDistricts() throws BindingException, NullPointerException {
		return districtDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<District> getAllDistrictsByProvinceId(long provinceId) throws NoSuchElementException {
		return getAllDistricts().stream().filter(
				district -> district.getProvinceId() == provinceId
		).collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public District getDistrictById(long id) throws NullPointerException, NoSuchElementException {
		return getAllDistricts()
				.stream()
				.filter(d -> d.getId() == id)
				.findFirst()
				.get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Ward> getAllWards() throws BindingException, NullPointerException {
		return wardDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Ward> getAllWardsByDistrictId(long districtId) throws NoSuchElementException {
		return getAllWards().stream().filter(
				ward -> ward.getDistrictId() == districtId
		).collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Ward getWardById(long id) throws NullPointerException, NoSuchElementException {
		return getAllWards()
				.stream()
				.filter(w -> w.getId() == id)
				.findFirst()
				.get();
	}
}
