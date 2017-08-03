package vn.azteam.tabasupport.modules.nursery.service.impl;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.service.impl.BaseServiceImpl;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropMedia;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;
import vn.azteam.tabasupport.modules.nursery.object.dao.NurseryDetailDao;
import vn.azteam.tabasupport.modules.nursery.object.dao.NurseryPestDao;
import vn.azteam.tabasupport.modules.nursery.object.dao.NurseryPesticideSprayingDao;
import vn.azteam.tabasupport.modules.nursery.object.model.NurseryDetail;
import vn.azteam.tabasupport.modules.nursery.object.model.NurseryPest;
import vn.azteam.tabasupport.modules.nursery.object.model.NurseryPesticideSpraying;
import vn.azteam.tabasupport.modules.nursery.service.NurseryDetailService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.nursery.service.impl
 * created 1/20/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseServiceImpl
 * @see NurseryDetailService
 * @since 1.0.0
 */
@Service
public class NurseryDetailServiceImpl extends BaseServiceImpl implements NurseryDetailService {
	@Autowired
	private NurseryDetailDao detailDao;

	@Autowired
	private NurseryPesticideSprayingDao sprayingDao;

	@Autowired
	private NurseryPestDao pestDao;

	@Autowired
	private CropSessionService cropSessionService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createNurseryDetail(NurseryDetail detail) throws BindingException, ValidationException {
		return createNurseryDetail(detail, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createNurseryDetail(NurseryDetail detail, boolean withoutValidate) throws BindingException, ValidationException {
		if (withoutValidate) {
			detailDao.insert(detail);
			return detail.getId();
		}

		final List<ObjectError> errors = detail.getErrors();
		if (errors.isEmpty()) {
			detailDao.insert(detail);
			return detail.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateNurseryDetail(NurseryDetail detail) throws BindingException, ValidationException {
		return updateNurseryDetail(detail, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updateNurseryDetail(NurseryDetail detail, boolean withoutValidate) throws BindingException, ValidationException {
		if (withoutValidate) {
			detailDao.update(detail);
			return detail.getId();
		}
		final List<ObjectError> errors = detail.getErrors();
		if (errors.isEmpty()) {
			detailDao.update(detail);
			return detail.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createPesticideSprayingDetail(NurseryPesticideSpraying spraying) throws BindingException, ValidationException {
		return createPesticideSprayingDetail(spraying, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createPesticideSprayingDetail(NurseryPesticideSpraying spraying, boolean withoutValidate) throws BindingException, ValidationException {
		if (withoutValidate) {
			sprayingDao.insert(spraying);
			return spraying.getId();
		}

		final List<ObjectError> errors = spraying.getErrors();
		if (errors.isEmpty()) {
			sprayingDao.insert(spraying);
			return spraying.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updatePesticideSprayingDetail(NurseryPesticideSpraying spraying, boolean withoutValidate) throws BindingException, ValidationException {
		if (withoutValidate) {
			sprayingDao.update(spraying);
			return spraying.getId();
		}
		final List<ObjectError> errors = spraying.getErrors();
		if (errors.isEmpty()) {
			sprayingDao.update(spraying);
			return spraying.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	@Override
	public long updatePesticideSprayingDetail(NurseryPesticideSpraying spraying) throws BindingException, ValidationException {
		return updatePesticideSprayingDetail(spraying, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<NurseryPesticideSpraying> getAllSprayingByDetailIds(long[] detailIds) throws BindingException {
		return sprayingDao.findAllByDetailIds(detailIds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<NurseryPesticideSpraying> getAllSprayingByDetailId(long detailId) throws BindingException {
		return getAllSprayingByDetailIds(new long[]{detailId});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createPestDetail(NurseryPest pest, boolean withoutValidate) throws BindingException, ValidationException {
		if (withoutValidate) {
			pestDao.insert(pest);
			return pest.getId();
		}
		final List<ObjectError> errors = pest.getErrors();
		if (errors.isEmpty()) {
			pestDao.insert(pest);
			return pest.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createPestDetail(NurseryPest pest) throws BindingException, ValidationException {
		return createPestDetail(pest, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updatePestDetail(NurseryPest pest, boolean withoutValidate) throws BindingException, ValidationException {
		if (withoutValidate) {
			pestDao.update(pest);
			return pest.getId();
		}
		final List<ObjectError> errors = pest.getErrors();
		if (errors.isEmpty()) {
			pestDao.update(pest);
			return pest.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long updatePestDetail(NurseryPest pest) throws BindingException, ValidationException {
		return updatePestDetail(pest, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<NurseryPest> getAllPestByDetailIds(long[] detailIds) throws NullPointerException, BindingException, NoSuchElementException {
		return pestDao.findAllByDetailIds(detailIds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<NurseryPest> getAllPestByNurseryId(long nurseryId) throws NullPointerException, BindingException, NoSuchElementException {
		return pestDao.findAllByNurseryId(nurseryId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<NurseryPest> getAllPestByDetailId(long detailId) throws NullPointerException, BindingException, NoSuchElementException {
		return getAllPestByDetailIds(new long[]{detailId});
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<NurseryDetail> getAllByNurseryIds(long[] nurseryIds) throws NullPointerException, BindingException, NoSuchElementException {
		return detailDao.findAllByNurseryIds(nurseryIds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<NurseryDetail> getAllByNurseryId(long nurseryId) throws NullPointerException, BindingException, NoSuchElementException {
		return getAllByNurseryIds(new long[]{nurseryId});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NurseryDetail getDetailLeafCutting(long detailId, long nurseryId) throws NullPointerException, BindingException, NoSuchElementException {
		return getAllByNurseryId(nurseryId)
				.stream()
				.filter(nd -> nd.getId() == detailId && nd.getAction().equals(NurseryDetail.ACTION.LEAF_CUTTING.getAction()))
				.findFirst()
				.get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NurseryDetail getDetail(long detailId, long nurseryId) throws NullPointerException, BindingException, NoSuchElementException {
		return getAllByNurseryId(nurseryId)
				.stream()
				.filter(nd -> nd.getId() == detailId)
				.findFirst()
				.get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createCropMedia(CropMedia media) throws ValidationException {
		return cropSessionService.createCropMedia(media);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long createCropMedia(CropMedia media, boolean withoutValidated) throws ValidationException {
		return cropSessionService.createCropMedia(media, withoutValidated);
	}
}
