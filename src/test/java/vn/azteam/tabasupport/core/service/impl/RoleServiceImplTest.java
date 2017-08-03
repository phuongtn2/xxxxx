package vn.azteam.tabasupport.core.service.impl;

import mockit.Injectable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import vn.azteam.tabasupport.core.object.dao.RoleDao;
import vn.azteam.tabasupport.core.object.model.Role;

import java.util.List;

/**
 * package vn.azteam.tabasupport.core.service.impl
 * created 12/15/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public class RoleServiceImplTest {
	@Injectable
	private RoleDao roleDao;

	private RoleServiceImpl service;

	@Before
	public void setUp() throws Exception {
		service = new RoleServiceImpl();
		ReflectionTestUtils.setField(service, "roleDao", roleDao);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(expected = NullPointerException.class)
	public void getAll() throws Exception {
		List<Role> roleList = service.getAll();
	}

	@Test(expected = NullPointerException.class)
	public void getAccountRoles() throws Exception {
		long accountId = 1;
		List<Role> roleList = service.getAccountRoles(accountId);
	}

	@Test(expected = NullPointerException.class)
	public void getAccountHierarchyRoles() throws Exception {
		long accountId = 1;
		List<Role> roleList = service.getAccountHierarchyRoles(accountId);
	}

	@Test(expected = NullPointerException.class)
	public void getAccountHierarchyRoleIds() throws Exception {
		long accountId = 1;
		long[] roleIds = service.getAccountHierarchyRoleIds(accountId);
	}

}