package vn.azteam.tabasupport.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import vn.azteam.tabasupport.core.object.dao.AccountDao;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.Operation;
import vn.azteam.tabasupport.core.object.model.Role;
import vn.azteam.tabasupport.core.object.model.User;
import vn.azteam.tabasupport.core.service.AccountService;
import vn.azteam.tabasupport.core.service.OperationService;
import vn.azteam.tabasupport.core.service.RoleService;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;
import vn.azteam.tabasupport.modules.user.service.UserService;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.core.service.impl
 * created 12/13/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseServiceImpl
 * @see AccountService
 * @since 1.0.0
 */
@Service
public class AccountServiceImpl extends BaseServiceImpl implements AccountService {
	@Autowired
	private AccountDao accountDao;

	@Autowired
	private RoleService roleService;

	@Autowired
	private OperationService operationService;

	@Autowired
	private UserService userService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Account> getAll() {
		return accountDao.finAll();
	}

	/**
	 * {@inheritDoc}
	 */
	public Account getAccountById(long id) throws NullPointerException {
		return accountDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account getAccountByEmail(String email) throws NoSuchElementException {
		return getAll().stream().filter(account -> account.getEmail().equals(email)).findFirst().get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account getAccountByUserId(String username) throws NullPointerException {
		return accountDao.findByUserId(username);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account getAccountByUid(long uId) throws NoSuchElementException {
		return accountDao.findByUid(uId);
	}

	/**
	 * {@inheritDoc}
	 */
	public long createAccount(Account account, String roleSlug) throws ValidationException {
		final List<ObjectError> errors = account.getErrors();
		final Role userRole = roleService.getRoleBySlug(roleSlug);
		if (errors.isEmpty()) {
			final long uId = userService.createUser(User.newInstanceFromChild(account, User.class));
			try {
				accountDao.insertAccount(account.setuId(uId));
			} catch (DuplicateKeyException e) {
				logger.error(e);
				throw new ValidationException(PropertiesParserUtil.getProperty("default.exception.duplicate.entry.code"),
						PropertiesParserUtil.parsePropertyByFormat("default.exception.duplicate.entry.msg", "userId"));
			}
			assigneeAccountToRole(account.getId(), userRole, account.getCreateId(), account.getCreated());
			return account.getId();
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Role> getRoles(long accountId) throws NullPointerException {
		return roleService.getAccountRoles(accountId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends GrantedAuthority> getRolesByUid(long uId) throws NullPointerException {
		return roleService.getAccountRolesByUid(uId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Operation> getOperations(long accountId) throws NullPointerException {
		final long[] roleIds = roleService.getAccountHierarchyRoleIds(accountId);
		return operationService.getOperationByRoleIds(roleIds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void assigneeAccountToRole(long accountId, Role role, long createId, Date created) throws NullPointerException {
		roleService.addAccountToRole(accountId, role, createId, created);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateAccount(Account account) throws ValidationException {
		final List<ObjectError> errors = account.getErrors();
		if (errors.isEmpty()) {
			try {
				createTransaction();
				final User user = Account.newInstanceFromChild(account, User.class);
				final long uId = account.getuId();
				userService.updateUser(user.setId(uId));
				accountDao.updateAccount(account);
				transactionManager.commit(status);
			} catch (Exception e) {
				transactionManager.rollback(status);
				throw new ValidationException("db_error_update_user", "Error update user.");
			}
		} else throw new ValidationException(errors.get(0).getCode(), errors.get(0).getDefaultMessage());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changePassword(Account account) {
		accountDao.updatePassword(account);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updatePushInformation(Account account) {
		//check existed account
		getAccountById(account.getId());
		accountDao.updatePushInformation(account);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void filterByUserId(List<? extends Employee> employees, String regex) {
		employees.removeIf(e -> {
			final Account account = getAccountByUid(e.getuId());
			return !account.getUserId().toLowerCase().matches(String.format(".*%s.*", regex).toString());
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void filterByRoleSlug(List<Employee> employees, String role) {
		employees.removeIf(e -> {
			final Account account = getAccountByUid(e.getuId());
			return !account.hasAnyRole(role);
		});
	}
}
