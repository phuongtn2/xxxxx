package vn.azteam.tabasupport.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * MapBeanConverter class.
 *
 * @author hieunc
 * @version 1.0.0
 * @since 1.0.0
 */
public class MapBeanConverter {
	private static Logger log = LogManager.getRootLogger();

	/**
	 * ConvertObjectToMap.
	 *
	 * @param obj             a {@link Object} object.
	 * @param exceptPropsList a {@link List} object.
	 * @return a {@link HashMap} object.
	 * @throws IllegalAccessException    if any.
	 * @throws IllegalArgumentException  if any.
	 * @throws InvocationTargetException if any.
	 */
	public static HashMap<String, Object> ConvertObjectToMap(Object obj, List<String> exceptPropsList) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> objClass = obj.getClass();

		Method[] methods = objClass.getMethods();
		HashMap<String, Object> map = new HashMap<>();

		for (Method m : methods) {
			String prop = StringUtil.decapitalize(m.getName().substring(3));
			if (m.getName().startsWith("get") && !m.getName().startsWith("getClass") && !exceptPropsList.contains(prop)) {
				Object value = m.invoke(obj);
				map.put(prop, value);
			}
		}
		return map;
	}

	/**
	 * HTP Bean convert to clazz
	 *
	 * @param clazz Bean
	 * @param map   A map contains property values of converting class.
	 * @param <T>   Converting class type.
	 * @return The converted class or null when errors occur.
	 */
	public static <T> T convertMapToDtoClass(Class<T> clazz, MultiValueMap<String, String> map) {
		final T bean;

		try {
			bean = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}

		final Map<String, Method> clazzPropSetterMap = new HashMap<>();
		for (final Method m : clazz.getMethods()) {
			final String propName = ClassUtil.parsePropertyName(m, "set");
			if (propName != null && m.getParameterTypes().length == 1) {
				clazzPropSetterMap.put(propName, m);
			}
		}

		for (final String propName : map.keySet()) {
			if (clazzPropSetterMap.containsKey(propName)) {
				final Method m = clazzPropSetterMap.get(propName);
				final Class<?>[] parameterTypes = m.getParameterTypes();
				Object v;

				try {
					v = formatParameter(m, parameterTypes[0], map.get(propName));
					ClassUtil.invokeMethod(bean, m, v);
				} catch (Exception ignored) {

				}
			}
		}
		return bean;
	}

	/**
	 * formatParameter.
	 *
	 * @param m             a {@link Method} object.
	 * @param parameterType a {@link Class} object.
	 * @param list          a {@link List} object.
	 * @return a {@link Object} object.
	 */
	public static Object formatParameter(Method m, Class<?> parameterType, List<String> list) throws NumberFormatException {
		if (parameterType.isArray()) {
			final Class<?> inArrayType = parameterType.getComponentType();
			return formatParameterArray(inArrayType, list);
		} else if (parameterType == List.class) {
			return formatParameterList(m, parameterType, list);
		} else {
			return castInData(parameterType, (list != null && (!list.isEmpty())) ? list.get(0) : null);
		}
	}

	/**
	 * formatParameterArray.
	 *
	 * @param inArrayType a {@link Class} object.
	 * @param list        a {@link List} object.
	 * @param <T>         a T object.
	 * @return an array of T objects.
	 */
	protected static <T> T[] formatParameterArray(Class<T> inArrayType, List<String> list) {
		@SuppressWarnings("unchecked")
		final T[] ary = (T[]) Array.newInstance(inArrayType, list.size());
		for (int i = 0; i < list.size(); i++) {
			@SuppressWarnings("unchecked")
			final T v = (T) castInData(inArrayType, list.get(i));
			Array.set(ary, i, v);
		}
		return ary;
	}

	/**
	 * formatParameterList.
	 *
	 * @param m             a {@link Method} object.
	 * @param parameterType a {@link Class} object.
	 * @param list          a {@link List} object.
	 * @return a {@link Object} object.
	 */
	protected static Object formatParameterList(Method m, Class<?> parameterType, List<String> list) {
		final Type[] tvs = m.getGenericParameterTypes();
		final ArrayList<Object> al = new ArrayList<>(list.size());
		if (tvs.length == 1) {
			final ParameterizedType pt = (ParameterizedType) tvs[0];
			final Type[] inTypes = pt.getActualTypeArguments();
			if (inTypes.length == 1) {
				for (final String s : list) {
					if (inTypes[0] instanceof Class) {
						al.add(castInData((Class<?>) inTypes[0], s));
					} else {
						al.add(s);
					}
				}
			}
		}
		return al;
	}

	/**
	 * Cast processing parameters to be passed to the DTO of the setter.
	 *
	 * @param pt Type to be passed to the setter
	 * @param s  String value received by the API
	 * @return mixed
	 */
	public static Object castInData(Class<?> pt, String s) throws NumberFormatException {
		if (pt == String.class) {
			if (s != null && s.trim().toLowerCase().equals("null")) {
				s = null;
			}
			return s;
		} else if (pt == Integer.TYPE || pt == Integer.class) {
			return Integer.parseInt(s);
		} else if (pt == Long.TYPE || pt == Long.class) {
			return Long.parseLong(s);
		} else if (pt == Double.TYPE || pt == Double.class) {
			return Double.parseDouble(s);
		} else if (pt == Byte.TYPE || pt == Byte.class) {
			return Byte.parseByte(s);
		} else if (pt == Short.TYPE || pt == Short.class) {
			return Short.parseShort(s);
		} else if (pt == Boolean.TYPE || pt == Boolean.class) {
			return Boolean.parseBoolean(s);
		} else if (pt == Float.TYPE || pt == Float.class) {
			return Float.parseFloat(s);
		} else if (pt == BigDecimal.class) {
			return StringUtil.isEmpty(s) ? null : new BigDecimal(s);
		} else if (Date.class.isAssignableFrom(pt)) {
			return StringUtil.isEmpty(s) ? null : StringUtil.parseDate(s);
		}
		return null;
	}
}
