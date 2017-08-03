package vn.azteam.tabasupport.core.object.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.validation.Errors;

/**
 * package vn.azteam.tabasupport.core.object.model
 * created 12/30/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseModel
 * @since 1.0.0
 */
public class Company extends BaseModel {
	private long id;
	private long parentId;
	private String name;

	@JsonIgnore
	private String notifyMail;
	@JsonIgnore
	private String notifyMailPassword;

	public long getId() {
		return id;
	}

	public Company setId(long id) {
		this.id = id;
		return this;
	}

	public long getParentId() {
		return parentId;
	}

	public Company setParentId(long parentId) {
		this.parentId = parentId;
		return this;
	}

	public String getName() {
		return name;
	}

	public Company setName(String name) {
		this.name = name;
		return this;
	}

	public String getNotifyMail() {
		return notifyMail;
	}

	public Company setNotifyMail(String notifyMail) {
		this.notifyMail = notifyMail;
		return this;
	}

	public String getNotifyMailPassword() {
		return notifyMailPassword;
	}

	public Company setNotifyMailPassword(String notifyMailPassword) {
		this.notifyMailPassword = notifyMailPassword;
		return this;
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

	}

	@Override
	public String toString() {
		return "Company{" +
				"id=" + id +
				", name='" + name + '\'' +
				", notifyMail='" + notifyMail + '\'' +
				", notifyMailPassword='" + notifyMailPassword + '\'' +
				'}';
	}
}
