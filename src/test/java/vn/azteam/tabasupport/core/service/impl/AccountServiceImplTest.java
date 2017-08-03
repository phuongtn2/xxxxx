package vn.azteam.tabasupport.core.service.impl;

import mockit.Mocked;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import vn.azteam.tabasupport.core.object.dao.AccountDao;

/**
 * package vn.azteam.tabasupport.core.service.impl
 * created 12/13/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class AccountServiceImplTest {
	@Mocked
	private AccountDao accountDao;

	private AccountServiceImpl service;

	@Before
	public void setUp() throws Exception {
		service = new AccountServiceImpl();
		ReflectionTestUtils.setField(service, "accountDao", accountDao);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(expected = NullPointerException.class)
	public void getAccountById() throws Exception {
		long id = -9999;
		service.getAccountById(id);
	}

	@Test
	public void createAccount() throws Exception {

	}

}