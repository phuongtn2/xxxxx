package vn.azteam.tabasupport.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import vn.azteam.tabasupport.core.object.dao.RoleDao;
import vn.azteam.tabasupport.core.object.model.Role;
import vn.azteam.tabasupport.core.service.RoleService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * package vn.azteam.tabasupport.core.service.impl
 * created 12/15/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see RoleService
 * @since 1.0.0
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleDao roleDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Role> getAll() throws NullPointerException {
		return roleDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role getRoleBySlug(String slug) throws NullPointerException {
		return roleDao.findRoleBySlug(slug.toLowerCase());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAccountToRole(long accountId, Role role, long createId, Date created) {
		roleDao.insertAccountRole(accountId, role.getId(), createId, created);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Role> getAccountRoles(long accountId) throws NullPointerException {
		return roleDao.findAccountRoles(accountId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Role> getAccountHierarchyRoles(long accountId) throws NullPointerException {
		final List<Role> rolePool = getAll();
		final List<Role> roleList = getAccountRoles(accountId);
		final List<Role> roles = new ArrayList<>();

		roleList.forEach(role -> {
			roles.add(role);
			while (roles.get(roles.size() - 1).getParentRoleId() > 0) {
				final long pId = roles.get(roles.size() - 1).getParentRoleId();
				roles.add(rolePool.stream().filter(i -> i.getId() == pId).findFirst().get());
			}
		});

		return roles;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long[] getAccountHierarchyRoleIds(long accountId) throws NullPointerException {
		return getAccountHierarchyRoles(accountId).stream().mapToLong(role -> role.getId()).toArray();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends GrantedAuthority> getAccountRolesByUid(long uId) {
		return roleDao.findAccountRolesByUid(uId);
	}
}
