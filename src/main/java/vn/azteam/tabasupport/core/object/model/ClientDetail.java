package vn.azteam.tabasupport.core.object.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

/**
 * package vn.azteam.tabasupport.core.object.model
 * created 12/14/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see ClientDetails
 * @since 1.0.0
 */
public class ClientDetail implements ClientDetails {
	private String clientId;
	private String resourceIds;
	private String scoped;
	private String authorizedGrantTypes;
	private int tokenValidity;
	private int refreshTokenValidity;

	/**
	 * ClientDetailDto constructor
	 *
	 * @param clientId             The client ID.
	 * @param resourceIds          resourceIds
	 * @param scoped               scoped
	 * @param authorizedGrantTypes authorizedGrantTypes
	 * @param tokenValidity        tokenValidity
	 * @param refreshTokenValidity refreshTokenValidity
	 */
	public ClientDetail(String clientId, String resourceIds, String scoped, String authorizedGrantTypes, int tokenValidity, int refreshTokenValidity) {
		this.clientId = clientId;
		this.resourceIds = resourceIds;
		this.scoped = scoped;
		this.authorizedGrantTypes = authorizedGrantTypes;
		this.tokenValidity = tokenValidity;
		this.refreshTokenValidity = refreshTokenValidity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getClientId() {
		return clientId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getResourceIds() {
		final String[] resourceIdsStringArr = resourceIds.split(",");
		return new HashSet<String>(Arrays.asList(resourceIdsStringArr));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSecretRequired() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getClientSecret() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isScoped() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getScope() {
		final String[] scopeStringArr = scoped.split(",");
		return new HashSet<String>(Arrays.asList(scopeStringArr));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getAuthorizedGrantTypes() {
		final String[] authorizeGrantTypesStringArr = authorizedGrantTypes.split(",");
		return new HashSet<String>(Arrays.asList(authorizeGrantTypesStringArr));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getRegisteredRedirectUri() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		final Collection<GrantedAuthority> listGrantedAuthorities = new ArrayList<GrantedAuthority>();
		return listGrantedAuthorities;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getAccessTokenValiditySeconds() {
		return tokenValidity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return refreshTokenValidity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAutoApprove(String scope) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getAdditionalInformation() {
		return null;
	}
}
