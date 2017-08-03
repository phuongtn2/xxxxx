package vn.azteam.tabasupport.core.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * package vn.azteam.tabasupport.core.service.impl
 * created 12/16/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class OAuth2RoleHierarchyImpl implements RoleHierarchy {
	final Logger logger = LogManager.getLogger(getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(Collection<? extends GrantedAuthority> collection) {
		logger.info(collection);
		return collection;
	}
}
