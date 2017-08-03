package vn.azteam.tabasupport.core.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.Operation;

import java.io.Serializable;
import java.util.List;

/**
 * package vn.azteam.tabasupport.core.service.impl
 * OAuth2PermissionEvaluator class.
 *
 * @author hieunc
 * @version 1.0.0
 * @since 1.0.0
 */
public class OAuth2PermissionEvaluatorImpl implements PermissionEvaluator {
	private Logger logger = LogManager.getLogger(getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		return hasPermission(authentication, authentication, targetDomainObject.toString(), permission);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		final Account account = (Account) authentication.getPrincipal();
		final List<Operation> accountOperations = (List<Operation>) account.getOperations();
		return accountOperations.stream().filter(o -> o.getModuleId().equals(targetType.toLowerCase()) && o.getSlug().equals(permission.toString().toLowerCase())).count() > 0;
	}
}
