package vn.azteam.tabasupport.core.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.service.AccountService;

/**
 * package vn.azteam.tabasupport.core.service.impl
 * created 12/14/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see UserDetailsService
 * @since 1.0.0
 */
public class OAuth2UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private AccountService accountService;
	private Logger logger = LogManager.getLogger(getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return accountService.getAccountByUserId(username);
	}

	/**
	 * Update notification key.
	 *
	 * @param account a {@link Account} object.
	 */
	public void updatePushInformation(Account account) {
		logger.debug("AccountId:" + account.getUserId() + "; DeviceType:" + account.getDeviceType() + "; Notification key:" + account.getPushKey());
		accountService.updatePushInformation(account);
	}

}
