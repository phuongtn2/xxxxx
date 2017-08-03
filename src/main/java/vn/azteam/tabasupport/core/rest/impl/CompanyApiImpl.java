package vn.azteam.tabasupport.core.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import vn.azteam.tabasupport.core.rest.CompanyApi;
import vn.azteam.tabasupport.core.service.CompanyService;

/**
 * Package vn.azteam.tabasupport.core.rest.impl
 * Created 4/11/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
@Component
public class CompanyApiImpl extends BaseRestImpl implements CompanyApi {

	@Autowired
	private CompanyService companyService;

	@Override
	public Object getAllCompany(OAuth2Authentication auth) {
		return companyService.getAll();
	}
}
