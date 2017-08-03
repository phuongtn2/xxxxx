package vn.azteam.tabasupport.core.service.impl;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import vn.azteam.tabasupport.core.object.model.ClientDetail;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.core.service.impl
 * created 12/14/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class OAuth2ClientDetailServiceImpl implements ClientDetailsService {
	private static List<ClientDetail> clientRepository = new ArrayList<>();

	/**
	 * Constructor
	 */
	public OAuth2ClientDetailServiceImpl() {
		final ClientDetail clientDetail = new ClientDetail(
				PropertiesParserUtil.getProperty("appClient.engineer.clientId"),
				PropertiesParserUtil.getProperty("appClient.engineer.resourceIds"),
				PropertiesParserUtil.getProperty("appClient.engineer.scoped"),
				PropertiesParserUtil.getProperty("appClient.engineer.authorizedGrantTypes"),
				PropertiesParserUtil.getIntProperty("appClient.engineer.tokenValidity"),
				PropertiesParserUtil.getIntProperty("appClient.engineer.refreshTokenValidity")
		);
		clientRepository.add(clientDetail);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		ClientDetail clientDetail;
		try {
			clientDetail = clientRepository.stream()
					.filter(detail -> detail.getClientId().equals(clientId))
					.findFirst()
					.get();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			throw new ClientRegistrationException(String.format("Client id %s not found.", clientId));
		}
		return clientDetail;
	}
}
