package vn.azteam.tabasupport.modules.zone.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.zone.rest.CompanyZoneApi;
import vn.azteam.tabasupport.modules.zone.service.CompanyZoneService;

/**
 * Package vn.azteam.tabasupport.modules.zone.rest.impl
 * Created 3/6/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@Component
public class CompanyZoneApiImpl extends BaseRestImpl implements CompanyZoneApi {

	@Autowired
	private CompanyZoneService companyZoneService;

	@Override
	public Object getAllZoneByCompanyId(OAuth2Authentication auth) {
		Account account = (Account) auth.getPrincipal();
		if (account.getCompanyId() > 1) {
			return companyZoneService.getAllCultivationZoneByCompanyId(account.getCompanyId());
		} else {
			return companyZoneService.getAllCultivationZone();
		}
	}
}
