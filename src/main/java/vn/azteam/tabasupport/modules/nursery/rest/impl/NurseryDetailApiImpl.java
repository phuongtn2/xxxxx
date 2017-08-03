package vn.azteam.tabasupport.modules.nursery.rest.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.SimpleResponse;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropRegistration;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;
import vn.azteam.tabasupport.modules.nursery.object.model.Nursery;
import vn.azteam.tabasupport.modules.nursery.object.model.NurseryDetail;
import vn.azteam.tabasupport.modules.nursery.rest.NurseryDetailApi;
import vn.azteam.tabasupport.modules.nursery.service.NurseryDetailService;
import vn.azteam.tabasupport.modules.nursery.service.NurseryService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.nursery.rest.impl
 * created 1/20/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseRestImpl
 * @see NurseryDetailApi
 * @since 1.0.0
 */
@Component
public class NurseryDetailApiImpl extends BaseRestImpl implements NurseryDetailApi {
	@Autowired
	private CropSessionService cropSessionService;

	@Autowired
	private NurseryService nurseryService;

	@Autowired
	private NurseryDetailService nurseryDetailService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object addNurseryDetail(HttpServletRequest request, OAuth2Authentication auth, @PathVariable long registrationId, @PathVariable long nurseryId, @PathVariable String action, @RequestBody MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, NoSuchElementException, ValidationException, NoSuchRequestHandlingMethodException {
		final Account account = (Account) auth.getPrincipal();
		final CropRegistration registration = cropSessionService.getCropRegistrationById(registrationId, account.getCompanyId());

		switch (NurseryDetail.ACTION.findAction(action)) {
			case LEAF_CUTTING:
				return new SimpleResponse(new Object[]{registration.findNursery(nurseryId).insertNewDetailLeafCutting(account, mapper)});
			case PESTICIDES_SPRAYING:
				return new SimpleResponse(new Object[]{});
			default:
				throw new NoSuchRequestHandlingMethodException(request);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object updateNurseryDetail(HttpServletRequest request, OAuth2Authentication auth, @PathVariable long registrationId, @PathVariable long nurseryId, @PathVariable String action, @PathVariable long detailId, @RequestBody MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, NoSuchElementException, ValidationException, NoSuchRequestHandlingMethodException {
		final Account account = (Account) auth.getPrincipal();
		final CropRegistration registration = cropSessionService.getCropRegistrationById(registrationId, account.getCompanyId());
		final Nursery nursery = registration.findNursery(nurseryId);

		switch (NurseryDetail.ACTION.findAction(action)) {
			case LEAF_CUTTING:
				return new SimpleResponse(new Object[]{nursery.updateDetailLeafCutting(account, detailId, mapper)});
			case PESTICIDES_SPRAYING:
				return new SimpleResponse(new Object[]{});
			default:
				throw new NoSuchRequestHandlingMethodException(request);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getNurseryDetails(HttpServletRequest request, OAuth2Authentication auth,
	                                @PathVariable long registrationId, @PathVariable long nurseryId)
			throws NullPointerException, BeansException, NoSuchElementException, ValidationException {
		final Map<String, String[]> paramsMapper = request.getParameterMap();
		final Account account = (Account) auth.getPrincipal();
		final CropRegistration registration = cropSessionService.getCropRegistrationById(registrationId, account.getCompanyId());

		final List<NurseryDetail> details = registration.findNursery(nurseryId).getDetails();

		// TODO filter result
//		paramsMapper.forEach((param, values) -> {
//			switch (param) {
//				case "name":
//					String val = values.length > 0 ? values[0] : "";
//					if (!val.isEmpty()) {
//						cropSessionService.filterByCropName(cropList, val);
//					}
//					break;
//			}
//		});

		//paging
		return paging(request, details);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getNurseryDetail(HttpServletRequest request, OAuth2Authentication auth,
	                               @PathVariable long registrationId, @PathVariable long nurseryId, @PathVariable long detailId)
			throws NullPointerException, BeansException, NoSuchElementException, ValidationException {
		final Account account = (Account) auth.getPrincipal();
		final Nursery nursery = cropSessionService.getCropRegistrationById(registrationId, account.getCompanyId())
				.findNursery(nurseryId);

		return new SimpleResponse(new Object[]{nursery.findDetail(detailId)});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object clientSync(OAuth2Authentication auth) throws NullPointerException, BeansException, NoSuchElementException, ValidationException {
		final Account account = (Account) auth.getPrincipal();
		final long[] nurseryIds = nurseryService.getAllNurseryByResponsibilityEmployeeId(account.getEmployee().getId())
				.stream()
				.mapToLong(nursery -> nursery.getId())
				.toArray();

		if (nurseryIds == null || nurseryIds.length < 1) return new Object[]{};

		return nurseryDetailService.getAllByNurseryIds(nurseryIds);
	}
}
