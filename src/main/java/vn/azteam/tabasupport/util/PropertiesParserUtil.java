package vn.azteam.tabasupport.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * package vn.azteam.tabasupport.util
 * created 12/13/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public class PropertiesParserUtil extends PropertyPlaceholderConfigurer {
	private static Map<String, String> propertiesMap;

	private int springSystemPropertiesMode = SYSTEM_PROPERTIES_MODE_FALLBACK;

	/**
	 * @param name {@link String} prop key
	 * @return String {@link String}
	 */
	public static String getProperty(String name) {
		return propertiesMap.getOrDefault(name, "");
	}

	/**
	 * @param name {@link String} prop key
	 * @param args {@link Object} arguments
	 * @return String {@link String}
	 */
	public static String parsePropertyByFormat(String name, Object... args) {
		return new Formatter().format(getProperty(name), args).toString();
	}


	/**
	 * @param name {@link String} prop key
	 * @return int {@link Integer}
	 */
	public static int getIntProperty(String name) {
		final int defaultVal = 0;
		try {
			return Integer.parseInt(getProperty(name));
		} catch (SecurityException | NumberFormatException e) {
			e.printStackTrace();
		}
		return defaultVal;
	}

	/**
	 * @param name {@link String} prop key
	 * @return long {@link Long}
	 */
	public static long getLongProperty(String name) {
		final int defaultVal = 0;
		try {
			return Long.parseLong(getProperty(name));
		} catch (SecurityException | NumberFormatException e) {
			e.printStackTrace();
		}
		return defaultVal;
	}

	/**
	 * @param systemPropertiesMode {@link Integer}
	 */
	@Override
	public void setSystemPropertiesMode(int systemPropertiesMode) {
		super.setSystemPropertiesMode(systemPropertiesMode);
		springSystemPropertiesMode = systemPropertiesMode;
	}

	/**
	 * @param beanFactory a {@link ConfigurableListableBeanFactory} Object
	 * @param props       a {@link Properties} Object
	 * @throws BeansException
	 */
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
		super.processProperties(beanFactory, props);

		propertiesMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String valueStr = resolvePlaceholder(keyStr, props, springSystemPropertiesMode);
			propertiesMap.put(keyStr, valueStr);
		}
	}
}
