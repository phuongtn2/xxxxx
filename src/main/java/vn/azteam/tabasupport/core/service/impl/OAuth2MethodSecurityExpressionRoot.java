package vn.azteam.tabasupport.core.service.impl;


import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;

/**
 * package vn.azteam.tabasupport.core.service.impl
 * created 1/16/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see SecurityExpressionRoot
 * @see MethodSecurityExpressionOperations
 * @since 1.0.0
 */
public class OAuth2MethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
	private Object filterObject;
	private Object returnObject;
	private Object target;

	OAuth2MethodSecurityExpressionRoot(Authentication a) {
		super(a);
	}

	public Object getFilterObject() {
		return filterObject;
	}

	public void setFilterObject(Object filterObject) {
		this.filterObject = filterObject;
	}

	public Object getReturnObject() {
		return returnObject;
	}

	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}

	public Object getThis() {
		return target;
	}

	/**
	 * Sets the "this" property for use in expressions. Typically this will be the "this" property of
	 * the {@code JoinPoint} representing the method invocation which is being protected.
	 *
	 * @param target the target object on which the method in is being invoked.
	 */
	void setThis(Object target) {
		this.target = target;
	}

	/**
	 * Check if user have global permission
	 *
	 * @param moduleId {@link String} operation module id
	 * @param slug     {@link String} operation slug
	 * @return boolean
	 */
	public boolean hasGlobalPermission(String moduleId, String slug) {
		return (((Account) getPrincipal()).hasGlobalPermission(moduleId, slug));
	}

	/**
	 * Check if user have division manger permission
	 *
	 * @param auth {@link OAuth2Authentication}
	 * @return boolean
	 */
	public boolean hasDivisionManagerPermission(OAuth2Authentication auth) {
		try {
			final Account account = (Account) auth.getPrincipal();
			final Employee employee = account.getEmployee();
			return employee.isDivisionManager(employee.getCompanyId(), employee.getDivisionId());
		} catch (Exception e) {
		}
		return false;
	}
}
