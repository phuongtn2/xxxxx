package vn.azteam.tabasupport.modules.farmer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.User;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.farmer.object.dao.FarmerDao;
import vn.azteam.tabasupport.modules.farmer.object.dao.FarmerRelativeDao;
import vn.azteam.tabasupport.modules.farmer.object.model.Farmer;
import vn.azteam.tabasupport.modules.farmer.object.model.FarmerRelative;
import vn.azteam.tabasupport.modules.farmer.service.FarmerService;
import vn.azteam.tabasupport.modules.user.service.UserService;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Package vn.azteam.tabasupport.modules.farmer.service.impl
 * Created 12/26/2016
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@Service
public class FarmerServiceImpl extends BaseServiceImpl implements FarmerService {

	@Autowired
	private FarmerDao farmerDao;

	@Autowired
	private FarmerRelativeDao farmerRelativeDao;

	@Autowired
	private UserService userService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createFarmer(Farmer farmer) throws ValidationException {
		List<ObjectError> errors = farmer.getErrors();

		if (!errors.isEmpty()) {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}

		try {
			createTransaction();
			long userId = userService.createUser(farmer);
			farmerDao.insertFarmer(farmer.setuId(userId));
			transactionManager.commit(status);
		} catch (ValidationException | DuplicateKeyException e) {
			transactionManager.rollback(status);
			logger.error(e);
			throw new ValidationException(PropertiesParserUtil.getProperty("default.exception.duplicate.entry.code"),
					PropertiesParserUtil.parsePropertyByFormat("default.exception.duplicate.entry.msg", "farmerCode"));
		}
		return farmer.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateFarmer(Farmer farmer) throws ValidationException {
		List<ObjectError> errors = farmer.getErrors();
		if (errors.isEmpty()) {
			try {
				createTransaction();
				final User user = User.newInstanceFromChild(farmer, User.class);
				userService.updateUser(user.setId(farmer.getuId()));
				farmerDao.updateFarmer(farmer);
				transactionManager.commit(status);
			} catch (Exception e) {
				logger.error(e);
				transactionManager.rollback(status);
				throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"),
						PropertiesParserUtil.getProperty("validation.request.default.msg"));
			}
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Farmer> getAllFarmer() {
		return farmerDao.findAllFarmer();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Farmer getFarmerById(long farmerId) {
		Farmer farmer = null;
		try {
			farmer = getAllFarmer().stream().filter(
					farmer1 -> farmer1.getId() == farmerId
			).findFirst().get();
		} catch (NoSuchElementException e) {
			logger.error(e);
		}
		return farmer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createRelative(FarmerRelative relative, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			createFarmerRelative(relative);
			return relative.getId();
		}

		List<ObjectError> errors = relative.getErrors();
		if (errors.isEmpty()) {
			createFarmerRelative(relative);
			return relative.getId();
		}
		throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createRelative(FarmerRelative relative) throws ValidationException {
		return createRelative(relative, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateRelative(FarmerRelative relative, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			updateFarmerRelative(relative);
			return relative.getId();
		}

		List<ObjectError> errors = relative.getErrors();
		if (errors.isEmpty()) {
			updateFarmerRelative(relative);
			return relative.getId();
		}
		throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateRelative(FarmerRelative relative) throws ValidationException {
		return updateRelative(relative, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<FarmerRelative> getAllByFarmerId(long farmerId) {
		return farmerRelativeDao.findAllByFarmerId(farmerId);
	}

	@Override
	public void filterFarmerByFullName(List<Farmer> farmers, String fullName) {
		farmers.removeIf(
				farmer -> !farmer.getFullName().toLowerCase().matches(String.format(".*%s.*", fullName).toString())
		);
	}

	@Override
	public void filterFarmerByPhone(List<Farmer> farmers, String phone) {
		farmers.removeIf(
				farmer -> !farmer.getPhone().toLowerCase().matches(String.format(".*%s.*", phone).toString())
		);
	}

	@Override
	public void filterFarmerByProvinceId(List<Farmer> farmers, long provinceId) {
		farmers.removeIf(
				farmer -> farmer.getProvinceId() != provinceId
		);
	}

	@Override
	public void filterFarmerByDistrictId(List<Farmer> farmers, long districtId) {
		farmers.removeIf(
				farmer -> farmer.getDistrictId() != districtId
		);
	}

	@Override
	public void filterFarmerByWardId(List<Farmer> farmers, long wardId) {
		farmers.removeIf(
				farmer -> farmer.getWardId() != wardId
		);
	}

	private long createFarmerRelative(FarmerRelative relative) throws ValidationException {
		try {
			createTransaction();
			long userId = userService.createUser(relative);
			farmerRelativeDao.insert(relative.setuId(userId));
			transactionManager.commit(status);
		} catch (DuplicateKeyException e) {
			logger.error(e);
			transactionManager.rollback(status);
			throw new ValidationException(PropertiesParserUtil.getProperty("default.exception.duplicate.entry.code"),
					e.getCause().getMessage());
		} catch (Exception e) {
			logger.error(e);
			transactionManager.rollback(status);
			throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"),
					e.getCause().getMessage());
		}
		return relative.getId();
	}

	private long updateFarmerRelative(FarmerRelative relative) throws ValidationException {
		final long relativeId = relative.getId();
		relative.setId(relative.getuId());
		try {
			createTransaction();
			userService.updateUser(relative);
			relative.setId(relativeId);
			farmerRelativeDao.update(relative);
			transactionManager.commit(status);
		} catch (DuplicateKeyException e) {
			logger.error(e);
			transactionManager.rollback(status);
			throw new ValidationException(PropertiesParserUtil.getProperty("default.exception.duplicate.entry.code"),
					e.getCause().getMessage());
		} catch (Exception e) {
			logger.error(e);
			transactionManager.rollback(status);
			throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"),
					e.getCause().getMessage());
		}
		return relative.getId();
	}
}
