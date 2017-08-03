package vn.azteam.tabasupport.core.object.dao;

import org.apache.ibatis.annotations.Param;
import vn.azteam.tabasupport.core.object.model.Account;

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
public interface AccountDao {
	/**
	 * Get all account
	 *
	 * @return a {@link List} object
	 */
	List<Account> finAll();

	/**
	 * Find Account by id
	 *
	 * @param id {@link Long}
	 * @return an {@link Account} object
	 */
	Account findById(@Param("id") long id);

	/**
	 * Insert Account
	 *
	 * @param account a {@link Account} object
	 */
	void insertAccount(@Param("account") Account account);

	/**
	 * Update Account
	 *
	 * @param account a {@link Account} object
	 */
	void updateAccount(@Param("account") Account account);

	/**
	 * Update Account password
	 *
	 * @param account a {@link Account} object
	 */
	void updatePassword(@Param("account") Account account);

	/**
	 * Find Account by userId
	 *
	 * @param username {@link String}
	 * @return a {@link Account} object
	 */
	Account findByUserId(String username);

	/**
	 * Find Account by uId
	 *
	 * @param uId {@link Long}
	 * @return a {@link Account} object
	 */
	Account findByUid(@Param("uId") long uId);

	/**
	 * Update push key and device type for account
	 *
	 * @param account {@link Account} object
	 */
	void updatePushInformation(@Param("account") Account account);
}
