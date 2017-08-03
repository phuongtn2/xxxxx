package vn.azteam.tabasupport.modules.cropsession.rest.impl;

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
import vn.azteam.tabasupport.modules.cropsession.rest.CropMaterialApi;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;

import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.cropsession.rest.impl
 * created 1/17/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseRestImpl
 * @see CropMaterialApi
 * @since 1.0.0
 */
@Component
public class CropMaterialApiImpl extends BaseRestImpl implements CropMaterialApi {
	@Autowired
	private CropSessionService cropSessionService;

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object addMaterial(OAuth2Authentication auth, @PathVariable long registrationId, @RequestBody MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, NoSuchElementException, ValidationException {
		final Account account = (Account) auth.getPrincipal();
		final CropRegistration registration = cropSessionService.getCropRegistrationById(registrationId, account.getCompanyId());
		return new SimpleResponse(new Object[]{registration.insertNewCropMaterial(mapper)});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object updateMaterial(OAuth2Authentication auth, @PathVariable long registrationId, @PathVariable long cropMaterialId, @RequestBody MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, NoSuchElementException, ValidationException {
		final Account account = (Account) auth.getPrincipal();
		final CropRegistration registration = cropSessionService.getCropRegistrationById(registrationId, account.getCompanyId());
		return new SimpleResponse(new Object[]{registration.updateCropMaterial(cropMaterialId, mapper)});
	}
}
