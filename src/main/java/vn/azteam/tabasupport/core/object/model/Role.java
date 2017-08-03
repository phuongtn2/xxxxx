package vn.azteam.tabasupport.core.object.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.io.Serializable;

/**
 * package vn.azteam.tabasupport.core.object.model
 * created 12/14/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @see GrantedAuthority
 * @see Serializable
 * @since 1.0.0
 */

@JsonIgnoreProperties({"createId", "updateId", "created", "updated", "lastErrors"})
public class Role extends BaseModel implements GrantedAuthority, Serializable {
	public static final String SCOPE_GLOBAL = "GLOBAL";
	public static final String SCOPE_COMPANY = "COMPANY";

	private long id;
	private long parentRoleId;
	private String roleName;
	private String slug;
	private String scope;

	public long getId() {
		return id;
	}

	public Role setId(long id) {
		this.id = id;
		return this;
	}

	public long getParentRoleId() {
		return parentRoleId;
	}

	public Role setParentRoleId(long parentRoleId) {
		this.parentRoleId = parentRoleId;
		return this;
	}

	public String getRoleName() {
		return roleName;
	}

	public Role setRoleName(String roleName) {
		this.roleName = roleName;
		return this;
	}

	public String getSlug() {
		return slug;
	}

	public Role setSlug(String slug) {
		this.slug = slug;
		return this;
	}

	public String getScope() {
		return scope;
	}

	public Role setScope(String scope) {
		this.scope = scope;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@JsonIgnore
	public String getAuthority() {
		return roleName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return this.getClass().equals(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "roleName",
				PropertiesParserUtil.getProperty("validation.role.roleName.empty.code"),
				PropertiesParserUtil.getProperty("validation.role.roleName.empty.msg"));

		ValidationUtils.rejectIfEmpty(errors, "scope",
				PropertiesParserUtil.getProperty("validation.role.scope.empty.code"),
				PropertiesParserUtil.getProperty("validation.role.scope.empty.msg"));

		ValidationUtils.rejectIfEmpty(errors, "slug",
				PropertiesParserUtil.getProperty("validation.role.slug.empty.code"),
				PropertiesParserUtil.getProperty("validation.role.slug.empty.msg"));
	}
}
