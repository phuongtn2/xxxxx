package vn.azteam.tabasupport.core.service;

import vn.azteam.tabasupport.core.object.model.MailTemplate;

import java.util.HashMap;
import java.util.List;

/**
 * package vn.azteam.tabasupport.core.service
 * created 1/3/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface MailTemplateService {
	/**
	 * Get all mail template
	 *
	 * @return a {@link List} object
	 */
	List<MailTemplate> getAll();

	/**
	 * Get template by slug
	 *
	 * @param slug         a {@link String}
	 * @param companyId    a {@link Long}
	 * @param searchGlobal a boolean - if true find both GLOBAL and COMPANY
	 * @return a {@link MailTemplate} | null
	 */
	MailTemplate getMailTemplateBySlug(String slug, long companyId, boolean searchGlobal);

	/**
	 * Get template by slug
	 *
	 * @param slug         a {@link String}
	 * @param companyId    a {@link Long}
	 * @param searchGlobal a boolean - if true find both GLOBAL and COMPANY
	 * @param hashMap      a {@link HashMap} object
	 * @return a {@link MailTemplate} | null
	 */
	MailTemplate getMailContent(String slug, long companyId, boolean searchGlobal, HashMap<String, String> hashMap);
}
