package vn.azteam.tabasupport.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

/**
 * ClassUtil class.
 *
 * @author hieunc
 * @version 1.0.0
 * @since 1.0.0
 */
public class ClassUtil {
	/**
	 * To get the specified by the property name the pre-string method name
	 *
	 * @param m      {@link Method}    method
	 * @param access string
	 * @return The string contains the parsed name or null when invalid method has been provided.
	 */
	public static String parsePropertyName(Method m, String access) {
		final String mn = m.getName();
		if (mn.startsWith(access)) {
			final int al = access.length();
			return String.valueOf(Character.toLowerCase(mn.charAt(al))) +
					mn.substring(al + 1);
		}
		return null;
	}

	/**
	 * Perform reflection-based method.
	 * Method assumes a non-argument
	 * Execution failure returns null
	 *
	 * @param bean executed Object
	 * @param m    execution method
	 * @param args run-time argument (variable length argument)
	 * @return mixed
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object invokeMethod(Object bean, Method m, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return m.invoke(bean, args);
	}

	/**
	 * Get properties mapper of an object
	 *
	 * @param obj {@link Object}
	 * @return a property {@link HashMap} mapper
	 */
	public static HashMap<String, Method> parsePropertyMapper(Object obj) {
		Class<?> clazz = obj.getClass();
		Method[] methods = Arrays.stream(clazz.getMethods())
				.filter(m -> m.getName().startsWith("get") && !m.getName().startsWith("getClass"))
				.toArray(Method[]::new);
		final HashMap<String, Method> map = new HashMap<>();

		Arrays.stream(methods).forEach(m -> {
			String prop = StringUtil.decapitalize(m.getName().substring(3));
			map.put(prop, m);
		});

		return map;
	}
}
