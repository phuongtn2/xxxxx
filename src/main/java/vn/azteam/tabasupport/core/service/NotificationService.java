package vn.azteam.tabasupport.core.service;

import vn.azteam.tabasupport.core.object.model.Account;

/**
 * package vn.azteam.tabasupport.core.service
 * created 12/30/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface NotificationService {
	/**
	 * Send mail
	 *
	 * @param mailDomain a {@link String}
	 * @param username   a {@link String}
	 * @param password   a {@link String}
	 * @param mailFrom   a {@link String}
	 * @param mailTo     a {@link String}
	 * @param subject    a {@link String}
	 * @param content    a {@link String}
	 */
	void sendMail(String mailDomain, String username, String password, String mailFrom, String mailTo, String subject, String content);

	/**
	 * Send mail By Company Notify Mail
	 *
	 * @param companyId a {@link Long}
	 * @param mailTo    a {@link String}
	 * @param subject   a {@link String}
	 * @param content   a {@link String}
	 */
	void sendMailByCompanyNotifyMail(long companyId, String mailTo, String subject, String content);

	/**
	 * Send  Notify Mail To Account
	 *
	 * @param account a {@link Account} object
	 * @param subject a {@link String}
	 * @param content a {@link String}
	 */
	void sendNotifyMailToAccount(Account account, String subject, String content);

	void pushNotification();

	void insertAllRecordPushNeededToDay();
}
