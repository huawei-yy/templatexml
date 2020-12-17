package pers.yhw.templatexml.beanpropertyutils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据com.alibaba.fastjson.util.TypeUtils 复制修改
 * 
 * @author yhw
 *
 */
public class TypeConverUtils {
	private static final Pattern NUMBER_WITH_TRAILING_ZEROS_PATTERN = Pattern.compile("\\.0*$");

	static <T> T cast(Object obj, Class<T> clazz) {
		if (obj == null) {
			if (clazz == int.class) {
				return (T) Integer.valueOf(0);
			} else if (clazz == long.class) {
				return (T) Long.valueOf(0);
			} else if (clazz == short.class) {
				return (T) Short.valueOf((short) 0);
			} else if (clazz == byte.class) {
				return (T) Byte.valueOf((byte) 0);
			} else if (clazz == float.class) {
				return (T) Float.valueOf(0);
			} else if (clazz == double.class) {
				return (T) Double.valueOf(0);
			} else if (clazz == boolean.class) {
				return (T) Boolean.FALSE;
			}
			return null;
		}

		if (clazz == null) {
			throw new IllegalArgumentException("clazz is null");
		}

		if (clazz == obj.getClass()) {
			return (T) obj;
		}

		if (obj instanceof Map) {
			if (clazz == Map.class) {
				return (T) obj;
			}

			Map map = (Map) obj;
			if (clazz == Object.class) {
				return (T) obj;
			}
		}

		if (clazz.isArray()) {
			if (obj instanceof Collection) {
				Collection collection = (Collection) obj;
				int index = 0;
				Object array = Array.newInstance(clazz.getComponentType(), collection.size());
				for (Object item : collection) {
					Object value = cast(item, clazz.getComponentType());
					Array.set(array, index, value);
					index++;
				}
				return (T) array;
			}
			if (clazz == byte[].class) {
				return (T) castToBytes(obj);
			}
		}

		if (clazz.isAssignableFrom(obj.getClass())) {
			return (T) obj;
		}

		if (clazz == boolean.class || clazz == Boolean.class) {
			return (T) castToBoolean(obj);
		}

		if (clazz == byte.class || clazz == Byte.class) {
			return (T) castToByte(obj);
		}

		if (clazz == char.class || clazz == Character.class) {
			return (T) castToChar(obj);
		}

		if (clazz == short.class || clazz == Short.class) {
			return (T) castToShort(obj);
		}

		if (clazz == int.class || clazz == Integer.class) {
			return (T) castToInt(obj);
		}

		if (clazz == long.class || clazz == Long.class) {
			return (T) castToLong(obj);
		}

		if (clazz == float.class || clazz == Float.class) {
			return (T) castToFloat(obj);
		}

		if (clazz == double.class || clazz == Double.class) {
			return (T) castToDouble(obj);
		}

		if (clazz == String.class) {
			return (T) castToString(obj);
		}

		if (clazz == BigDecimal.class) {
			return (T) castToBigDecimal(obj);
		}

		if (clazz == BigInteger.class) {
			return (T) castToBigInteger(obj);
		}

		if (clazz == Date.class) {
			return (T) castToDate(obj);
		}

		if (clazz == java.sql.Date.class) {
			return (T) castToSqlDate(obj);
		}

		if (clazz == java.sql.Time.class) {
			return (T) castToSqlTime(obj);
		}

		if (clazz == java.sql.Timestamp.class) {
			return (T) castToTimestamp(obj);
		}

		if (clazz.isEnum()) {
			return castToEnum(obj, clazz);
		}

		if (Calendar.class.isAssignableFrom(clazz)) {
			Date date = castToDate(obj);
			Calendar calendar;
			if (clazz == Calendar.class) {
				calendar = Calendar.getInstance();
			} else {
				try {
					calendar = (Calendar) clazz.newInstance();
				} catch (Exception e) {
					throw new RuntimeException("can not cast to : " + clazz.getName(), e);
				}
			}
			calendar.setTime(date);
			return (T) calendar;
		}

		String className = clazz.getName();

		if (obj instanceof String) {
			String strVal = (String) obj;
			if (strVal.length() == 0 //
					|| "null".equals(strVal) //
					|| "NULL".equals(strVal)) {
				return null;
			}

			if (clazz == java.util.Currency.class) {
				return (T) java.util.Currency.getInstance(strVal);
			}
		}
		throw new RuntimeException("can not cast to : " + clazz.getName());

	}

	static Boolean castToBoolean(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Boolean) {
			return (Boolean) value;
		}

		if (value instanceof BigDecimal) {
			return ((BigDecimal) value).intValue() == 1;
		}

		if (value instanceof Number) {
			return ((Number) value).intValue() == 1;
		}

		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0 //
					|| "null".equals(strVal) //
					|| "NULL".equals(strVal)) {
				return null;
			}
			if ("true".equalsIgnoreCase(strVal) //
					|| "1".equals(strVal)) {
				return Boolean.TRUE;
			}
			if ("false".equalsIgnoreCase(strVal) //
					|| "0".equals(strVal)) {
				return Boolean.FALSE;
			}
			if ("Y".equalsIgnoreCase(strVal) //
					|| "T".equals(strVal)) {
				return Boolean.TRUE;
			}
			if ("F".equalsIgnoreCase(strVal) //
					|| "N".equals(strVal)) {
				return Boolean.FALSE;
			}
		}
		throw new RuntimeException("can not cast to boolean, value : " + value);
	}

	static Character castToChar(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Character) {
			return (Character) value;
		}
		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0) {
				return null;
			}
			if (strVal.length() != 1) {
				throw new RuntimeException("can not cast to char, value : " + value);
			}
			return strVal.charAt(0);
		}
		throw new RuntimeException("can not cast to char, value : " + value);
	}

	static Short castToShort(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof BigDecimal) {
			return shortValue((BigDecimal) value);
		}

		if (value instanceof Number) {
			return ((Number) value).shortValue();
		}

		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0 //
					|| "null".equals(strVal) //
					|| "NULL".equals(strVal)) {
				return null;
			}
			return Short.parseShort(strVal);
		}

		throw new RuntimeException("can not cast to short, value : " + value);
	}

	static BigDecimal castToBigDecimal(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		}
		if (value instanceof BigInteger) {
			return new BigDecimal((BigInteger) value);
		}
		String strVal = value.toString();
		if (strVal.length() == 0) {
			return null;
		}
		if (value instanceof Map && ((Map) value).size() == 0) {
			return null;
		}
		return new BigDecimal(strVal);
	}

	static BigInteger castToBigInteger(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof BigInteger) {
			return (BigInteger) value;
		}
		if (value instanceof Float || value instanceof Double) {
			return BigInteger.valueOf(((Number) value).longValue());
		}
		if (value instanceof BigDecimal) {
			BigDecimal decimal = (BigDecimal) value;
			int scale = decimal.scale();
			if (scale > -1000 && scale < 1000) {
				return ((BigDecimal) value).toBigInteger();
			}
		}
		String strVal = value.toString();
		if (strVal.length() == 0 //
				|| "null".equals(strVal) //
				|| "NULL".equals(strVal)) {
			return null;
		}
		return new BigInteger(strVal);
	}

	static Float castToFloat(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Number) {
			return ((Number) value).floatValue();
		}
		if (value instanceof String) {
			String strVal = value.toString();
			if (strVal.length() == 0 //
					|| "null".equals(strVal) //
					|| "NULL".equals(strVal)) {
				return null;
			}
			if (strVal.indexOf(',') != -1) {
				strVal = strVal.replaceAll(",", "");
			}
			return Float.parseFloat(strVal);
		}
		throw new RuntimeException("can not cast to float, value : " + value);
	}

	static Double castToDouble(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Number) {
			return ((Number) value).doubleValue();
		}
		if (value instanceof String) {
			String strVal = value.toString();
			if (strVal.length() == 0 //
					|| "null".equals(strVal) //
					|| "NULL".equals(strVal)) {
				return null;
			}
			if (strVal.indexOf(',') != -1) {
				strVal = strVal.replaceAll(",", "");
			}
			return Double.parseDouble(strVal);
		}
		throw new RuntimeException("can not cast to double, value : " + value);
	}

	static Date castToDate(Object value) {
		return castToDate(value, null);
	}

	static Date castToDate(Object value, String format) {
		if (value == null) {
			return null;
		}

		if (value instanceof Date) { // 浣跨敤棰戠巼鏈�楂樼殑锛屽簲浼樺厛澶勭悊
			return (Date) value;
		}

		if (value instanceof Calendar) {
			return ((Calendar) value).getTime();
		}

		long longValue = -1;

		if (value instanceof BigDecimal) {
			longValue = longValue((BigDecimal) value);
			return new Date(longValue);
		}

		if (value instanceof Number) {
			longValue = ((Number) value).longValue();
			if ("unixtime".equals(format)) {
				longValue *= 1000;
			}
			return new Date(longValue);
		}

		if (value instanceof String) {
			String strVal = (String) value;

			if (strVal.startsWith("/Date(") && strVal.endsWith(")/")) {
				strVal = strVal.substring(6, strVal.length() - 2);
			}

			if (strVal.indexOf('-') > 0 || strVal.indexOf('+') > 0) {
				if (format == null) {
					if (strVal.length() == 10) {
						format = "yyyy-MM-dd";
					} else if (strVal.length() == "yyyy-MM-dd HH:mm:ss".length()) {
						format = "yyyy-MM-dd HH:mm:ss";
					} else if (strVal.length() == 29 && strVal.charAt(26) == ':' && strVal.charAt(28) == '0') {
						format = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
					} else if (strVal.length() == 23 && strVal.charAt(19) == ',') {
						format = "yyyy-MM-dd HH:mm:ss,SSS";
					} else {
						format = "yyyy-MM-dd HH:mm:ss.SSS";
					}
				}

				SimpleDateFormat dateFormat = new SimpleDateFormat(format);
				try {
					return dateFormat.parse(strVal);
				} catch (ParseException e) {
					throw new RuntimeException("can not cast to Date, value : " + strVal);
				}
			}
			if (strVal.length() == 0) {
				return null;
			}
			longValue = Long.parseLong(strVal);
		}

		return new Date(longValue);

	}

	static java.sql.Date castToSqlDate(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof java.sql.Date) {
			return (java.sql.Date) value;
		}
		if (value instanceof java.util.Date) {
			return new java.sql.Date(((java.util.Date) value).getTime());
		}
		if (value instanceof Calendar) {
			return new java.sql.Date(((Calendar) value).getTimeInMillis());
		}

		long longValue = 0;
		if (value instanceof BigDecimal) {
			longValue = longValue((BigDecimal) value);
		} else if (value instanceof Number) {
			longValue = ((Number) value).longValue();
		}

		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0 //
					|| "null".equals(strVal) //
					|| "NULL".equals(strVal)) {
				return null;
			}
			if (isNumber(strVal)) {
				longValue = Long.parseLong(strVal);
			}
		}
		if (longValue <= 0) {
			throw new RuntimeException("can not cast to Date, value : " + value); // TODO 蹇界暐 1970-01-01 涔嬪墠鐨勬椂闂村鐞嗭紵
		}
		return new java.sql.Date(longValue);
	}

	static Integer castToInt(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof Integer) {
			return (Integer) value;
		}

		if (value instanceof BigDecimal) {
			return intValue((BigDecimal) value);
		}

		if (value instanceof Number) {
			return ((Number) value).intValue();
		}

		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0 //
					|| "null".equals(strVal) //
					|| "NULL".equals(strVal)) {
				return null;
			}
			if (strVal.indexOf(',') != -1) {
				strVal = strVal.replaceAll(",", "");
			}

			Matcher matcher = NUMBER_WITH_TRAILING_ZEROS_PATTERN.matcher(strVal);
			if (matcher.find()) {
				strVal = matcher.replaceAll("");
			}
			return Integer.parseInt(strVal);
		}

		if (value instanceof Boolean) {
			return (Boolean) value ? 1 : 0;
		}
		if (value instanceof Map) {
			Map map = (Map) value;
			if (map.size() == 2 && map.containsKey("andIncrement") && map.containsKey("andDecrement")) {
				Iterator iter = map.values().iterator();
				iter.next();
				Object value2 = iter.next();
				return castToInt(value2);
			}
		}
		throw new RuntimeException("can not cast to int, value : " + value);
	}

	static byte[] castToBytes(Object value) {
		if (value instanceof byte[]) {
			return (byte[]) value;
		}
		if (value instanceof String) {
			return ((String) value).getBytes();
		}
		throw new RuntimeException("can not cast to byte[], value : " + value);
	}

	static Long castToLong(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof BigDecimal) {
			return longValue((BigDecimal) value);
		}

		if (value instanceof Number) {
			return ((Number) value).longValue();
		}

		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0 //
					|| "null".equals(strVal) //
					|| "NULL".equals(strVal)) {
				return null;
			}
			if (strVal.indexOf(',') != -1) {
				strVal = strVal.replaceAll(",", "");
			}
			try {
				return Long.parseLong(strVal);
			} catch (NumberFormatException ex) {
				//
			}
		}

		if (value instanceof Map) {
			Map map = (Map) value;
			if (map.size() == 2 && map.containsKey("andIncrement") && map.containsKey("andDecrement")) {
				Iterator iter = map.values().iterator();
				iter.next();
				Object value2 = iter.next();
				return castToLong(value2);
			}
		}

		throw new RuntimeException("can not cast to long, value : " + value);
	}

	static java.sql.Time castToSqlTime(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof java.sql.Time) {
			return (java.sql.Time) value;
		}
		if (value instanceof java.util.Date) {
			return new java.sql.Time(((java.util.Date) value).getTime());
		}
		if (value instanceof Calendar) {
			return new java.sql.Time(((Calendar) value).getTimeInMillis());
		}

		long longValue = 0;
		if (value instanceof BigDecimal) {
			longValue = longValue((BigDecimal) value);
		} else if (value instanceof Number) {
			longValue = ((Number) value).longValue();
		}

		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0 //
					|| "null".equalsIgnoreCase(strVal)) {
				return null;
			}
			if (isNumber(strVal)) {
				longValue = Long.parseLong(strVal);
			}
		}
		if (longValue <= 0) {
			throw new RuntimeException("can not cast to Date, value : " + value); // TODO 蹇界暐 1970-01-01 涔嬪墠鐨勬椂闂村鐞嗭紵
		}
		return new java.sql.Time(longValue);
	}

	static java.sql.Timestamp castToTimestamp(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Calendar) {
			return new java.sql.Timestamp(((Calendar) value).getTimeInMillis());
		}
		if (value instanceof java.sql.Timestamp) {
			return (java.sql.Timestamp) value;
		}
		if (value instanceof java.util.Date) {
			return new java.sql.Timestamp(((java.util.Date) value).getTime());
		}
		long longValue = 0;
		if (value instanceof BigDecimal) {
			longValue = longValue((BigDecimal) value);
		} else if (value instanceof Number) {
			longValue = ((Number) value).longValue();
		}
		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0 //
					|| "null".equals(strVal) //
					|| "NULL".equals(strVal)) {
				return null;
			}
			if (strVal.endsWith(".000000000")) {
				strVal = strVal.substring(0, strVal.length() - 10);
			} else if (strVal.endsWith(".000000")) {
				strVal = strVal.substring(0, strVal.length() - 7);
			}

			if (strVal.length() == 29 && strVal.charAt(4) == '-' && strVal.charAt(7) == '-' && strVal.charAt(10) == ' '
					&& strVal.charAt(13) == ':' && strVal.charAt(16) == ':' && strVal.charAt(19) == '.') {
				int year = num(strVal.charAt(0), strVal.charAt(1), strVal.charAt(2), strVal.charAt(3));
				int month = num(strVal.charAt(5), strVal.charAt(6));
				int day = num(strVal.charAt(8), strVal.charAt(9));
				int hour = num(strVal.charAt(11), strVal.charAt(12));
				int minute = num(strVal.charAt(14), strVal.charAt(15));
				int second = num(strVal.charAt(17), strVal.charAt(18));
				int nanos = num(strVal.charAt(20), strVal.charAt(21), strVal.charAt(22), strVal.charAt(23),
						strVal.charAt(24), strVal.charAt(25), strVal.charAt(26), strVal.charAt(27), strVal.charAt(28));
				return new java.sql.Timestamp(year - 1900, month - 1, day, hour, minute, second, nanos);
			}

			if (isNumber(strVal)) {
				longValue = Long.parseLong(strVal);
			}
		}

		if (longValue < 0) {
			throw new RuntimeException("can not cast to Timestamp, value : " + value);
		}
		return new java.sql.Timestamp(longValue);
	}

	static Byte castToByte(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof BigDecimal) {
			return byteValue((BigDecimal) value);
		}

		if (value instanceof Number) {
			return ((Number) value).byteValue();
		}

		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0 //
					|| "null".equals(strVal) //
					|| "NULL".equals(strVal)) {
				return null;
			}
			return Byte.parseByte(strVal);
		}
		throw new RuntimeException("can not cast to byte, value : " + value);
	}

	static String castToString(Object value) {
		if (value == null) {
			return null;
		}
		return value.toString();
	}

	static <T> T castToEnum(Object obj, Class<T> clazz) {
		try {
			if (obj instanceof String) {
				String name = (String) obj;
				if (name.length() == 0) {
					return null;
				}

				return (T) Enum.valueOf((Class<? extends Enum>) clazz, name);
			}

			if (obj instanceof BigDecimal) {
				int ordinal = intValue((BigDecimal) obj);
				Object[] values = clazz.getEnumConstants();
				if (ordinal < values.length) {
					return (T) values[ordinal];
				}
			}

			if (obj instanceof Number) {
				int ordinal = ((Number) obj).intValue();
				Object[] values = clazz.getEnumConstants();
				if (ordinal < values.length) {
					return (T) values[ordinal];
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException("can not cast to : " + clazz.getName(), ex);
		}
		throw new RuntimeException("can not cast to : " + clazz.getName());
	}

	static byte byteValue(BigDecimal decimal) {
		if (decimal == null) {
			return 0;
		}

		int scale = decimal.scale();
		if (scale >= -100 && scale <= 100) {
			return decimal.byteValue();
		}

		return decimal.byteValueExact();
	}

	static short shortValue(BigDecimal decimal) {
		if (decimal == null) {
			return 0;
		}

		int scale = decimal.scale();
		if (scale >= -100 && scale <= 100) {
			return decimal.shortValue();
		}

		return decimal.shortValueExact();
	}

	static int intValue(BigDecimal decimal) {
		if (decimal == null) {
			return 0;
		}

		int scale = decimal.scale();
		if (scale >= -100 && scale <= 100) {
			return decimal.intValue();
		}

		return decimal.intValueExact();
	}

	static long longValue(BigDecimal decimal) {
		if (decimal == null) {
			return 0;
		}

		int scale = decimal.scale();
		if (scale >= -100 && scale <= 100) {
			return decimal.longValue();
		}

		return decimal.longValueExact();
	}

	static boolean isNumber(String str) {
		for (int i = 0; i < str.length(); ++i) {
			char ch = str.charAt(i);
			if (ch == '+' || ch == '-') {
				if (i != 0) {
					return false;
				}
			} else if (ch < '0' || ch > '9') {
				return false;
			}
		}
		return true;
	}

	static int num(char c0, char c1) {
		if (c0 >= '0' && c0 <= '9' && c1 >= '0' && c1 <= '9') {
			return (c0 - '0') * 10 + (c1 - '0');
		}

		return -1;
	}

	static int num(char c0, char c1, char c2, char c3) {
		if (c0 >= '0' && c0 <= '9' && c1 >= '0' && c1 <= '9' && c2 >= '0' && c2 <= '9' && c3 >= '0' && c3 <= '9') {
			return (c0 - '0') * 1000 + (c1 - '0') * 100 + (c2 - '0') * 10 + (c3 - '0');
		}

		return -1;
	}

	static int num(char c0, char c1, char c2, char c3, char c4, char c5, char c6, char c7, char c8) {
		if (c0 >= '0' && c0 <= '9' && c1 >= '0' && c1 <= '9' && c2 >= '0' && c2 <= '9' && c3 >= '0' && c3 <= '9'
				&& c4 >= '0' && c4 <= '9' && c5 >= '0' && c5 <= '9' && c6 >= '0' && c6 <= '9' && c7 >= '0' && c7 <= '9'
				&& c8 >= '0' && c8 <= '9') {
			return (c0 - '0') * 100000000 + (c1 - '0') * 10000000 + (c2 - '0') * 1000000 + (c3 - '0') * 100000
					+ (c4 - '0') * 10000 + (c5 - '0') * 1000 + (c6 - '0') * 100 + (c7 - '0') * 10 + (c8 - '0');
		}

		return -1;
	}
}
