package com.spinach.example.util.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DataChg {
	/**
	 * 将"yyyy年M月d日"格式的字符串转化成日期
	 * 
	 * @param str 日期字符串
	 * @return 日期对象
	 * @throws java.text.ParseException
	 */
	public static Date convertStringToDate(String str) {
		return convertStringToDate(str, "yyyy-MM-dd");
	}

	/**
	 * 将特定格式的字符串转化成日期
	 * 
	 * @param str 日期字符串
	 * @param pattern 字符串模式，如：yyyy年M月d日，yyyy-MM-dd等
	 * @return 日期对象
	 */
	public static Date convertStringToDate(String str, String pattern) {
		Date date = null;
		if (str == null || "".equals(str))
			return date;

		try {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			date = formatter.parse(str);
		} catch (ParseException e) {
			throw new RuntimeException("ERROR.COMMON.001"+ str);
		}

		return date;
	}

	/**
	 * 将日期转化成字符串，日期的模式为"yyyy年MM月dd日"
	 * 
	 * @param date 日期对象
	 * @return 表示日期的字符串
	 */
	public static String convertDateToString(Date date) {
		return convertDateToString(date, "yyyy-MM-dd");
	}

	/**
	 * 将日期对象转换成特定模式的字符串
	 * 
	 * @param date 日期对象
	 * @param pattern 字符串模式，如：yyyy年M月d日，yyyy-MM-dd等
	 * @return 特定格式的字符串
	 */
	public static String convertDateToString(Date date, String pattern) {
		String str = null;
		if (date == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		str = formatter.format(date);
		return str;
	}

	/**
	 * 将字符串转换成int值
	 * 
	 * @param str 字符串
	 * @return 对应的int值
	 */
	public static int convertStringToInt(String str) {
		return Integer.parseInt(str);
	}

	/**
	 * 将字符串转换成float值
	 * 
	 * @param str 字符串
	 * @return 对应的float值
	 */
	public static float convertStringToFloat(String str) {
		return Float.parseFloat(str);
	}

	/**
	 * 将字符串转换成double值
	 * 
	 * @param str 字符串
	 * @return 对应的double值
	 */
	public static double convertStringToDouble(String str) {
		if (str == null || "".equals(str))
			return 0;
		return Double.parseDouble(str);
	}

	/**
	 * 将java.util.Data类型转化成java.sql.Data
	 * 
	 * @param date java.util.Data对象
	 * @return java.sql.Data对象
	 */
	public static java.sql.Date convertDateToSqlDate(Date date) {
		if (date == null)
			return null;
		else
			return new java.sql.Date(date.getTime());
	}

	/**
	 * 将java.util.Data类型转化成java.sql.Timestamp
	 * 
	 * @param date java.util.Data对象
	 * @return java.sql.Timestamp对象
	 */
	public static java.sql.Timestamp convertDateToTimestamp(Date date) {
		if (date == null)
			return null;
		else
			return new java.sql.Timestamp(date.getTime());
	}

	/**
	 * 将获取的航班日期和四位字符串转换成 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param str字符串
	 * @return str字符串
	 * @throws ParseException
	 */
	public static String strDateHhMm(String dayLineDate, String endStr) {
		String date = "";
		if (!"".equals(dayLineDate) && !"".equals(endStr)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dayDate = dayLineDate + endStr;
				String str1 = dayDate.substring(0, 10);
				String str2 = dayDate.substring(10, 12);
				String str3 = dayDate.substring(12, 14);
				String str4 = str1 + " " + str2 + ":" + str3 + ":" + "00";
				java.util.Date utilDate = sdf.parse(str4);
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				date = sdf.format(sqlDate);
			} catch (Exception e) {
			}
		}

		return date;
	}

	/**
	 * <p>
	 * 将航班日期YYYY-MM-DD和六位(DDHH24MM)的时间格式的时间转化成YYYY-MM-DD HH:mm:ss
	 * </p>
	 * 
	 * @param DataStr YYYY-MM-DD
	 * @param sixTim DD24HHMM
	 * @return
	 * @throws ParseException
	 */
	public static String modeTimeFormatSixToNormal(String dataStr, String sixTim) throws ParseException {
		String normalTimeStr = "";
		if (StrUtil.isNotNullAndBlank(dataStr) && StrUtil.isNotNullAndBlank(sixTim) && sixTim.length() == 6) {
			dataStr = dataStr.replace("/", "-").replace(".", "-").replace("\\", "-");
			if (!"".equals(dataStr) && !"".equals(sixTim)) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date newDate = format.parse(dataStr);
				String preDateStr = format.format(DateUtil.addDays(newDate, 1));
				String nextDateStr = format.format(DateUtil.addDays(newDate, -1));
				String day = sixTim.substring(0, 2);
				try {
					if (day.equals(dataStr.substring(8, 10))) {
						normalTimeStr = dataStr.substring(0, dataStr.lastIndexOf("-") + 1) + sixTim.substring(0, 2) + " " + sixTim.substring(2, 4) + ":"
								+ sixTim.substring(4, 6) + ":00";
					} else if (day.equals(preDateStr.substring(8, 10))) {
						normalTimeStr = preDateStr.substring(0, preDateStr.lastIndexOf("-") + 1) + sixTim.substring(0, 2) + " " + sixTim.substring(2, 4) + ":"
								+ sixTim.substring(4, 6) + ":00";
					} else if (day.equals(nextDateStr.substring(8, 10))) {
						normalTimeStr = nextDateStr.substring(0, nextDateStr.lastIndexOf("-") + 1) + sixTim.substring(0, 2) + " " + sixTim.substring(2, 4)
								+ ":" + sixTim.substring(4, 6) + ":00";
					}
					java.util.Date utilDate = sdf.parse(normalTimeStr);
					java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
					normalTimeStr = sdf.format(sqlDate);
				} catch (Exception e) {
				}
			}
		}
		return normalTimeStr;
	}

	public static String modeTimeFormatSixToNormalFordori(String dataStr, String sixTim) throws ParseException {
		String normalTimeStr = "";
		dataStr = dataStr.replace("/", "-").replace(".", "-").replace("\\", "-");
		if (!"".equals(dataStr) && !"".equals(sixTim)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date newDate = format.parse(dataStr);
			String preDateStr = format.format(DateUtil.addDays(newDate, 1));
			String preDateStr2 = format.format(DateUtil.addDays(newDate, 2));
			String nextDateStr = format.format(DateUtil.addDays(newDate, -1));
			String day = sixTim.substring(0, 2);
			try {
				if (day.equals(dataStr.substring(8, 10))) {
					normalTimeStr = dataStr.substring(0, dataStr.lastIndexOf("-") + 1) + sixTim.substring(0, 2) + " " + sixTim.substring(2, 4) + ":"
							+ sixTim.substring(4, 6) + ":00";
				} else if (day.equals(preDateStr.substring(8, 10))) {
					normalTimeStr = preDateStr.substring(0, preDateStr.lastIndexOf("-") + 1) + sixTim.substring(0, 2) + " " + sixTim.substring(2, 4) + ":"
							+ sixTim.substring(4, 6) + ":00";
				} else if (day.equals(preDateStr2.substring(8, 10))) {
					normalTimeStr = preDateStr2.substring(0, preDateStr2.lastIndexOf("-") + 1) + sixTim.substring(0, 2) + " " + sixTim.substring(2, 4) + ":"
							+ sixTim.substring(4, 6) + ":00";
				} else if (day.equals(nextDateStr.substring(8, 10))) {
					normalTimeStr = nextDateStr.substring(0, nextDateStr.lastIndexOf("-") + 1) + sixTim.substring(0, 2) + " " + sixTim.substring(2, 4) + ":"
							+ sixTim.substring(4, 6) + ":00";
				}
				java.util.Date utilDate = sdf.parse(normalTimeStr);
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				normalTimeStr = sdf.format(sqlDate);
			} catch (Exception e) {
			}
		}
		return normalTimeStr;
	}

	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * 
	 * @return String
	 */
	public static String sysDate() {
		Date dt = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(dt);
	}

	/**
	 * 将字符串转换成日期格式 yyyy-mm-dd
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static String stringDateYyMmDd(String str) {
		String date = "";
		if (!"".equals(str)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date utilDate = sdf.parse(str);
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				date = sdf.format(sqlDate);
			} catch (Exception e) {
			}
		}

		return date;
	}

	/**
	 * 将获取的数量大于99时显示99+
	 * 
	 * @param count
	 * @return
	 */
	public static String StringUtilCount(String count) {
		return Integer.parseInt(count) > 99 ? "99+" : count;
	}
}
