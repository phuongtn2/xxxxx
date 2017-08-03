package vn.azteam.tabasupport.modules.nursery.rest.impl;

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
import vn.azteam.tabasupport.modules.nursery.object.model.Nursery;
import vn.azteam.tabasupport.modules.nursery.rest.NurseryApi;
import vn.azteam.tabasupport.modules.nursery.service.NurseryService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.nursery.rest.impl
 * created 1/20/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseRestImpl
 * @see NurseryApi
 * @since 1.0.0
 */
@Component
public class NurseryApiImpl extends BaseRestImpl implements NurseryApi {
	@Autowired
	private NurseryService nurseryService;

	@Autowired
	private CropSessionService cropSessionService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object addNursery(OAuth2Authentication auth, @PathVariable long registrationId, @RequestBody Nursery nursery) throws NullPointerException, BeansException, NoSuchElementException, ValidationException {
		Account account = (Account) auth.getPrincipal();
		CropRegistration registration = cropSessionService.getCropRegistrationById(registrationId, account.getCompanyId());
		if (registration.getNurseryId() > 0) {
			throw new ValidationException("nursery_exists_code", "Nursery have been created.");
		}

		return new SimpleResponse(new Object[]{registration.insertNewNursery(nursery, account)});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object updateNursery(OAuth2Authentication auth, @PathVariable long registrationId, @RequestBody MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, NoSuchElementException, ValidationException {
		Account account = (Account) auth.getPrincipal();
		CropRegistration registration = cropSessionService.getCropRegistrationById(registrationId, account.getCompanyId());

		return new SimpleResponse(new Object[]{registration.updateNursery(mapper, account)});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getNursery(OAuth2Authentication auth, @PathVariable long registrationId, @PathVariable long nurseryId) throws NullPointerException, BeansException, NoSuchElementException, ValidationException {
		return new SimpleResponse(new Object[]{nurseryService.getNurseryById(nurseryId, registrationId)});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object clientSync(OAuth2Authentication auth) throws NullPointerException, BeansException, NoSuchElementException, ValidationException {
		Account account = (Account) auth.getPrincipal();

		return nurseryService.getAllNurseryByResponsibilityEmployeeId(account.getEmployee().getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object serverSync(OAuth2Authentication auth, @RequestBody List<Nursery> nurseries) throws NullPointerException, BeansException, NoSuchElementException, ValidationException {
		logger.info(nurseries);
		Account account = (Account) auth.getPrincipal();
		return nurseryService.syncList(nurseries, account);
	}
}
