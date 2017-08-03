package vn.azteam.tabasupport.modules.sale.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.modules.sale.object.model.SaleRegistration;
import vn.azteam.tabasupport.modules.sale.rest.SaleRegistrationApi;
import vn.azteam.tabasupport.modules.sale.service.SaleRegistrationService;

import java.util.List;

/**
 * Package vn.azteam.tabasupport.modules.sale.rest
 * Created 2/9/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see SaleRegistrationApi
 * @since 1.0.0
 */
@Component
public class SaleRegistrationApiImpl extends BaseRestImpl implements SaleRegistrationApi {

	@Autowired
	private SaleRegistrationService saleRegistrationService;

	@Override
	public Object syncGetSaleRegistration(OAuth2Authentication auth) {
		Account account = (Account) auth.getPrincipal();
		Employee employee = account.getEmployee();
		return saleRegistrationService.syncGetSaleRegistration(account.getCompanyId(), employee.getId());
	}

	@Override
	public Object syncPostSaleRegistration(OAuth2Authentication auth, @RequestBody List<SaleRegistration> saleRegistrations) throws ValidationException {
		logger.info(saleRegistrations);
		Account account = (Account) auth.getPrincipal();
		Employee employee = account.getEmployee();
		for (SaleRegistration saleRegistration : saleRegistrations) {
			saleRegistration.setProposerId(employee.getId());
			saleRegistrationService.syncPostSaleRegistration(account.getCompanyId(), saleRegistration);
		}
		return saleRegistrations;
	}

	@Override
	public Object syncGetSaleRegistration(OAuth2Authentication auth, @PathVariable long registrationId) {
		Account account = (Account) auth.getPrincipal();
		return saleRegistrationService.getAllSaleByRegistrationId(account.getCompanyId(), registrationId);
	}
}
