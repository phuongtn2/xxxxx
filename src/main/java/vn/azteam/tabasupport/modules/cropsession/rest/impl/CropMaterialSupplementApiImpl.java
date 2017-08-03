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
import vn.azteam.tabasupport.modules.cropsession.object.model.CropMaterialSupplement;
import vn.azteam.tabasupport.modules.cropsession.object.model.CropRegistration;
import vn.azteam.tabasupport.modules.cropsession.rest.CropMaterialSupplementApi;
import vn.azteam.tabasupport.modules.cropsession.service.CropSessionService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.modules.cropsession.rest.impl
 * created 1/19/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseRestImpl
 * @see CropMaterialSupplementApi
 * @since 1.0.0
 */
@Component
public class CropMaterialSupplementApiImpl extends BaseRestImpl implements CropMaterialSupplementApi {
	@Autowired
	private CropSessionService cropSessionService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object addMaterial(OAuth2Authentication auth, @PathVariable long registrationId, @RequestBody MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, NoSuchElementException, ValidationException {
		final Account account = (Account) auth.getPrincipal();
		final CropRegistration registration = cropSessionService.getCropRegistrationById(registrationId, account.getCompanyId());
		return new SimpleResponse(new Object[]{registration.insertNewCropMaterialSupplement(mapper)});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object addMaterials(OAuth2Authentication auth, @PathVariable long registrationId, @RequestBody List<CropMaterialSupplement> materials) throws NullPointerException, BeansException, NoSuchElementException, ValidationException {
		final Account account = (Account) auth.getPrincipal();
		final CropRegistration registration = cropSessionService.getCropRegistrationById(registrationId, account.getCompanyId());
		return new SimpleResponse(new Object[]{registration.insertNewCropMaterialSupplements(materials)});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object updateMaterial(OAuth2Authentication auth, @PathVariable long registrationId, @PathVariable long cropMaterialId, @RequestBody MultiValueMap<String, String> mapper) throws NullPointerException, BeansException, NoSuchElementException, ValidationException {
		final Account account = (Account) auth.getPrincipal();
		final CropRegistration registration = cropSessionService.getCropRegistrationById(registrationId, account.getCompanyId());
		return new SimpleResponse(new Object[]{registration.updateCropMaterialSupplement(cropMaterialId, mapper)});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object updateMaterials(OAuth2Authentication auth, @PathVariable long registrationId, @RequestBody List<CropMaterialSupplement> materials) throws NullPointerException, BeansException, NoSuchElementException, ValidationException {
		final Account account = (Account) auth.getPrincipal();
		final CropRegistration registration = cropSessionService.getCropRegistrationById(registrationId, account.getCompanyId());
		return new SimpleResponse(new Object[]{registration.updateCropMaterialSupplements(materials)});
	}
}
