package vn.azteam.tabasupport.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringUtil class.
 *
 * @author hieunc
 * @version 1.0.0
 * @since 1.0.0
 */
public class StringUtil {
	/**
	 * Regular expression pattern of the date format from the form of the REST API.
	 * "yyyy / MM / dd HH: mm: ss" (omitted in seconds, omission of the hour, minute, and second also forgive)
	 */
	private static final Pattern dataPattern = Pattern.compile("(\\d{4})/(\\d{1,2})/(\\d{1,2})(?: (\\d{1,2}):(\\d{1,2})(?::(\\d{1,2}))?)?");

	/**
	 * decapitalize.
	 *
	 * @param string a {@link String} object.
	 * @return a {@link String} object.
	 */
	public static String decapitalize(String string) {
		if (string == null || string.length() == 0) {
			return string;
		}

		char c[] = string.toCharArray();
		c[0] = Character.toLowerCase(c[0]);
		return new String(c);
	}

	/**
	 * isEmpty.
	 *
	 * @param s a {@link String} object.
	 * @return a boolean.
	 */
	public static boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}

	/**
	 * The date string Of Perth.
	 * "Yyyy / MM / dd HH: mm: ss" assumption (Optional seconds, omission of the hour, minute, and second also forgive)
	 *
	 * @param apiDateString date input string
	 * @return Perth result Date
	 * @throws NumberFormatException format of the input string is different from the assumption
	 * @throws NullPointerException  If the input string is NULL
	 */
	public static Date parseDate(String apiDateString) throws NumberFormatException, NullPointerException {
		try {
			return new Date(parseDateStringTimeInMilis(apiDateString));
		} catch (NumberFormatException | NullPointerException e) {
			long timeLong = StringUtil.convertStringToLong(apiDateString, 0);
			Date timeDate = new Date(timeLong);
			return timeDate;
		}

	}

	/**
	 * Replace date string to a date (Long number of timeInMilis).
	 * <p>
	 * Yyyy / MM / dd HH: mm: ss format (yyyy / MM / dd or yyyy / MM / dd HH: mm also acceptable format)
	 *
	 * @param apiDateTimeString date assumption of string
	 * @return The datetime string.
	 * @throws NullPointerException  If the string is null
	 *                               If @throws NumberFormatException string format is not the assumed format
	 * @throws NumberFormatException if any.
	 */
	public static long parseDateStringTimeInMilis(String apiDateTimeString) throws NumberFormatException, NullPointerException {
		if (apiDateTimeString == null) {
			throw new NullPointerException("parseDateStringTimeInMilis argument is null");
		}
		final Matcher m = dataPattern.matcher(apiDateTimeString);
		if (m.find()) {
			final int[] dds = new int[6];
			for (int i = 0; i < m.groupCount() && i < dds.length; i++) {
				final String s = m.group(i + 1);
				dds[i] = isEmpty(s) ? 0 : Integer.parseInt(s);
			}
			final Calendar cal = Calendar.getInstance();
			cal.clear();
			cal.set(dds[0], dds[1] - 1, dds[2], dds[3], dds[4], dds[5]);
			cal.set(Calendar.MILLISECOND, 0);
			return cal.getTimeInMillis();
		} else {
			throw new NumberFormatException("parseDateStringTimeInMilis format error::" + apiDateTimeString);
		}
	}

	/**
	 * Convert String Array to long Array.
	 *
	 * @param arrStr array {@link String}
	 * @return array {@link Long}
	 */
	public static long[] convertToLongArray(String[] arrStr) throws NumberFormatException {
		return Arrays.stream(arrStr).mapToLong(Long::parseLong).toArray();
	}

	/**
	 * Convert string to int with default value
	 * @param string containing the {@code int} representation to be parsed
	 * @param defaultValue a {@code int}
	 * @return if throw exception, return default value
	 */
	public static int convertStringToUnsignedInt(String string, int defaultValue){
		try{
			return Integer.parseUnsignedInt(string);
		}catch (NumberFormatException n){
			n.printStackTrace();
		}
		return defaultValue;
	}

	public static int[] convertArrayStringToUnsignedInt(String[] strings, int defaultValue) {
		int[] intArray = new int[strings.length];
		int i = 0;
		for (String str : strings) {
			try {
				intArray[i] = Integer.parseUnsignedInt(str.trim());
			} catch (NumberFormatException n) {
				n.printStackTrace();
				intArray[i] = defaultValue;
			}
			i++;
		}
		return intArray;
	}

	/**
	 * Convert String to long
	 * @param string a string
	 * @param defaultValue  long
	 * @return if throw exception, return default value
	 */
	public static long convertStringToLong(String string, long defaultValue){
		try{
			return Long.parseLong(string);
		}catch (NumberFormatException n){
			n.printStackTrace();
		}
		return defaultValue;
	}

	public static long[] convertArrayStringToLong(String[] strings, long defaultValue) {
		long[] longArray = new long[strings.length];
		int i = 0;
		for (String str : strings) {
			try {
				longArray[i] = Long.parseLong(str.trim());
			} catch (NumberFormatException n) {
				n.printStackTrace();
				longArray[i] = defaultValue;
			}
			i++;
		}
		return longArray;
	}

	public static String getDateFromDateTime(Date dateTime) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(dateTime);
	}

	public static String getTimeFromDateTime(Date dateTime) {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(dateTime);
	}
}
