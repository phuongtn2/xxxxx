package vn.azteam.tabasupport.core.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import vn.azteam.tabasupport.core.object.model.Account;

import java.util.HashMap;

/**
 * package vn.azteam.tabasupport.core.service.impl
 * created 12/14/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see TokenEnhancer
 * @since 1.0.0
 */
public class OAuth2TokenEnhancerImpl implements TokenEnhancer {
	private final Logger logger = LogManager.getLogger(getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		final Account account = (Account) authentication.getPrincipal();
		final HashMap<String, Object> additionalInformation = new HashMap<String, Object>();

		additionalInformation.put("username", account.getUserId());
		additionalInformation.put("roles", account.getRoles());
		additionalInformation.put("fullName", account.getFullName());
		additionalInformation.put("companyId", account.getCompanyId());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);

		return accessToken;
	}
}
