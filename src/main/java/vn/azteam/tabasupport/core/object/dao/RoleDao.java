package vn.azteam.tabasupport.core.object.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.GrantedAuthority;
import vn.azteam.tabasupport.core.object.model.Role;

import java.util.Date;
import java.util.List;

/**
 * package vn.azteam.tabasupport.core.object.dao
 * created 12/13/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface RoleDao {

	/**
	 * Find all roles
	 *
	 * @return a {@link List<Role>} Object
	 */
	List<Role> findAll();

	/**
	 * Find all roles belong to Account
	 *
	 * @return a {@link List<Role>} Object
	 */
	List<Role> findAccountRoles(@Param("accountId") long accountId);

	/**
	 * Find all roles belong to Account by uId
	 *
	 * @return a {@link List<Role>} Object
	 */
	List<? extends GrantedAuthority> findAccountRolesByUid(@Param("uId") long uId);

	/**
	 * Find role by slug
	 *
	 * @return a Role {@link Object}
	 */
	Role findRoleBySlug(@Param("slug") String s);

	/**
	 * Insert to account role
	 *
	 * @param accountId a {@link Long}
	 * @param id        a {@link Long} role Id
	 * @param createId  a {@link Long} create id
	 * @param created   a {@link Date}
	 */
	void insertAccountRole(@Param("accountId") long accountId, @Param("roleId") long id, @Param("createId") long createId, @Param("created") Date created);
}
