package vn.azteam.tabasupport.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.azteam.tabasupport.core.object.dao.MailConfigDao;
import vn.azteam.tabasupport.core.object.model.MailConfig;
import vn.azteam.tabasupport.core.service.ConfigService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * package vn.azteam.tabasupport.core.service.impl
 * created 12/30/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseServiceImpl
 * @see ConfigService
 * @since 1.0.0
 */
@Service
public class ConfigServiceImpl extends BaseServiceImpl implements ConfigService {
	@Autowired
	private MailConfigDao mailConfigDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<MailConfig> getAllMailConfig() {
		return mailConfigDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MailConfig getMailConfig(String domain) {
		MailConfig mailConfig = null;
		try {
			mailConfig = getAllMailConfig().stream().filter(cf -> cf.getDomain().equals(domain)).findFirst().get();
		} catch (NoSuchElementException e) {
			logger.error(e);
		}
		return mailConfig;
	}
}
