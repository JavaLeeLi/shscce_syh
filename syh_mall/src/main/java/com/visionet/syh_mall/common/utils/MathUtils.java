package com.visionet.syh_mall.common.utils;

/**
 * 如果需要精确计算,一要要用String来够造BigDecimal
 */

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精 确的浮点数运算，包括加减乘除和四舍五入。
 */
public class MathUtils {

	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 构造
	 */
	private MathUtils() {

	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * 
	 * @param v2
	 *            加数
	 * 
	 * @return 两个参数的和
	 */

	public static double add(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.add(b2).doubleValue();

	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * 
	 * @param v2
	 *            加数
	 * 
	 * @return 两个参数的和
	 */

	public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
		if (v1 == null) {
			v1 = BigDecimal.valueOf(0);
		}
		if (v2 == null) {
			v2 = BigDecimal.valueOf(0);
		}
		BigDecimal b1 = new BigDecimal(v1.toString());

		BigDecimal b2 = new BigDecimal(v2.toString());

		return b1.add(b2);

	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */

	public static double sub(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.subtract(b2).doubleValue();

	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */

	public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {

		BigDecimal b1 = new BigDecimal(v1.toString());

		BigDecimal b2 = new BigDecimal(v2.toString());

		return b1.subtract(b2);

	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * 
	 * @param v2
	 *            乘数
	 * 
	 * @return 两个参数的积
	 */

	public static double mul(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.multiply(b2).doubleValue();

	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * 
	 * @param v2
	 *            乘数
	 * 
	 * @return 两个参数的积
	 */

	public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {

		BigDecimal b1 = new BigDecimal(v1.toString());

		BigDecimal b2 = new BigDecimal(v2.toString());

		return b1.multiply(b2);

	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 * 
	 * 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * 
	 * @param v2
	 *            除数
	 * 
	 * @return 两个参数的商
	 */

	public static double div(double v1, double v2) {

		return div(v1, v2, DEF_DIV_SCALE);

	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 * 
	 * 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * 
	 * @param v2
	 *            除数
	 * 
	 * @return 两个参数的商
	 */

	public static BigDecimal div(BigDecimal v1, BigDecimal v2) {

		return div(v1, v2, DEF_DIV_SCALE);

	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 
	 * 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * 
	 * @param v2
	 *            除数
	 * 
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * 
	 * @return 两个参数的商
	 */

	public static double div(double v1, double v2, int scale) {

		if (scale < 0) {

			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");

		}

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 
	 * 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * 
	 * @param v2
	 *            除数
	 * 
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * 
	 * @return 两个参数的商
	 */

	public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {

		if (scale < 0) {

			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");

		}

		BigDecimal b1 = new BigDecimal(v1.toString());

		BigDecimal b2 = new BigDecimal(v2.toString());

		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);

	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * 
	 * @param scale
	 *            小数点后保留几位
	 * 
	 * @return 四舍五入后的结果
	 */

	public static double round(double v, int scale) {

		if (scale < 0) {

			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");

		}

		BigDecimal b = new BigDecimal(Double.toString(v));

		BigDecimal one = new BigDecimal("1");

		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * 
	 * @param scale
	 *            小数点后保留几位
	 * 
	 * @return 四舍五入后的结果
	 */

	public static BigDecimal round(BigDecimal v, int scale) {

		if (scale < 0) {

			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");

		}

		BigDecimal b = new BigDecimal(v.toString());

		BigDecimal one = new BigDecimal("1");

		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP);

	}

	/**
	 * 
	 * Description: 格式化，参数传递"#0.00"
	 * 
	 * @param
	 * @return String
	 * @throws
	 */
	public static String format(Double v) {
		return format(v, "#0.00");
	}

	/**
	 * 
	 * Description: 格式化，参数传递"#0.00"
	 * 
	 * @param
	 * @return String
	 * @throws
	 */
	public static String format(Double v, String format) {
		DecimalFormat currentNumberFormat = new DecimalFormat(format);
		String v1 = currentNumberFormat.format(v); // 支付金额
		return v1;
	}

	/**
	 * 
	 * Description:保留两位小数
	 * 
	 * @param
	 * @return String
	 * @throws
	 */
	public static double round(Double v) {
		return round(v, 2);
	}

	/**
	 * 保留两位小数 不四舍五入
	 * 
	 * @param v
	 * @return
	 */
	public static double rounding(Double v) {
		DecimalFormat formater = new DecimalFormat();
		formater.setMaximumFractionDigits(2);
		formater.setGroupingSize(0);
		formater.setRoundingMode(RoundingMode.FLOOR);
		return Double.parseDouble(formater.format(v));
	}

	/**
	 * 处理数学计算空异常
	 * 
	 * @param v1
	 *            值1
	 * @param v2
	 *            值2
	 * @return 相加结果
	 */
	public static double arithAdd(Double v1, Double v2) {
		if (v1 == null) {
			v1 = 0d;
		}
		if (v2 == null) {
			v2 = 0d;
		}
		return MathUtils.add(v1, v2);
	}
	
	/**
	 * 其它类型转换为BigDecimal
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("null")
	public static BigDecimal getBigDecimal(Object obj) {
		BigDecimal ret = null;
		if (null == obj) {
			throw new ClassCastException("Not possible to coerce [" + obj
					+ "] from class " + obj.getClass() + " into a BigDecimal.");
		}
		if (obj instanceof BigDecimal) {
			ret = (BigDecimal) obj;
		} else if (obj instanceof String) {
			ret = new BigDecimal((String) obj);
		} else if (obj instanceof BigInteger) {
			ret = new BigDecimal((BigInteger) obj);
		} else if (obj instanceof Number) {
			ret = new BigDecimal(((Number) obj).doubleValue());
		}
		return ret;
	}

	public static void main(String[] args) {
		float v = 100.256f;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("key", v);
		BigDecimal big = getBigDecimal(map.get("key"));
		System.out.println(big);
	}
};
