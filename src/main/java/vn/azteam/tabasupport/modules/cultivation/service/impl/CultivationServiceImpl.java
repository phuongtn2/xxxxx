package vn.azteam.tabasupport.modules.cultivation.service.impl;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;
import vn.azteam.tabasupport.modules.cultivation.object.dao.CultivationDao;
import vn.azteam.tabasupport.modules.cultivation.object.dao.CultivationHarvestDao;
import vn.azteam.tabasupport.modules.cultivation.object.dao.FieldPlotDao;
import vn.azteam.tabasupport.modules.cultivation.object.model.Cultivation;
import vn.azteam.tabasupport.modules.cultivation.object.model.CultivationHarvest;
import vn.azteam.tabasupport.modules.cultivation.object.model.FieldPlot;
import vn.azteam.tabasupport.modules.cultivation.service.CultivationService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.service.impl
 * Created 1/19/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseServiceImpl
 * @see CultivationService
 * @since 1.0.0
 */
@Service
public class CultivationServiceImpl extends BaseServiceImpl implements CultivationService {

	@Autowired
	private CultivationDao cultivationDao;

	@Autowired
	private FieldPlotDao fieldPlotDao;

	@Autowired
	private CultivationHarvestDao cultivationHarvestDao;

	@Autowired
	private CropSessionService cropSessionService;

	@Override
	public long insertCultivation(Cultivation cultivation) throws ValidationException {
		List<ObjectError> errors = cultivation.getErrors();
		if (errors.isEmpty()) {
			cultivationDao.insertCultivation(cultivation);
			return cultivation.getId();
		}
		throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	@Override
	public void updateCultivation(Cultivation cultivation) throws ValidationException {
		List<ObjectError> errors = cultivation.getErrors();
		if (errors.isEmpty()) {
			cultivationDao.updateCultivation(cultivation);
		} else {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
	}

	@Override
	public List<Cultivation> getAllCultivationByCompanyIds(long[] companyIds) {
		return cultivationDao.findAllCultivationByCompanyIds(companyIds);
	}

	@Override
	public List<Cultivation> getAllCultivationByCompanyId(long companyId) {
		return getAllCultivationByCompanyIds(new long[]{companyId});
	}

	@Override
	public Cultivation getCultivationById(long companyId, long cultivationId) throws NoSuchElementException {
		final Cultivation cultivation = getAllCultivationByCompanyId(companyId).stream().filter(
				cultivation1 -> cultivation1.getId() == cultivationId
		).findFirst().get();
		return cultivation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Cultivation> getAllCultivationByResponsibilityEmployeeId(long responsibilityId) {
		return cultivationDao.findAllCultivationResponsibilityEmployeeId(responsibilityId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Cultivation> syncList(List<Cultivation> cultivations, Account account) throws NullPointerException, BindingException, ValidationException {
		for (Cultivation cultivation : cultivations) {
			try {
				cultivation.save(account);
			} catch (NullPointerException | BindingException | ValidationException e) {
				throw new ValidationException("sync_error_code", e.getMessage());
			}
		}

		return cultivations;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createFieldPlot(FieldPlot fieldPlot, boolean withoutValidated) throws ValidationException {

		if (withoutValidated) {
			fieldPlotDao.insertFieldPlot(fieldPlot);
			return fieldPlot.getId();
		}
		final List<ObjectError> errors = fieldPlot.getErrors();
		if (errors.isEmpty()) {
			fieldPlotDao.insertFieldPlot(fieldPlot);
			return fieldPlot.getId();
		} else {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createFieldPlot(FieldPlot fieldPlot) throws ValidationException {
		return createFieldPlot(fieldPlot, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateFieldPlot(FieldPlot fieldPlot, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			fieldPlotDao.update(fieldPlot);
			return fieldPlot.getId();
		}
		final List<ObjectError> errors = fieldPlot.getErrors();
		if (errors.isEmpty()) {
			fieldPlotDao.update(fieldPlot);
			return fieldPlot.getId();
		} else {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateFieldPlot(FieldPlot fieldPlot) throws ValidationException {
		return updateFieldPlot(fieldPlot, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<FieldPlot> getAllFieldPlotByCultivationId(long cultivationId) {
		return fieldPlotDao.findAllByCultivationId(cultivationId);
	}

	@Override
	public FieldPlot getFieldPlotById(long cultivationId, long fieldPlotId) {
		return getAllFieldPlotByCultivationId(cultivationId).stream().filter(
				fieldPlot -> fieldPlot.getId() == fieldPlotId
		).findFirst().get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<FieldPlot> getAllFieldPlotByRegistrationIds(long[] registrationIds) throws NullPointerException, BindingException, NoSuchElementException {
		return fieldPlotDao.getAllFieldPlotByRegistrationIds(registrationIds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<FieldPlot> getAllFieldPlotByRegistrationId(long registrationId) throws NullPointerException, BindingException, NoSuchElementException {
		return getAllFieldPlotByRegistrationIds(new long[]{registrationId});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createHarvest(CultivationHarvest cultivationHarvest, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			cultivationHarvestDao.insertCultivationHarvest(cultivationHarvest);
			return cultivationHarvest.getId();
		}
		final List<ObjectError> errors = cultivationHarvest.getErrors();
		if (errors.isEmpty()) {
			cultivationHarvestDao.insertCultivationHarvest(cultivationHarvest);
			return cultivationHarvest.getId();
		} else {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createHarvest(CultivationHarvest cultivationHarvest) throws ValidationException {
		return createHarvest(cultivationHarvest, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateHarvest(CultivationHarvest cultivationHarvest, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			cultivationHarvestDao.update(cultivationHarvest);
			return cultivationHarvest.getId();
		}
		final List<ObjectError> errors = cultivationHarvest.getErrors();
		if (errors.isEmpty()) {
			cultivationHarvestDao.update(cultivationHarvest);
			return cultivationHarvest.getId();
		} else {
			throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateHarvest(CultivationHarvest cultivationHarvest) throws ValidationException {
		return updateHarvest(cultivationHarvest, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CultivationHarvest> getAllHarvestByCultivationId(long cultivationId) {
		return cultivationHarvestDao.findAllHarvestByCultivationId(cultivationId);
	}

	@Override
	public List<CultivationHarvest> getAllHarvestByRegistrationId(long registrationId) {
		return cultivationHarvestDao.findAllHarvestByRegistrationId(registrationId);
	}
}
