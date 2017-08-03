package vn.azteam.tabasupport.core.object.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.util.ClassUtil;
import vn.azteam.tabasupport.util.MapBeanConverter;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


/**
 * package vn.azteam.tabasupport.core.object.model
 * created 12/15/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @See Serializable
 * @See Validator
 * @since 1.0.0
 */
@JsonIgnoreProperties({"createId", "updateId", "created", "updated", "errors"})
public abstract class BaseModel implements Validator, Serializable {
	protected final Logger logger = LogManager.getLogger(getClass());

	protected long clientPrimaryKey;

	@JsonIgnore
	protected long createId;
	@JsonIgnore
	protected long updateId;

	@JsonIgnore
	protected Date created;
	@JsonIgnore
	protected Date updated;

	/**
	 * Get new instance from child class
	 *
	 * @param childObj         a {@link Object} extend from User
	 * @param clazz            a {@link Class<T>} to cast.
	 * @param ignoreProperties String  list properties to ignore.
	 * @return a {@link User} object
	 */
	public static <T> T newInstanceFromChild(Object childObj, Class<T> clazz, String... ignoreProperties) throws ValidationException {
		final T bean;
		try {
			bean = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new ValidationException(PropertiesParserUtil.getProperty("default.exception.java.language.code"),
					PropertiesParserUtil.getProperty("default.exception.java.language.msg"));
		}

		if (clazz.isInstance(childObj)) {
			BeanUtils.copyProperties(childObj, bean, ignoreProperties);
		}

		return bean;
	}

	/**
	 * Get new instance from child class
	 *
	 * @param childObj a {@link Object} extend from User.
	 * @param clazz    a {@link Class<T>} to cast.
	 * @return a {@link T} object
	 */
	public static <T> T newInstanceFromChild(Object childObj, Class<T> clazz) throws ValidationException {
		return newInstanceFromChild(childObj, clazz, (String[]) null);
	}

	public long getClientPrimaryKey() {
		return clientPrimaryKey;
	}

	public BaseModel setClientPrimaryKey(long clientPrimaryKey) {
		this.clientPrimaryKey = clientPrimaryKey;
		return this;
	}

	public long getCreateId() {
		return createId;
	}

	public BaseModel setCreateId(long createId) {
		this.createId = createId;
		return this;
	}

	public long getUpdateId() {
		return updateId;
	}

	public BaseModel setUpdateId(long updateId) {
		this.updateId = updateId;
		return this;
	}

	public Date getCreated() {
		return created;
	}

	public BaseModel setCreated(Date created) {
		this.created = created;
		return this;
	}

	public Date getUpdated() {
		return updated;
	}

	public BaseModel setUpdated(Date updated) {
		this.updated = updated;
		return this;
	}

	/**
	 * Insert to model author and update author
	 *
	 * @param auth {@link OAuth2Authentication}
	 */
	public void insertAuthor(OAuth2Authentication auth) {
		final Account account = (Account) auth.getPrincipal();
		createId = createId == 0 ? account.getId() : createId;
		updateId = account.getId();
	}

	/**
	 * set Default data before validate
	 */
	protected void beforeValidate() {
		Date now = new Date();
		created = created == null ? now : created;
		updated = updated == null ? now : updated;
		createId = createId == 0 ? -1 : createId;
		updateId = updateId == 0 ? -1 : updateId;
	}

	/**
	 * Validating model.
	 *
	 * @return boolean
	 */
	public boolean validated() {
		return getErrors() == null || getErrors().isEmpty();
	}

	/**
	 * Get list ObjectError of model validation. Must call after validated() method
	 *
	 * @return a {@link List<ObjectError>} object
	 */
	@JsonIgnore
	public List<ObjectError> getErrors() {
		MapBindingResult errors = new ErrorMapBindingResult(this, getClass().getName());
		beforeValidate();
		validate(this, errors);
		return errors.getAllErrors();
	}

	/**
	 * Get first ObjectError of model validation. Must call after validated() method
	 *
	 * @return a {@link List<ObjectError>} object
	 */
	public ObjectError firstError() {
		return getErrors().get(0);
	}

	private void doSetProperties(MultiValueMap<String, String> mapper, boolean isListIgnore, String... properties) throws ValidationException {
		final Map<String, Method> clazzPropSetterMap = new HashMap<>();
		final List<String> propsList = Arrays.asList(properties);

		for (final Method m : this.getClass().getMethods()) {
			final String propName = ClassUtil.parsePropertyName(m, "set");
			if (propName != null && m.getParameterTypes().length == 1) {
				if (isListIgnore && !propsList.contains(propName)) {
					clazzPropSetterMap.put(propName, m);
				} else if (!isListIgnore && propsList.contains(propName)) {
					clazzPropSetterMap.put(propName, m);
				}
			}
		}

		for (final String propName : mapper.keySet()) {
			if (clazzPropSetterMap.containsKey(propName)) {
				final Method m = clazzPropSetterMap.get(propName);
				final Class<?>[] parameterTypes = m.getParameterTypes();
				Object v;

				try {
					v = MapBeanConverter.formatParameter(m, parameterTypes[0], mapper.get(propName));
				} catch (NumberFormatException n) {
					logger.error(n);
					throw new ValidationException(PropertiesParserUtil.getProperty("validation.request.default.code"),
							PropertiesParserUtil.getProperty("validation.request.default.msg"));
				}

				try {
					ClassUtil.invokeMethod(this, m, v);
				} catch (IllegalAccessException | InvocationTargetException e) {
					logger.error(e);
					throw new ValidationException(PropertiesParserUtil.getProperty("default.exception.java.language.code"),
							PropertiesParserUtil.getProperty("default.exception.java.language.msg"));
				}
			}
		}
	}

	/**
	 * Copy properties from mapper.
	 *
	 * @param mapper           a {@link MultiValueMap} data
	 * @param ignoreProperties String  list properties to ignore.
	 * @throws ValidationException
	 */
	public void copyPropertiesFromMapper(MultiValueMap<String, String> mapper, String... ignoreProperties) throws ValidationException {
		doSetProperties(mapper, true, ignoreProperties);
	}

	/**
	 * Update properties from mapper.
	 *
	 * @param mapper     a {@link MultiValueMap} data
	 * @param properties String  list properties to update.
	 * @throws ValidationException
	 */
	public void copyPropertiesInListFromMapper(MultiValueMap<String, String> mapper, String... properties) throws ValidationException {
		doSetProperties(mapper, false, properties);
	}
}
