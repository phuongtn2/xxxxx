package vn.azteam.tabasupport.core.service;

import vn.azteam.tabasupport.core.object.model.MailConfig;

import java.util.List;

/**
 * package vn.azteam.tabasupport.core.service
 * created 12/30/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface ConfigService {
	/**
	 * Get all mail config
	 *
	 * @return a {@link List} MailConfig
	 */
	List<MailConfig> getAllMailConfig();

	/**
	 * Get Mail config by domain
	 *
	 * @param domain a {@link String}
	 * @return a {@link MailConfig} | null
	 */
	MailConfig getMailConfig(String domain);
}
