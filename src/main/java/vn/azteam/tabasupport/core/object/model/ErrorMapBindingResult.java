package vn.azteam.tabasupport.core.object.model;

import org.springframework.validation.MapBindingResult;
import vn.azteam.tabasupport.util.ClassUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * package vn.azteam.tabasupport.core.object.model
 * created 12/21/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see MapBindingResult
 * @since 1.0.0
 */
public class ErrorMapBindingResult extends MapBindingResult {

	private Object validateObject;

	/**
	 * Create a new MapBindingResult instance.
	 *
	 * @param object     the object to bind onto
	 * @param objectName the name of the target object
	 */
	public ErrorMapBindingResult(Object object, String objectName) {
		super(ClassUtil.parsePropertyMapper(object), objectName);
		validateObject = object;
	}

	/**
	 * Create a new MapBindingResult instance.
	 *
	 * @param target     the target Map to bind onto
	 * @param objectName the name of the target object
	 */
	public ErrorMapBindingResult(Map<?, ?> target, String objectName) {
		super(target, objectName);
	}

	@Override
	protected Object getActualFieldValue(String field) {
		Method method = (Method) getTargetMap().get(field);
		try {
			return method.invoke(validateObject);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
