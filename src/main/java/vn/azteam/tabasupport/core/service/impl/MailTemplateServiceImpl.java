package vn.azteam.tabasupport.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.azteam.tabasupport.core.object.dao.MailTemplateDao;
import vn.azteam.tabasupport.core.object.model.MailTemplate;
import vn.azteam.tabasupport.core.service.MailTemplateService;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * package vn.azteam.tabasupport.core.service.impl
 * created 1/3/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseServiceImpl
 * @see MailTemplateService
 * @since 1.0.0
 */
@Service
public class MailTemplateServiceImpl extends BaseServiceImpl implements MailTemplateService {
	@Autowired
	private MailTemplateDao mailTemplateDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<MailTemplate> getAll() {
		return mailTemplateDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MailTemplate getMailTemplateBySlug(String slug, long companyId, boolean searchGlobal) {
		List<MailTemplate> mailTemplates = null;
		MailTemplate mailTemplate = null;

		try {
			mailTemplates = getAll().stream().filter(tpl -> tpl.getSlug().equals(slug)).collect(Collectors.toList());
			mailTemplate = mailTemplates.stream()
					.filter(tpl -> tpl.getCompanyId() == companyId && tpl.getScope().equals(MailTemplate.SCOPE_COMPANY))
					.findFirst().get();
		} catch (NoSuchElementException e) {
			logger.error(e);
		}

		if (mailTemplates != null && mailTemplate == null && searchGlobal) {
			try {
				return mailTemplates.stream()
						.filter(tpl -> tpl.getScope().equals(MailTemplate.SCOPE_GLOBAL))
						.findFirst().get();
			} catch (NoSuchElementException e) {
				logger.error(e);
			}
		}

		return mailTemplate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MailTemplate getMailContent(String slug, long companyId, boolean searchGlobal, HashMap<String, String> hashMap) {
		final MailTemplate mailTemplate = getMailTemplateBySlug(slug, companyId, searchGlobal);
		if (mailTemplate == null) return null;
		String content = mailTemplate.getContent();
		for (String key : hashMap.keySet()) {
			content = content.replaceAll("\\#\\{" + key + "\\}", hashMap.get(key));
		}

		return mailTemplate.setContent(content);
	}
}
