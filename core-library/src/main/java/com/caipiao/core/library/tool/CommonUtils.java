package com.caipiao.core.library.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caipiao.core.library.constant.Constants;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Qiang
 * @date 2017年7月27日 上午11:17:13
 *
 */
public class CommonUtils {
	private static final Logger LOG = LoggerFactory.getLogger(CommonUtils.class);
	/**
	 * 获取field缓存
	 */
	private static Map<Class<?>, Map<String, Field>> fieldMap = new HashMap<Class<?>, Map<String, Field>>();

	/**
	 * 是否整形类型
	 * @param data
	 * @return
	 */
	public static boolean isInteger(Object data) {
		if (data == null || "".equals(data))
			return false;
		String reg = "[\\d]+";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(data.toString());
		return Integer.MAX_VALUE >= Double.parseDouble(data.toString())
				&& m.matches();
	}

	/**
	 * 判断一个集合是否为空或没有元素
	 */
	public static <T> boolean isCollectionEmpty(Collection<T> collection) {
		if (collection == null || collection.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串是否为空
	 * @param str
	 * @return true 空
	 */
	public static boolean isNull(String str) {
		return str == null || str.trim().length() == 0 || str.equals("null");
	}

	/**
	 * 功能：判断字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param obj
	 * @return
	 */
	public static int parseInt(String obj) {
		if (obj == null || obj.equals("")) {
			return 0;
		}
		try {
			if (!obj.toString().contains(".")) {
				return Integer.parseInt(obj.toString());
			}
			return (int) Double.parseDouble((obj.toString()));
		} catch (Exception e) {
			LOG.error("异常：",e);
		}
		return 0;
	}

	public static byte parseByte(String obj) {
		return (byte) parseInt(obj);
	}

	public static short parseShort(String obj) {
		return (short) parseInt(obj);
	}

	/**
	 * @param obj
	 * @return
	 */
	public static float parseFloat(Object obj) {
		if (obj == null || obj.equals("")) {
			return 0;
		}
		try {
			return Float.parseFloat(obj.toString());
		} catch (Exception e) {
			LOG.error("异常：",e);
		}
		return 0;
	}

	/**
	 * @param obj
	 * @return
	 */
	public static long parseLong(Object obj) {
		if (obj == null || obj.equals("")) {
			return 0;
		}
		try {
			if (!obj.toString().contains(".")) {
				return Long.parseLong(obj.toString());
			}
			return (long) Double.parseDouble((obj.toString()));
		} catch (Exception e) {
			LOG.error("异常：",e);
		}
		return 0;
	}

	private static Pattern contentPattern = Pattern
			.compile("[\u4e00-\u9fa50-9a-zA-Z,，。.?？!！@()（）\\[\\]\\-_]+");

	/**
	 * 内容是否合法
	 * @param content
	 * @return
	 */
	public static boolean isContentIll(String content) {
		Matcher matcher = contentPattern.matcher(content);
		StringBuilder sb = new StringBuilder();
		while (matcher.find()) {
			sb.append(matcher.group());
		}
		String ill = sb.toString();
		if (!ill.equals(content))
			return false; // 有特殊字符
		return true;
	}

	private static Pattern namePattern = Pattern
			.compile("[\u4e00-\u9fa50-9a-zA-Z]+");

	/**
	 * @param name
	 * @return
	 */
	public static boolean isNameIll(String name) {
		Matcher matcher = namePattern.matcher(name);
		StringBuilder sb = new StringBuilder();
		while (matcher.find()) {
			sb.append(matcher.group());
		}
		String ill = sb.toString();
		if (!ill.equals(name))
			return false; // 有特殊字符
		return true;
	}

	/**
	 * 获取随机值
	 * @param minNumber
	 * @param maxNumber
	 * @return
	 */
	public static int getRandom(int minNumber, int maxNumber) {
		if (minNumber > maxNumber) {
			int temp = minNumber;
			minNumber = maxNumber;
			maxNumber = temp;
		}
		return (int) (minNumber + Math.random() * (maxNumber - minNumber + 1));
	}

	/**
	 * 按顺序加入元素(插入排序)
	 * @param list
	 * @param o
	 *            (实现Comparable接口)
	 * @param isDesc-降序 false-升序
	 */
	@SuppressWarnings("unchecked")
	public static <T> void addOrderList(List<T> list, T o, boolean isDesc) {
		for (int i = 0; i < list.size(); i++) {
			if (isDesc) {
				if (((Comparable<T>) o).compareTo(list.get(i)) == 1) {
					list.add(i, o);
					return;
				}
			} else {
				if (((Comparable<T>) o).compareTo(list.get(i)) == -1) {
					list.add(i, o);
					return;
				}
			}
		}
		list.add(o);
	}

	/**
	 * 返回字符串长度
	 * @param value
	 * @return
	 */
	public static int stringLength(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/**
	 * 判断一个map是否为空或没有元素
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return
	 */
	public static <K, V> boolean isMapEmpty(Map<K, V> map) {
		if (map == null || map.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 转换字节
	 * @param fileS
	 * @return
	 */
	public static String formetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 是否包装类型
	 * @param type1
	 * @param type2
	 * @return
	 */
	public static boolean isWrappedType(Class<?> type1, Class<?> type2) {
		if (type1 == int.class && type2 == Integer.class)
			return true;
		else if (type2 == int.class && type1 == Integer.class)
			return true;
		else if (type1 == long.class && type2 == Long.class)
			return true;
		else if (type2 == long.class && type1 == Long.class)
			return true;
		else if (type1 == short.class && type2 == Short.class)
			return true;
		else if (type2 == short.class && type1 == Short.class)
			return true;
		else if (type2 == byte.class && type1 == Byte.class)
			return true;
		else if (type1 == byte.class && type2 == Byte.class)
			return true;
		else if (type2 == float.class && type1 == Float.class)
			return true;
		else if (type1 == float.class && type2 == Float.class)
			return true;
		else if (type2 == double.class && type1 == Double.class)
			return true;
		else if (type1 == double.class && type2 == Double.class)
			return true;
		else if (type2 == boolean.class && type1 == Boolean.class)
			return true;
		else if (type1 == boolean.class && type2 == Boolean.class)
			return true;

		return false;
	}

	/**
	 * 设置dest field 为 value
	 * @param name
	 * @param src
	 * @return
	 */
	public static Field getField(String name, Object src) {
		Map<String, Field> data = fieldMap.get(src.getClass());
		if (data == null) {
			data = new HashMap<String, Field>();
			fieldMap.put(src.getClass(), data);
		}
		Field field = data.get(name);
		if (field == null) {
			try {
				field = src.getClass().getDeclaredField(name);
			} catch (Exception e) {
				e.printStackTrace();
			}
			data.put(name, field);
		}
		return field;
	}

	/**
	 * 设置dest field 为 value
	 * @param field
	 * @param dest
	 * @return boolean
	 */
	public static boolean setField(Field field, Object dest, Object value) {
		if (!field.getType().isAssignableFrom(value.getClass())
				&& !isWrappedType(field.getType(), value.getClass())) {
			// 类型不匹配
			Object data = conver(value, field.getType());
			if (data == null) {
				return false;
			}
			value = data;
		}
		field.setAccessible(true);
		// 不忽略空值
		try {
			field.set(dest, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 把数字或字符串转换为需要的对象
	 * @param value
	 * @param c
	 * @return
	 */
	public static Object conver(Object value, Class<?> c) {
		Class<?> type = value.getClass();
		if (type == c) {
			return value;
		}
		if (String.class.equals(c)) {
			return value.toString();
		} else if (Number.class.isAssignableFrom(value.getClass()) && (Number.class.isAssignableFrom(c) || c.isPrimitive())) {
			Number number = (Number) value;
			if (Integer.class.equals(c) || int.class == c) {
				return number.intValue();
			} else if (Long.class.equals(c) || long.class == c) {
				return number.longValue();
			} else if (Double.class.equals(c) || double.class == c) {
				return number.doubleValue();
			} else if (Float.class.equals(c) || float.class == c) {
				return number.floatValue();
			} else if (Short.class.equals(c) || short.class == c) {
				return number.shortValue();
			} else if (Byte.class.equals(c) || byte.class == c) {
				return number.byteValue();
			} else {
				return null;
			}
		} /*
			 * else if(c.isPrimitive()){
			 * return 0;
			 * }
			 */
		return null;
	}
	
	/**
	 * 生成订单号
	 * @author admin
	 * @return
	 * @date 2017年9月1日 下午5:54:43
	 * @return String
	 */
	public static  String createOrderNo() {
		LocalDateTime now = LocalDateTime.now();
		StringBuilder sBuilder = new StringBuilder();
		String timeString = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
		ThreadLocalRandom tlr = ThreadLocalRandom.current();
		//生成1000-999999之间的随机数
		long longno = tlr.nextLong(1000, 999999l);
		sBuilder.append(timeString).append(longno);
		return sBuilder.toString();
	}

	/**
	 * @explain: 计算百分比
	 * @author: Qiang
	 * @version: 1.0.0
	 * @date; 2017/12/26 15:55
	 * @param: [total, num]
	 * @return: java.lang.Double
	 */
	public static Double computeScale(int total, int num) {
		//创建一个数值格式化对象
		java.text.NumberFormat numberformat = java.text.NumberFormat.getInstance();

		//设置精确到小数点后2位
		numberformat.setMaximumFractionDigits(3);

		String result = numberformat.format((float)num / (float)total * 100);

		return Double.parseDouble(result);
	}
	
	/**
	 * @param phoneNumber
	 * @return
	 */
	public static String subPhoneLastFourNumber(String phoneNumber) {
		String strsub = Constants.DEFAULT_NULL;
		try {
			// 判断是否长度大于等于4
			if (phoneNumber.length() >= 4) {
				// 一个参数表示截取传递的序号之后的部分
				strsub = phoneNumber.substring(phoneNumber.length() - 4,phoneNumber.length());
			} else {
				return strsub;
			}
		} catch (Exception e) {
			strsub = Constants.DEFAULT_NULL;
		}
		return strsub;
	}

}
