package vn.azteam.tabasupport.core.object.dao;

import vn.azteam.tabasupport.core.object.model.MailTemplate;

import java.util.List;

/**
 * package vn.azteam.tabasupport.core.object.dao
 * created 1/3/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public interface MailTemplateDao {
	/**
	 * Get all mail template
	 *
	 * @return a {@link List} MailTemplate
	 */
	List<MailTemplate> findAll();
}
