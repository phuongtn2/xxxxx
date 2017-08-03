package vn.azteam.tabasupport.modules.cultivation.rest.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.SimpleResponse;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropRegistration;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;
import vn.azteam.tabasupport.modules.cultivation.object.model.Cultivation;
import vn.azteam.tabasupport.modules.cultivation.object.model.CultivationDetail;
import vn.azteam.tabasupport.modules.cultivation.object.model.FieldPlot;
import vn.azteam.tabasupport.modules.cultivation.rest.CultivationApi;
import vn.azteam.tabasupport.modules.cultivation.service.CultivationDetailService;
import vn.azteam.tabasupport.modules.cultivation.service.CultivationService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.rest.impl
 * Created 1/19/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseRestImpl
 * @see CultivationApi
 * @since 1.0.0
 */
@Component
public class CultivationApiImpl extends BaseRestImpl implements CultivationApi {

	@Autowired
	private CultivationService cultivationService;
	@Autowired
	private CropSessionService cropSessionService;
	@Autowired
	private CultivationDetailService cultivationDetailService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object addCultivation(OAuth2Authentication auth, @PathVariable long registrationId,
	                             @RequestBody MultiValueMap<String, String> mapper) throws ValidationException {
		final CropRegistration registration = cropSessionService.getCropRegistrationById(registrationId);
		if (registration.getCultivationId() > 0) {
			throw new ValidationException("cultivation_exists_code", "Cultivation have been created.");
		}
		final Cultivation cultivation = new Cultivation();
		cultivation.insertAuthor(auth);
		cultivation.setRegistrationId(registration.getId());
		cultivation.copyPropertiesInListFromMapper(mapper, "seedId", "address", "provinceId", "districtId", "wardId");
		long cultivationId = cultivationService.insertCultivation(cultivation);
		cultivation.setId(cultivationId);

		//Update cultivationId for registration
		registration.setCultivationId(cultivationId);
		cropSessionService.updateCropRegistration(registration);

		return new SimpleResponse(new Object[]{cultivation});
	}

	@Override
	public Object updateCultivation(OAuth2Authentication auth, @PathVariable long registrationId,
	                                @PathVariable long cultivationId, @RequestBody MultiValueMap<String, String> mapper)
			throws ValidationException {
		final Account account = (Account) auth.getPrincipal();
		final CropRegistration registration = cropSessionService.getCropRegistrationById(registrationId);
		Cultivation cultivation = cultivationService.getCultivationById(account.getCompanyId(), cultivationId);
		cultivation.copyPropertiesInListFromMapper(mapper, "seedId", "address", "provinceId", "districtId", "wardId", "delFlag");
		cultivation.setUpdateId(account.getEmployee().getId());

		if (cultivation.getDelFlag() != 0 && cultivation.getAcreage() != 0) {
			// can not delete cultivation
			throw new ValidationException("can_not_delete_cultivation", "Can not delete cultivation");
		}
		cultivationService.updateCultivation(cultivation);

		//DELETE cultivation
		if (cultivation.getDelFlag() != 0 && cultivation.getAcreage() == 0) {
			//update cultivationId for registration
			registration.setCultivationId(0);
			registration.setUpdateId(account.getEmployee().getId());
			cropSessionService.updateCropRegistration(registration);
		}
		return new SimpleResponse(new Object[]{cultivation});
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object addActionPlanting(OAuth2Authentication auth, @PathVariable long registrationId, @PathVariable long cultivationId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException {
		//check existed registrationId
		cropSessionService.getCropRegistrationById(registrationId);
		final Account account = (Account) auth.getPrincipal();
		final Cultivation cultivation = cultivationService.getCultivationById(account.getCompanyId(), cultivationId);

		//Insert Cultivation Detail, fieldPlot
		final CultivationDetail cultivationDetail = new CultivationDetail();
		final FieldPlot fieldPlot = new FieldPlot();

		cultivationDetail.insertAuthor(auth);
		fieldPlot.insertAuthor(auth);
		cultivationDetail.copyPropertiesInListFromMapper(mapper, "cultivationId", "action", "actionName", "actionIndex", "actionDate", "responsibilityEmployeeId", "memo");
		fieldPlot.copyPropertiesInListFromMapper(mapper, "name", "acreage", "densityRow", "densityColumn", "plantRowRatio", "rowPlotRatio", "leavesRatio");
		fieldPlot.setRegistrationId(registrationId).setCultivationId(cultivationId);

		long cultivationDetailId = cultivationDetailService.createActionPlanting(cultivationDetail, fieldPlot);
		cultivationDetail.setId(cultivationDetailId);

		//Update Cultivation
		cultivation.insertAuthor(auth);
		float dtThua = fieldPlot.getAcreage();
		cultivation.setAcreage(cultivation.getAcreage() + fieldPlot.getAcreage());
		float dtAll = cultivation.getAcreage();
		float hhThua = fieldPlot.getDensityRow();
		float ccThua = fieldPlot.getDensityColumn();
		float cayHang = fieldPlot.getPlantRowRatio();
		float soHangRuong = fieldPlot.getRowPlotRatio();
		int laCay = fieldPlot.getLeavesRatio();

		// HxH = (Dt Thua * HxH Thua)/dtAll
		float hh = (dtThua * hhThua) / dtAll;
		cultivation.setDensityRow(cultivation.getDensityRow() + hh);
		//CxC = (Dt Thua * CxC Thua)/dtAll
		float cc = (dtThua * ccThua) / dtAll;
		cultivation.setDensityColumn(cultivation.getDensityColumn() + cc);
		//cay/ha = (Thua cay/hang * so hang/ruong)/ Dt all
		float cayHa = (cayHang * soHangRuong) / dtAll;
		cultivation.setPlantAcreageRatio(cultivation.getPlantAcreageRatio() + cayHa);
		// trung binh la/cay  = (dtThua * la/cay)/dtAll
		float tbLaCay = (dtThua * laCay) / dtAll;
		cultivation.setLeavesRatio(cultivation.getLeavesRatio() + (int) tbLaCay);

		cultivationService.updateCultivation(cultivation);
		return new SimpleResponse(new Object[]{cultivation});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object get(OAuth2Authentication auth, @PathVariable("registrationId") String registrationId, @PathVariable long cultivationId) throws NullPointerException, BeansException, NoSuchElementException {
		final Account account = (Account) auth.getPrincipal();
		return new SimpleResponse(new Object[]{cultivationService.getCultivationById(account.getCompanyId(), cultivationId)});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object clientSync(OAuth2Authentication auth) throws NullPointerException, BeansException, NoSuchElementException, ValidationException {
		Account account = (Account) auth.getPrincipal();

		return cultivationService.getAllCultivationByResponsibilityEmployeeId(account.getEmployee().getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object serverSync(OAuth2Authentication auth, @RequestBody List<Cultivation> cultivations) throws NullPointerException, BeansException, NoSuchElementException, ValidationException {
		logger.info(cultivations);
		Account account = (Account) auth.getPrincipal();
		return cultivationService.syncList(cultivations, account);
	}
}
