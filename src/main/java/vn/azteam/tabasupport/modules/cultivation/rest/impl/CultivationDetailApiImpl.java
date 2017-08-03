package vn.azteam.tabasupport.modules.cultivation.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.SimpleResponse;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.cultivation.object.model.*;
import vn.azteam.tabasupport.modules.cultivation.rest.CultivationDetailApi;
import vn.azteam.tabasupport.modules.cultivation.service.CultivationDetailService;

/**
 * Package vn.azteam.tabasupport.modules.cultivation.rest.impl
 * Created 1/17/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see CultivationDetailApi
 * @since 1.0.0
 */
@Component
public class CultivationDetailApiImpl extends BaseRestImpl implements CultivationDetailApi {

	@Autowired
	private CultivationDetailService cultivationDetailService;

	@Override
	public Object addActionManuring(OAuth2Authentication auth, @PathVariable long fieldPlotId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException {
		//TODO: check fieldPlotId
		final CultivationDetail cultivationDetail = new CultivationDetail();
		final CultivationManuring cultivationManuring = new CultivationManuring();

		cultivationDetail.insertAuthor(auth);
		cultivationManuring.insertAuthor(auth);
		cultivationManuring.setFieldPlotId(fieldPlotId);
		cultivationDetail.setFieldPlotId(fieldPlotId);
		cultivationDetail.copyPropertiesFromMapper(mapper);
		cultivationManuring.copyPropertiesFromMapper(mapper);
		long cultivationDetailId = cultivationDetailService.createActionManuring(cultivationDetail, cultivationManuring);
		cultivationDetail.setId(cultivationDetailId);
		return new SimpleResponse(new Object[]{cultivationDetail});
	}

	@Override
	public Object addActionIrrigation(OAuth2Authentication auth, @PathVariable long fieldPlotId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException {
		//TODO: Check fieldPlotId
		final CultivationDetail cultivationDetail = new CultivationDetail();
		cultivationDetail.insertAuthor(auth);
		cultivationDetail.setFieldPlotId(fieldPlotId);
		cultivationDetail.copyPropertiesFromMapper(mapper);
		long cultivationDetailId = cultivationDetailService.createActionIrrigation(cultivationDetail);
		cultivationDetail.setId(cultivationDetailId);
		return new SimpleResponse(new Object[]{cultivationDetail});
	}

	@Override
	public Object addActionCloverCutting(OAuth2Authentication auth, @PathVariable long fieldPlotId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException {
		//TODO: check fieldPlotId
		final CultivationDetail cultivationDetail = new CultivationDetail();
		final CloverCutting cloverCutting = new CloverCutting();

		cultivationDetail.insertAuthor(auth);
		cloverCutting.insertAuthor(auth);
		cultivationDetail.setFieldPlotId(fieldPlotId);
		cultivationDetail.copyPropertiesFromMapper(mapper);
		cloverCutting.copyPropertiesFromMapper(mapper);
		long cultivationDetailId = cultivationDetailService.createActionCloverCutting(cultivationDetail, cloverCutting);
		cultivationDetail.setId(cultivationDetailId);
		return new SimpleResponse(new Object[]{cultivationDetail});
	}

	@Override
	public Object addActionPestUpdate(OAuth2Authentication auth, @PathVariable long fieldPlotId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException {
		//TODO: check fieldPlotId
		final CultivationDetail cultivationDetail = new CultivationDetail();
		final CultivationPest cultivationPest = new CultivationPest();

		cultivationDetail.insertAuthor(auth);
		cultivationPest.insertAuthor(auth);
		cultivationDetail.setFieldPlotId(fieldPlotId);
		cultivationPest.setFieldPlotId(fieldPlotId);
		cultivationDetail.copyPropertiesFromMapper(mapper);
		cultivationPest.copyPropertiesFromMapper(mapper);
		long cultivationDetailId = cultivationDetailService.createActionPestUpdate(cultivationDetail, cultivationPest);
		cultivationDetail.setId(cultivationDetailId);
		return new SimpleResponse(new Object[]{cultivationDetail});
	}

	@Override
	public Object addActionPesticideSpraying(OAuth2Authentication auth, @PathVariable long fieldPlotId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException {
		//TODO: check fieldPlotId
		final CultivationDetail cultivationDetail = new CultivationDetail();
		final PesticideSpraying pesticideSpraying = new PesticideSpraying();

		cultivationDetail.insertAuthor(auth);
		pesticideSpraying.insertAuthor(auth);
		cultivationDetail.setFieldPlotId(fieldPlotId);
		pesticideSpraying.setFieldPlotId(fieldPlotId);
		cultivationDetail.copyPropertiesFromMapper(mapper);
		pesticideSpraying.copyPropertiesFromMapper(mapper);
		long cultivationDetailId = cultivationDetailService.createActionPesticideSpraying(cultivationDetail, pesticideSpraying);
		cultivationDetail.setId(cultivationDetailId);
		return new SimpleResponse(new Object[]{cultivationDetail});
	}

	@Override
	public Object addActionHarvest(OAuth2Authentication auth, @PathVariable long fieldPlotId, @RequestBody MultiValueMap<String, String> mapper) throws ValidationException {
		//TODO: check fieldPlotId
		final CultivationDetail cultivationDetail = new CultivationDetail();
		final CultivationHarvest cultivationHarvest = new CultivationHarvest();

//		cultivationDetail.insertAuthor(auth);
//		cultivationHarvest.insertAuthor(auth);
//		cultivationDetail.setFieldPlotId(fieldPlotId);
//		cultivationDetail.copyPropertiesFromMapper(mapper);
//		cultivationHarvest.copyPropertiesFromMapper(mapper);
//		long cultivationDetailId = cultivationDetailService.createActionHarvest(cultivationDetail, cultivationHarvest);
//		cultivationDetail.setId(cultivationDetailId);
		return new SimpleResponse(new Object[]{cultivationDetail});
	}
}
