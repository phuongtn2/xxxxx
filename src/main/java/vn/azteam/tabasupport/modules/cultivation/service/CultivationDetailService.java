package vn.azteam.tabasupport.modules.cultivation.service;

import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.modules.cultivation.object.model.*;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.service
 * Created 1/17/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface CultivationDetailService {

	long createActionPlanting(CultivationDetail cultivationDetail, FieldPlot fieldPlot) throws ValidationException;

	long createActionManuring(CultivationDetail cultivationDetail, CultivationManuring cultivationManuring) throws ValidationException;

	long createActionIrrigation(CultivationDetail cultivationDetail) throws ValidationException;

	long createActionCloverCutting(CultivationDetail cultivationDetail, CloverCutting cloverCutting) throws ValidationException;

	long createActionPestUpdate(CultivationDetail cultivationDetail, CultivationPest cultivationPest) throws ValidationException;

	long createActionPesticideSpraying(CultivationDetail cultivationDetail, PesticideSpraying pesticideSpraying) throws ValidationException;

	/**
	 * Create new CultivationDetail.
	 *
	 * @param detail           a {@link CultivationDetail}
	 * @param withoutValidated a {@link Boolean}
	 * @return detailId
	 * @throws ValidationException
	 */
	long createDetail(CultivationDetail detail, boolean withoutValidated) throws ValidationException;

	/**
	 * Create new CultivationDetail.
	 *
	 * @param detail a {@link CultivationDetail}
	 * @return detailId
	 * @throws ValidationException
	 */
	long createDetail(CultivationDetail detail) throws ValidationException;

	/**
	 * update new CultivationDetail.
	 *
	 * @param detail           a {@link CultivationDetail}
	 * @param withoutValidated a {@link Boolean}
	 * @return detailId
	 * @throws ValidationException
	 */
	long updateDetail(CultivationDetail detail, boolean withoutValidated) throws ValidationException;

	/**
	 * update new CultivationDetail.
	 *
	 * @param detail a {@link CultivationDetail}
	 * @return detailId
	 * @throws ValidationException
	 */
	long updateDetail(CultivationDetail detail) throws ValidationException;

	/**
	 * Get all by cultivationId and fieldId.
	 *
	 * @param cultivationId long
	 * @param fieldId       long
	 * @return a {@link List}
	 */
	List<CultivationDetail> getAllByCultivationIdAndFieldPlotId(long cultivationId, long fieldId);

	/**
	 * Create new CultivationDetail.
	 *
	 * @param cloverCutting    a {@link CloverCutting}
	 * @param withoutValidated a {@link Boolean}
	 * @return cuttingId
	 * @throws ValidationException
	 */
	long createCloverCutting(CloverCutting cloverCutting, boolean withoutValidated) throws ValidationException;

	/**
	 * Create new CultivationDetail.
	 *
	 * @param cloverCutting a {@link CloverCutting}
	 * @return cuttingId
	 * @throws ValidationException
	 */
	long createCloverCutting(CloverCutting cloverCutting) throws ValidationException;

	/**
	 * update exist CultivationDetail.
	 *
	 * @param cloverCutting    a {@link CloverCutting}
	 * @param withoutValidated a {@link Boolean}
	 * @return cuttingId
	 * @throws ValidationException
	 */
	long updateCloverCutting(CloverCutting cloverCutting, boolean withoutValidated) throws ValidationException;

	/**
	 * update exist CultivationDetail.
	 *
	 * @param cloverCutting a {@link CloverCutting}
	 * @return cuttingId
	 * @throws ValidationException
	 */
	long updateCloverCutting(CloverCutting cloverCutting) throws ValidationException;

	/**
	 * Get all clover cutting by detailId
	 *
	 * @param detailId long
	 * @return a {@link List} obj
	 */
	List<CloverCutting> getAllCloverCuttingByDetailId(long detailId);

	/**
	 * Create new CultivationManuring
	 *
	 * @param cultivationManuring a {@link CultivationManuring}
	 * @param withoutValidated    {@link Boolean}
	 * @return manuringId
	 * @throws ValidationException
	 */
	long createManuring(CultivationManuring cultivationManuring, boolean withoutValidated) throws ValidationException;

	/**
	 * Create new CultivationManuring
	 *
	 * @param cultivationManuring a {@link CultivationManuring}
	 * @return manuringId
	 * @throws ValidationException
	 */
	long createManuring(CultivationManuring cultivationManuring) throws ValidationException;

	/**
	 * Update exists CultivationManuring
	 *
	 * @param cultivationManuring a {@link CultivationManuring}
	 * @param withoutValidated    {@link Boolean}
	 * @return manuringId
	 * @throws ValidationException
	 */
	long updateManuring(CultivationManuring cultivationManuring, boolean withoutValidated) throws ValidationException;

	/**
	 * Update exists CultivationManuring
	 *
	 * @param cultivationManuring a {@link CultivationManuring}
	 * @return manuringId
	 * @throws ValidationException
	 */
	long updateManuring(CultivationManuring cultivationManuring) throws ValidationException;

	/**
	 * get All  CultivationManuring by detailId
	 *
	 * @param detailId long
	 * @return a {@link List}
	 */
	List<CultivationManuring> getAllManuringByDetailId(long detailId);

	/**
	 * Create new PesticideSpraying
	 *
	 * @param pesticideSpraying a {@link PesticideSpraying}
	 * @param withoutValidated  {@link Boolean}
	 * @return id
	 * @throws ValidationException
	 */
	long createPesticideSpraying(PesticideSpraying pesticideSpraying, boolean withoutValidated) throws ValidationException;

	/**
	 * Create new PesticideSpraying
	 *
	 * @param pesticideSpraying a {@link PesticideSpraying}
	 * @return id
	 * @throws ValidationException
	 */
	long createPesticideSpraying(PesticideSpraying pesticideSpraying) throws ValidationException;

	/**
	 * Update new PesticideSpraying
	 *
	 * @param pesticideSpraying a {@link PesticideSpraying}
	 * @param withoutValidated  {@link Boolean}
	 * @return id
	 * @throws ValidationException
	 */
	long updatePesticideSpraying(PesticideSpraying pesticideSpraying, boolean withoutValidated) throws ValidationException;

	/**
	 * Update new PesticideSpraying
	 *
	 * @param pesticideSpraying a {@link PesticideSpraying}
	 * @return id
	 * @throws ValidationException
	 */
	long updatePesticideSpraying(PesticideSpraying pesticideSpraying) throws ValidationException;

	/**
	 * get All  PesticideSpraying by detailId
	 *
	 * @param detailId long
	 * @return a {@link List}
	 */
	List<PesticideSpraying> getAllPesticideSprayingByDetailId(long detailId);

	/**
	 * Create new CultivationPest
	 *
	 * @param cultivationPest  a {@link CultivationPest}
	 * @param withoutValidated {@link Boolean}
	 * @return cultivationPestId
	 * @throws ValidationException
	 */
	long createPest(CultivationPest cultivationPest, boolean withoutValidated) throws ValidationException;

	/**
	 * Create new CultivationPest
	 *
	 * @param cultivationPest a {@link CultivationPest}
	 * @return cultivationPestId
	 * @throws ValidationException
	 */
	long createPest(CultivationPest cultivationPest) throws ValidationException;

	/**
	 * Update new CultivationPest
	 *
	 * @param cultivationPest  a {@link CultivationPest}
	 * @param withoutValidated {@link Boolean}
	 * @return cultivationPestId
	 * @throws ValidationException
	 */
	long updatePest(CultivationPest cultivationPest, boolean withoutValidated) throws ValidationException;

	/**
	 * Update new CultivationPest
	 *
	 * @param cultivationPest a {@link CultivationPest}
	 * @return cultivationPestId
	 * @throws ValidationException
	 */
	long updatePest(CultivationPest cultivationPest) throws ValidationException;

	/**
	 * get All  CultivationPest by detailId
	 *
	 * @param detailId long
	 * @return a {@link List}
	 */
	List<CultivationPest> getAllPestByDetailId(long detailId);
}
