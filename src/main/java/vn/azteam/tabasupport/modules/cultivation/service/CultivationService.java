package vn.azteam.tabasupport.modules.cultivation.service;

import org.apache.ibatis.binding.BindingException;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.modules.cultivation.object.model.Cultivation;
import vn.azteam.tabasupport.modules.cultivation.object.model.CultivationHarvest;
import vn.azteam.tabasupport.modules.cultivation.object.model.FieldPlot;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.service
 * Created 1/19/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface CultivationService {
	long insertCultivation(Cultivation cultivation) throws ValidationException;

	void updateCultivation(Cultivation cultivation) throws ValidationException;

	List<Cultivation> getAllCultivationByCompanyIds(long[] companyIds);

	List<Cultivation> getAllCultivationByCompanyId(long companyId);

	Cultivation getCultivationById(long companyId, long cultivationId) throws NoSuchElementException;

	/**
	 * Get all cultivation by responsibilityId in current crop.
	 *
	 * @param responsibilityId
	 * @return a {@link List} object
	 */
	List<Cultivation> getAllCultivationByResponsibilityEmployeeId(long responsibilityId);

	/**
	 * server sync data.
	 *
	 * @param cultivations {@link Cultivation} object.
	 * @param account      {@link Account} object.
	 * @return a {@link List} object
	 * @throws NullPointerException
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	List<Cultivation> syncList(List<Cultivation> cultivations, Account account) throws NullPointerException, BindingException, ValidationException;

	/**
	 * Create new fieldPlot.
	 *
	 * @param fieldPlot        {@link FieldPlot} obj
	 * @param withoutValidated {@link Boolean}
	 * @return fieldPlotId
	 * @throws ValidationException
	 */
	long createFieldPlot(FieldPlot fieldPlot, boolean withoutValidated) throws ValidationException;

	/**
	 * Create new fieldPlot.
	 *
	 * @param fieldPlot {@link FieldPlot} obj
	 * @return fieldPlotId
	 * @throws ValidationException
	 */
	long createFieldPlot(FieldPlot fieldPlot) throws ValidationException;

	/**
	 * Update exists fieldPlot.
	 *
	 * @param fieldPlot        {@link FieldPlot} obj
	 * @param withoutValidated {@link Boolean}
	 * @return fieldPlotId
	 * @throws ValidationException
	 */
	long updateFieldPlot(FieldPlot fieldPlot, boolean withoutValidated) throws ValidationException;

	/**
	 * Update exists fieldPlot.
	 *
	 * @param fieldPlot {@link FieldPlot} obj
	 * @return fieldPlotId
	 * @throws ValidationException
	 */
	long updateFieldPlot(FieldPlot fieldPlot) throws ValidationException;

	/**
	 * Get all fieldPlot by cultivationId.
	 *
	 * @param cultivationId long
	 * @return a {@link List} obj
	 */
	List<FieldPlot> getAllFieldPlotByCultivationId(long cultivationId);

	FieldPlot getFieldPlotById(long cultivationId, long fieldPlotId);

	/**
	 * get all field plot by registration Ids
	 *
	 * @param registrationIds long
	 * @return
	 * @throws NullPointerException
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	List<FieldPlot> getAllFieldPlotByRegistrationIds(long[] registrationIds) throws NullPointerException, BindingException, NoSuchElementException;

	/**
	 * get all field plot by registration Ids
	 *
	 * @param registrationId long
	 * @return
	 * @throws NullPointerException
	 * @throws BindingException
	 * @throws NoSuchElementException
	 */
	List<FieldPlot> getAllFieldPlotByRegistrationId(long registrationId) throws NullPointerException, BindingException, NoSuchElementException;

	/**
	 * Create new CultivationHarvest.
	 *
	 * @param cultivationHarvest {@link CultivationHarvest} obj
	 * @param withoutValidated   {@link Boolean}
	 * @return harvestId
	 * @throws ValidationException
	 */
	long createHarvest(CultivationHarvest cultivationHarvest, boolean withoutValidated) throws ValidationException;

	/**
	 * Create new CultivationHarvest.
	 *
	 * @param cultivationHarvest {@link CultivationHarvest} obj
	 * @return harvestId
	 * @throws ValidationException
	 */
	long createHarvest(CultivationHarvest cultivationHarvest) throws ValidationException;

	/**
	 * Update exists CultivationHarvest.
	 *
	 * @param cultivationHarvest {@link CultivationHarvest} obj
	 * @param withoutValidated   {@link Boolean}
	 * @return harvestId
	 * @throws ValidationException
	 */
	long updateHarvest(CultivationHarvest cultivationHarvest, boolean withoutValidated) throws ValidationException;

	/**
	 * Update exists CultivationHarvest.
	 *
	 * @param cultivationHarvest {@link CultivationHarvest} obj
	 * @return harvestId
	 * @throws ValidationException
	 */
	long updateHarvest(CultivationHarvest cultivationHarvest) throws ValidationException;

	/**
	 * Get all Cultivation harvest by cultivationId
	 *
	 * @param cultivationId long
	 * @return a {@link List}
	 */
	List<CultivationHarvest> getAllHarvestByCultivationId(long cultivationId);

	/**
	 * Get all Cultivation harvest by registrationId
	 *
	 * @param registrationId long
	 * @return a {@link List}
	 */
	List<CultivationHarvest> getAllHarvestByRegistrationId(long registrationId);
}
