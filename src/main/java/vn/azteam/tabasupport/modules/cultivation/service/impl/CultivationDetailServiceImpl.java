package vn.azteam.tabasupport.modules.cultivation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.cultivation.object.dao.*;
import vn.azteam.tabasupport.modules.cultivation.object.model.*;
import vn.azteam.tabasupport.modules.cultivation.service.CultivationDetailService;
import vn.azteam.tabasupport.modules.cultivation.service.constant.ActionCultivation;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.service.impl
 * Created 1/17/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see CultivationDetailService
 * @since 1.0.0
 */
@Service
public class CultivationDetailServiceImpl implements CultivationDetailService {

	@Autowired
	private CultivationDetailDao cultivationDetailDao;

	@Autowired
	private FieldPlotDao fieldPlotDao;

	@Autowired
	private CultivationManuringDao cultivationManuringDao;

	@Autowired
	private CloverCuttingDao cloverCuttingDao;

	@Autowired
	private CultivationPestDao cultivationPestDao;

	@Autowired
	private PesticideSprayingDao pesticideSprayingDao;

	@Override
	public long createActionPlanting(CultivationDetail cultivationDetail, FieldPlot fieldPlot) throws ValidationException {
		List<ObjectError> errors = cultivationDetail.getErrors();
		List<ObjectError> errorList = fieldPlot.getErrors();
		if (errors.isEmpty() && errorList.isEmpty()) {
			try {
				ActionCultivation actionCultivation = ActionCultivation.valueOf(cultivationDetail.getAction());
				if (actionCultivation.equals(ActionCultivation.PLANTING)) {
					fieldPlotDao.insertFieldPlot(fieldPlot);
					cultivationDetail.setFieldPlotId(fieldPlot.getId());
					cultivationDetailDao.insertCultivationDetail(cultivationDetail);
					return cultivationDetail.getId();
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"),
				PropertiesParserUtil.getProperty("validation.request.default.msg"));
	}

	@Override
	public long createActionManuring(CultivationDetail cultivationDetail, CultivationManuring cultivationManuring) throws ValidationException {
		List<ObjectError> errors = cultivationDetail.getErrors();
		List<ObjectError> errorList = cultivationManuring.getErrors();
		if (errors.isEmpty() && errorList.isEmpty()) {
			try {
				ActionCultivation actionCultivation = ActionCultivation.valueOf(cultivationDetail.getAction());
				if (actionCultivation.equals(ActionCultivation.MANURING)) {
					cultivationDetailDao.insertCultivationDetail(cultivationDetail);
					cultivationManuring.setActionId(cultivationDetail.getId());
					cultivationManuringDao.insertCultivationManuring(cultivationManuring);
					return cultivationDetail.getId();
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"),
				PropertiesParserUtil.getProperty("validation.request.default.msg"));
	}

	@Override
	public long createActionIrrigation(CultivationDetail cultivationDetail) throws ValidationException {
		List<ObjectError> errors = cultivationDetail.getErrors();
		if (errors.isEmpty()) {
			try {
				ActionCultivation actionCultivation = ActionCultivation.valueOf(cultivationDetail.getAction());
				if (actionCultivation.equals(ActionCultivation.IRRIGATION)) {
					cultivationDetailDao.insertCultivationDetail(cultivationDetail);
					return cultivationDetail.getId();
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"),
				PropertiesParserUtil.getProperty("validation.request.default.msg"));
	}

	@Override
	public long createActionCloverCutting(CultivationDetail cultivationDetail, CloverCutting cloverCutting) throws ValidationException {
		List<ObjectError> errors = cultivationDetail.getErrors();
		List<ObjectError> errorList = cloverCutting.getErrors();
		if (errors.isEmpty() && errorList.isEmpty()) {
			try {
				ActionCultivation actionCultivation = ActionCultivation.valueOf(cultivationDetail.getAction());
				if (actionCultivation.equals(ActionCultivation.CLOVER_CUTTING)) {
					cultivationDetailDao.insertCultivationDetail(cultivationDetail);
					cloverCutting.setActionId(cultivationDetail.getId());
					cloverCuttingDao.insertCloverCutting(cloverCutting);
					return cultivationDetail.getId();
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"),
				PropertiesParserUtil.getProperty("validation.request.default.msg"));
	}

	@Override
	public long createActionPestUpdate(CultivationDetail cultivationDetail, CultivationPest cultivationPest) throws ValidationException {
		List<ObjectError> errors = cultivationDetail.getErrors();
		List<ObjectError> errorList = cultivationPest.getErrors();
		if (errors.isEmpty() && errorList.isEmpty()) {
			try {
				ActionCultivation actionCultivation = ActionCultivation.valueOf(cultivationDetail.getAction());
				if (actionCultivation.equals(ActionCultivation.PEST_UPDATE)) {
					cultivationDetailDao.insertCultivationDetail(cultivationDetail);
					cultivationPest.setActionId(cultivationDetail.getId());
					cultivationPestDao.insertCultivationPest(cultivationPest);
					return cultivationDetail.getId();
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"),
				PropertiesParserUtil.getProperty("validation.request.default.msg"));
	}

	@Override
	public long createActionPesticideSpraying(CultivationDetail cultivationDetail, PesticideSpraying pesticideSpraying) throws ValidationException {
		List<ObjectError> errors = cultivationDetail.getErrors();
		List<ObjectError> errorList = pesticideSpraying.getErrors();
		if (errors.isEmpty() && errorList.isEmpty()) {
			try {
				ActionCultivation actionCultivation = ActionCultivation.valueOf(cultivationDetail.getAction());
				if (actionCultivation.equals(ActionCultivation.PESTICIDE_SPRAYING)) {
					cultivationDetailDao.insertCultivationDetail(cultivationDetail);
					pesticideSpraying.setActionId(cultivationDetail.getId());
					pesticideSprayingDao.insertPesticideSpraying(pesticideSpraying);
					return cultivationDetail.getId();
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"),
				PropertiesParserUtil.getProperty("validation.request.default.msg"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createDetail(CultivationDetail detail, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			cultivationDetailDao.insertCultivationDetail(detail);
			return detail.getId();
		}
		List<ObjectError> errors = detail.getErrors();
		if (errors.isEmpty()) {
			cultivationDetailDao.insertCultivationDetail(detail);
			return detail.getId();
		}
		throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createDetail(CultivationDetail detail) throws ValidationException {
		return createDetail(detail, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateDetail(CultivationDetail detail, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			cultivationDetailDao.update(detail);
			return detail.getId();
		}
		List<ObjectError> errors = detail.getErrors();
		if (errors.isEmpty()) {
			cultivationDetailDao.update(detail);
			return detail.getId();
		}
		throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateDetail(CultivationDetail detail) throws ValidationException {
		return updateDetail(detail, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CultivationDetail> getAllByCultivationIdAndFieldPlotId(long cultivationId, long fieldId) {
		return cultivationDetailDao.findAllByCultivationIdAndFieldPlotId(cultivationId, fieldId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createCloverCutting(CloverCutting cloverCutting, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			cloverCuttingDao.insertCloverCutting(cloverCutting);
			return cloverCutting.getId();
		}
		List<ObjectError> errors = cloverCutting.getErrors();
		if (errors.isEmpty()) {
			cloverCuttingDao.insertCloverCutting(cloverCutting);
			return cloverCutting.getId();
		}
		throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createCloverCutting(CloverCutting cloverCutting) throws ValidationException {
		return createCloverCutting(cloverCutting, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateCloverCutting(CloverCutting cloverCutting, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			cloverCuttingDao.update(cloverCutting);
			return cloverCutting.getId();
		}
		List<ObjectError> errors = cloverCutting.getErrors();
		if (errors.isEmpty()) {
			cloverCuttingDao.update(cloverCutting);
			return cloverCutting.getId();
		}
		throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateCloverCutting(CloverCutting cloverCutting) throws ValidationException {
		return updateCloverCutting(cloverCutting, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CloverCutting> getAllCloverCuttingByDetailId(long detailId) {
		return cloverCuttingDao.findAllByActionId(detailId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createManuring(CultivationManuring cultivationManuring, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			cultivationManuringDao.insertCultivationManuring(cultivationManuring);
			return cultivationManuring.getId();
		}
		List<ObjectError> errors = cultivationManuring.getErrors();
		if (errors.isEmpty()) {
			cultivationManuringDao.insertCultivationManuring(cultivationManuring);
			return cultivationManuring.getId();
		}
		throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createManuring(CultivationManuring cultivationManuring) throws ValidationException {
		return createManuring(cultivationManuring, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateManuring(CultivationManuring cultivationManuring, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			cultivationManuringDao.update(cultivationManuring);
			return cultivationManuring.getId();
		}
		List<ObjectError> errors = cultivationManuring.getErrors();
		if (errors.isEmpty()) {
			cultivationManuringDao.update(cultivationManuring);
			return cultivationManuring.getId();
		}
		throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateManuring(CultivationManuring cultivationManuring) throws ValidationException {
		return updateManuring(cultivationManuring, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CultivationManuring> getAllManuringByDetailId(long detailId) {
		return cultivationManuringDao.findAllByDetailId(detailId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createPesticideSpraying(PesticideSpraying pesticideSpraying, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			pesticideSprayingDao.insertPesticideSpraying(pesticideSpraying);
			return pesticideSpraying.getId();
		}
		List<ObjectError> errors = pesticideSpraying.getErrors();
		if (errors.isEmpty()) {
			pesticideSprayingDao.insertPesticideSpraying(pesticideSpraying);
			return pesticideSpraying.getId();
		}
		throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createPesticideSpraying(PesticideSpraying pesticideSpraying) throws ValidationException {
		return createPesticideSpraying(pesticideSpraying, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updatePesticideSpraying(PesticideSpraying pesticideSpraying, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			pesticideSprayingDao.update(pesticideSpraying);
			return pesticideSpraying.getId();
		}
		List<ObjectError> errors = pesticideSpraying.getErrors();
		if (errors.isEmpty()) {
			pesticideSprayingDao.update(pesticideSpraying);
			return pesticideSpraying.getId();
		}
		throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updatePesticideSpraying(PesticideSpraying pesticideSpraying) throws ValidationException {
		return updatePesticideSpraying(pesticideSpraying, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PesticideSpraying> getAllPesticideSprayingByDetailId(long detailId) {
		return pesticideSprayingDao.findAllPesticideSprayingByDetailId(detailId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createPest(CultivationPest cultivationPest, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			cultivationPestDao.insertCultivationPest(cultivationPest);
			return cultivationPest.getId();
		}
		List<ObjectError> errors = cultivationPest.getErrors();
		if (errors.isEmpty()) {
			cultivationPestDao.insertCultivationPest(cultivationPest);
			return cultivationPest.getId();
		}
		throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createPest(CultivationPest cultivationPest) throws ValidationException {
		return createPest(cultivationPest, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updatePest(CultivationPest cultivationPest, boolean withoutValidated) throws ValidationException {
		if (withoutValidated) {
			cultivationPestDao.update(cultivationPest);
			return cultivationPest.getId();
		}
		List<ObjectError> errors = cultivationPest.getErrors();
		if (errors.isEmpty()) {
			cultivationPestDao.update(cultivationPest);
			return cultivationPest.getId();
		}
		throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updatePest(CultivationPest cultivationPest) throws ValidationException {
		return updatePest(cultivationPest, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CultivationPest> getAllPestByDetailId(long detailId) {
		return cultivationPestDao.findAllPestByDetailId(detailId);
	}
}
