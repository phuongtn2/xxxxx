package vn.azteam.tabasupport.core.service;

import org.springframework.security.core.GrantedAuthority;
import vn.azteam.tabasupport.core.object.model.Role;

import java.util.Date;
import java.util.List;

/**
 * package vn.azteam.tabasupport.core.service
 * created 12/15/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see vn.azteam.tabasupport.core.service.impl.RoleServiceImpl
 * @since 1.0.0
 */
public interface RoleService {
	/**
	 * Get all roles
	 *
	 * @return a {@link List} Role Object
	 * @throws NullPointerException
	 */
	List<Role> getAll() throws NullPointerException;

	/**
	 * Get the role by slug
	 *
	 * @param slug a {@link String} role slug
	 * @return a role {@link Object} Role
	 * @throws NullPointerException
	 */
	Role getRoleBySlug(String slug) throws NullPointerException;

	/**
	 * Add account to role
	 *
	 * @param accountId a {@link Long} accountId
	 * @param role      a role {@link Object} Role
	 * @param createId  a {@link Long} Object
	 * @param created   a {@link Date} Object
	 */
	void addAccountToRole(long accountId, Role role, long createId, Date created);

	/**
	 * Get roles belong to account
	 *
	 * @param accountId a {@link Long} Object
	 * @return a {@link List} Role Object
	 * @throws NullPointerException
	 */
	List<Role> getAccountRoles(long accountId) throws NullPointerException;

	/**
	 * Get Roles hierarchy belong to Accoounr
	 *
	 * @param accountId a {@link Long} Object
	 * @return a {@link List} Role Object
	 * @throws NullPointerException
	 */
	List<Role> getAccountHierarchyRoles(long accountId) throws NullPointerException;

	/**
	 * Get Roles hierarchy belong to account
	 *
	 * @param accountId a {@link Long} Object
	 * @return a {@link List} {@link Long} roleId
	 * @throws NullPointerException
	 */
	long[] getAccountHierarchyRoleIds(long accountId) throws NullPointerException;

	/**
	 * Get all roles belong to account by uid
	 *
	 * @param uId long
	 * @return a {@link List}
	 * @throws NullPointerException
	 */
	List<? extends GrantedAuthority> getAccountRolesByUid(long uId);
}
