package vn.azteam.tabasupport.core.cache;

import vn.azteam.tabasupport.core.object.model.Account;

/**
 * package vn.azteam.tabasupport.core.cache
 * created 12/27/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface ResetPasswordTokenCache {
	/**
	 * Cache account
	 *
	 * @param account an {@link Account} object
	 * @return a {@link String} uuid
	 */
	String cache(Account account);

	/**
	 * Get cached object
	 *
	 * @param uuid a {@link String} uuid
	 * @return
	 */
	Account getCached(String uuid);

	/**
	 * Remove cached object
	 *
	 * @param uuid a {@link String} uuid
	 */
	void remove(String uuid);

	/**
	 * Clean expired cached Object
	 */
	void clean();
}
