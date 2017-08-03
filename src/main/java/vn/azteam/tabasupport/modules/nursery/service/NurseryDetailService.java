package vn.azteam.tabasupport.modules.nursery.service;

import org.apache.ibatis.binding.BindingException;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropMedia;
import vn.azteam.tabasupport.modules.nursery.object.model.NurseryDetail;
import vn.azteam.tabasupport.modules.nursery.object.model.NurseryPest;
import vn.azteam.tabasupport.modules.nursery.object.model.NurseryPesticideSpraying;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.nursery.service
 * created 1/20/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface NurseryDetailService {
	/**
	 * Create detail.
	 *
	 * @param detail {@link NurseryDetail} object
	 * @return nurseryDetailId
	 * @throws BindingException
	 * @throws ValidationException
	 */
	long createNurseryDetail(NurseryDetail detail) throws BindingException, ValidationException;

	/**
	 * Create detail.
	 *
	 * @param detail          {@link NurseryDetail} object
	 * @param withoutValidate {@link Boolean} object
	 * @return nurseryDetailId
	 * @throws BindingException
	 * @throws ValidationException
	 */
	long createNurseryDetail(NurseryDetail detail, boolean withoutValidate) throws BindingException, ValidationException;

	/**
	 * Update detail LeafCutting.
	 *
	 * @param detail {@link NurseryDetail} object
	 * @return nurseryDetailId
	 * @throws BindingException
	 * @throws ValidationException
	 */
	long updateNurseryDetail(NurseryDetail detail) throws BindingException, ValidationException;

	/**
	 * Update detail LeafCutting.
	 *
	 * @param detail          {@link NurseryDetail} object
	 * @param withoutValidate {@link Boolean} object
	 * @return nurseryDetailId
	 * @throws BindingException
	 * @throws ValidationException
	 */
	long updateNurseryDetail(NurseryDetail detail, boolean withoutValidate) throws BindingException, ValidationException;

	/**
	 * Create detail Pesticide Spraying.
	 *
	 * @param spraying {@link NurseryPesticideSpraying} object
	 * @return id
	 * @throws BindingException
	 * @throws ValidationException
	 */
	long createPesticideSprayingDetail(NurseryPesticideSpraying spraying) throws BindingException, ValidationException;

	/**
	 * Create detail Pesticide Spraying.
	 *
	 * @param spraying        {@link NurseryPesticideSpraying} object
	 * @param withoutValidate {@link Boolean} object
	 * @return id
	 * @throws BindingException
	 * @throws ValidationException
	 */
	long createPesticideSprayingDetail(NurseryPesticideSpraying spraying, boolean withoutValidate) throws BindingException, ValidationException;

	/**
	 * Update Pesticide spraying.
	 *
	 * @param spraying        a {@link NurseryPesticideSpraying} object
	 * @param withoutValidate boolean
	 * @return spraying id
	 * @throws BindingException
	 * @throws ValidationException
	 */
	long updatePesticideSprayingDetail(NurseryPesticideSpraying spraying, boolean withoutValidate) throws BindingException, ValidationException;

	/**
	 * Update Pesticide spraying.
	 *
	 * @param spraying a {@link NurseryPesticideSpraying} object
	 * @return spraying id
	 * @throws BindingException
	 * @throws ValidationException
	 */
	long updatePesticideSprayingDetail(NurseryPesticideSpraying spraying) throws BindingException, ValidationException;

	/**
	 * Get All Spraying By DetailIds.
	 *
	 * @param detailIds long[]
	 * @return a {@link List} Object
	 * @throws BindingException
	 */
	List<NurseryPesticideSpraying> getAllSprayingByDetailIds(long[] detailIds)
			throws BindingException;

	/**
	 * Get All Spraying By DetailIds.
	 *
	 * @param detailId long
	 * @return a {@link List} Object
	 * @throws BindingException
	 * @throws NullPointerException
	 * @throws NoSuchElementException
	 */
	List<NurseryPesticideSpraying> getAllSprayingByDetailId(long detailId)
			throws BindingException, NullPointerException, NoSuchElementException;

	/**
	 * Create new pest.
	 *
	 * @param pest            a {@link NurseryPest} obj
	 * @param withoutValidate {@link Boolean}
	 * @return nurseryPestId
	 * @throws BindingException
	 * @throws ValidationException
	 */
	long createPestDetail(NurseryPest pest, boolean withoutValidate) throws BindingException, ValidationException;

	/**
	 * Create new pest.
	 *
	 * @param pest a {@link NurseryPest} obj
	 * @return nurseryPestId
	 * @throws BindingException
	 * @throws ValidationException
	 */
	long createPestDetail(NurseryPest pest) throws BindingException, ValidationException;

	/**
	 * Update exists pest.
	 *
	 * @param pest            a {@link NurseryPest} obj
	 * @param withoutValidate {@link Boolean}
	 * @return nurseryPestId
	 * @throws BindingException
	 * @throws ValidationException
	 */
	long updatePestDetail(NurseryPest pest, boolean withoutValidate) throws BindingException, ValidationException;

	/**
	 * Update exists pest.
	 *
	 * @param pest a {@link NurseryPest} obj
	 * @return nurseryPestId
	 * @throws BindingException
	 * @throws ValidationException
	 */
	long updatePestDetail(NurseryPest pest) throws BindingException, ValidationException;

	/**
	 * Get all pests by nurseryDetailIds.
	 *
	 * @param detailIds long[]
	 * @return a {@link List} obj.
	 * @throws NullPointerException
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	List<NurseryPest> getAllPestByDetailIds(long[] detailIds) throws NullPointerException, BindingException, NoSuchElementException;

	/**
	 * Get all pests by nurseryId.
	 *
	 * @param nurseryId long
	 * @return a {@link List} obj.
	 * @throws NullPointerException
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	List<NurseryPest> getAllPestByNurseryId(long nurseryId) throws NullPointerException, BindingException, NoSuchElementException;

	/**
	 * Get all pests by nurseryDetailId.
	 *
	 * @param detailId long
	 * @return a {@link List} obj.
	 * @throws NullPointerException
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	List<NurseryPest> getAllPestByDetailId(long detailId) throws NullPointerException, BindingException, NoSuchElementException;

	/**
	 * Get All detail by nurseryIds
	 *
	 * @param nurseryIds long[]
	 * @return a {@link List} object
	 * @throws NullPointerException
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	List<NurseryDetail> getAllByNurseryIds(long[] nurseryIds) throws NullPointerException, BindingException, NoSuchElementException;

	/**
	 * Get All detail by nurseryId
	 *
	 * @param nurseryId long
	 * @return a {@link List} object
	 * @throws NullPointerException
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	List<NurseryDetail> getAllByNurseryId(long nurseryId) throws NullPointerException, BindingException, NoSuchElementException;

	/**
	 * Get detail with type LeafCutting.
	 *
	 * @param detailId  long
	 * @param nurseryId long
	 * @return a {@link NurseryDetail} object
	 * @throws NullPointerException
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	NurseryDetail getDetailLeafCutting(long detailId, long nurseryId) throws NullPointerException, BindingException, NoSuchElementException;

	/**
	 * Get detail.
	 *
	 * @param detailId  long
	 * @param nurseryId long
	 * @return a {@link NurseryDetail} object
	 * @throws NullPointerException
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	NurseryDetail getDetail(long detailId, long nurseryId) throws NullPointerException, BindingException, NoSuchElementException;

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
}
