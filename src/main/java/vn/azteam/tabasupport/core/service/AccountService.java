package vn.azteam.tabasupport.core.service;

import org.springframework.security.core.GrantedAuthority;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.Operation;
import vn.azteam.tabasupport.core.object.model.Role;
import vn.azteam.tabasupport.modules.employee.object.model.Employee;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.core.service
 * created 12/13/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface AccountService {
	/**
	 * Get all account
	 *
	 * @return a {@link List} object
	 */
	List<Account> getAll();

	/**
	 * Get account information from Database
	 *
	 * @param id a {@link Long} account id
	 * @return a {@link Account} object
	 * @throws NullPointerException
	 */
	Account getAccountById(long id) throws NullPointerException;

	/**
	 * Get account by email address
	 *
	 * @param email {@link String} email address.
	 * @return a {@link Account} object
	 * @throws NoSuchElementException
	 */
	Account getAccountByEmail(String email) throws NoSuchElementException;

	/**
	 * Get account information from userId
	 *
	 * @param username {@link String}
	 * @return a {@link Account} object
	 * @throws NullPointerException
	 */
	Account getAccountByUserId(String username) throws NullPointerException;

	/**
	 * get account by uId
	 *
	 * @param uId a {@link Long}
	 * @return a {@link Account} object
	 * @throws NullPointerException
	 */
	Account getAccountByUid(long uId) throws NoSuchElementException;

	/**
	 * Create new account
	 *
	 * @param account  a {@link Account} object
	 * @param roleSlug a {@link String} role slug
	 * @return a {@link Long} account Id.
	 * @throws ValidationException
	 */
	long createAccount(Account account, String roleSlug) throws ValidationException;

	/**
	 * Update exists account
	 *
	 * @param account a {@link Account} object
	 * @throws ValidationException
	 */
	void updateAccount(Account account) throws ValidationException;

	/**
	 * Get account role
	 *
	 * @param accountId a {@link Long} account Id
	 * @return a {@link List} object
	 * @throws NullPointerException
	 */
	List<Role> getRoles(long accountId) throws NullPointerException;

	/**
	 * Get all roles belong to account by uid
	 *
	 * @param uId long
	 * @return a {@link List}
	 * @throws NullPointerException
	 */
	List<? extends GrantedAuthority> getRolesByUid(long uId) throws NullPointerException;

	/**
	 * Get account operations
	 *
	 * @param accountId a {@link Long} account Id
	 * @return a {@link List} object
	 * @throws NullPointerException
	 */
	List<Operation> getOperations(long accountId) throws NullPointerException;

	/**
	 * Assignee an account to a role
	 *
	 * @param accountId a {@link Long} accountId
	 * @param role      a {@link Role} role slug
	 * @param createId  a {@link Long} createId
	 * @param created   a {@link Date} created
	 * @throws NullPointerException
	 */
	void assigneeAccountToRole(long accountId, Role role, long createId, Date created) throws NullPointerException;

	/**
	 * Change account password.
	 *
	 * @param account a {@link Account} object
	 */
	void changePassword(Account account);

	/**
	 * Update push information.
	 *
	 * @param account a {@link Account} object
	 */
	void updatePushInformation(Account account);

	/**
	 * Filter by userId
	 *
	 * @param listEmployees a {@link List} object
	 * @param value         a {@link String} object
	 */
	void filterByUserId(List<? extends Employee> listEmployees, String value);

	/**
	 * Filter by userId
	 *
	 * @param listEmployees a {@link List} object
	 * @param role          a {@link String} object
	 */
	void filterByRoleSlug(List<Employee> listEmployees, String role);
}
