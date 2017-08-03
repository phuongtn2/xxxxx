package vn.azteam.tabasupport.core.object.model;

import org.springframework.validation.Errors;

/**
 * package vn.azteam.tabasupport.core.object.model
 * created 1/3/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @since 1.0.0
 */
public class MailTemplate extends BaseModel {
	public static final String SCOPE_GLOBAL = "GLOBAL";
	public static final String SCOPE_COMPANY = "COMPANY";

	private long id;
	private long companyId;
	private String scope;
	private String title;
	private String slug;
	private String content;

	public static String getScopeGlobal() {
		return SCOPE_GLOBAL;
	}

	public static String getScopeCompany() {
		return SCOPE_COMPANY;
	}

	public long getId() {
		return id;
	}

	public MailTemplate setId(long id) {
		this.id = id;
		return this;
	}

	public long getCompanyId() {
		return companyId;
	}

	public MailTemplate setCompanyId(long companyId) {
		this.companyId = companyId;
		return this;
	}

	public String getScope() {
		return scope;
	}

	public MailTemplate setScope(String scope) {
		this.scope = scope;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public MailTemplate setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getSlug() {
		return slug;
	}

	public MailTemplate setSlug(String slug) {
		this.slug = slug;
		return this;
	}

	public String getContent() {
		return content;
	}

	public MailTemplate setContent(String content) {
		this.content = content;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

	}

	@Override
	public String toString() {
		return "MailTemplate{" +
				"id=" + id +
				", companyId=" + companyId +
				", scope='" + scope + '\'' +
				", title='" + title + '\'' +
				", slug='" + slug + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}
