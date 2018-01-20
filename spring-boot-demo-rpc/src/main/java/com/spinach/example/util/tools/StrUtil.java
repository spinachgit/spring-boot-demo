package com.spinach.example.util.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StrUtil {

	private static final String ZEROSTR = "0000000000000000000000000000000000000000";
	private static final String BLANKSTR = "                                        ";

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(StrUtil.class);

	/**
	 * 去掉空格后不为空
	 * 
	 * @param strVal
	 * @return
	 */
	public static boolean isNotBlank(String strVal) {
		return StringUtils.isNotBlank(strVal);
	}

	/**
	 * 不为空
	 * 
	 * @param strVal
	 * @return
	 */
	public static boolean isNotEmpty(String strVal) {
		return StringUtils.isNotEmpty(strVal);
	}

	/**
	 * 判断是否为NULL或者为空
	 * 
	 * @param obj
	 * @return 是返回true，否则返回false
	 * @author Shang jingmin
	 */
	public static boolean isNull(Object obj) {
		if (obj == null || "".equals(obj) || "NULL".equals(obj) || "null".equals(obj)) {
			return true;

		}
		return false;
	}

	/**
	 * 判断是否为NULL或者为空
	 * 
	 * @param obj
	 * @return 是返回true，否则返回false
	 * @author Shang jingmin
	 */
	public static boolean isNullTwo(Object obj) {
		if (obj == null || "NULL".equals(obj) || "null".equals(obj)) {
			return true;
		}
		return false;
	}

	/**
	 * 将一个以逗号隔开的字符串转化成字符串数组
	 * 
	 * @param str String 以逗号隔开的字符串
	 * @return String[] 字符串数组
	 */
	public static String[] split(String str) {
		return split(str, ",");
	}

	/**
	 * 将用指定符号格开的字符串转化成字符串数组
	 * 
	 * @param str 带有指定符号的字符串
	 * @param delim 指定符号
	 * @return 对应的字符串数组
	 */
	public static String[] split(String str, String delim) {
		return StringUtils.split(str, delim);
	}

	/**
	 * 将用指定符号格开的字符串转化成list
	 * 
	 * @param str
	 * @param delim
	 * @return
	 */
	public static List<String> splitList(String str, String delim) {
		List<String> listStr = new ArrayList<String>();
		String[] strs = null;

		if (str != null && !"".equals(str)) {
			strs = split(str, delim);
			for (String s : strs) {
				listStr.add(s);
			}
		}

		return listStr;
	}

	/**
	 * 将一个字符串数组转换成用分隔符隔开的字符串
	 * 
	 * @param str String[]　字符串数组
	 * @param delim String　分隔符
	 * @return String 用分隔符隔开的字符串
	 */
	public static String union(String[] str, String delim) {
		return StringUtils.join(str, delim);
	}

	/**
	 * 将一个字符串数组转换成用逗号符隔开的字符串
	 * 
	 * @param str String[] 字符串数组
	 * @return String 用逗号符隔开的字符串
	 */
	public static String union(String[] str) {
		return union(str, ",");
	}

	/**
	 * 将一个对象数组转换成用分隔符隔开的字符串
	 * 
	 * @param str Object[]　字符串数组
	 * @param delim String　分隔符
	 * @return String 用分隔符隔开的字符串
	 */
	public static String union(Object[] str, String delim) {
		return StringUtils.join(str, delim);
	}

	/**
	 * 将一个对象数组转换成用逗号符隔开的字符串
	 * 
	 * @param str Object[] 字符串数组
	 * @return String 用逗号符隔开的字符串
	 */
	public static String union(Object[] str) {
		return union(str, ",");
	}

	/**
	 * 将指定字符串中的标记用相应的字符串替换掉
	 * 
	 * @param source 需要替换的字符串
	 * @param tag 标记
	 * @param value 替换值
	 * @return 相关标记被替换的字符串
	 */
	public static String replaceString(String source, String tag, String value) {
		return StringUtils.replace(source, tag, value);
	}

	/**
	 * 将指定字符串中的标记用相应的字符串替换掉，忽略大小写
	 * 
	 * @param source
	 * @param tag
	 * @param value
	 * @return
	 */
	public static String replaceStringIgnoreCase(String source, String tag, String value) {

		if (source == null || "".equals(source))
			return source;

		String str = "";
		int start = -1;
		String right = source;
		// 全部变为大写进行查找
		String sourceUpper = right.toUpperCase();
		String tagUpper = tag.toUpperCase();

		while (true) {
			start = sourceUpper.indexOf(tagUpper);

			if (start == -1) {
				// 说明没找到一个标记
				if ("".equals(str)) {
					str = right;
				} else {
					str += right;
				}
				break;
			}
			str += right.substring(0, start) + value;
			right = right.substring(start + tag.length());
			sourceUpper = sourceUpper.substring(start + tag.length());
		}

		return str;
	}

	/**
	 * 将文本按照HTML的要进行编码
	 * 
	 * @param html 原始的字符串
	 * @return 转换后的字符串
	 */
	public static String encodeHTML(String source) {
		String html = "";

		List<String> lst = new ArrayList<String>();
		// 将长的字符串转化成多个长度为100的短字符串
		int shortStrLen = 100;
		String src = source;
		while (source.length() > shortStrLen) {
			String tempStr = source.substring(0, shortStrLen);
			lst.add(tempStr);
			src = source.substring(shortStrLen);
		}
		lst.add(src);

		for (int i = 0; i < lst.size(); i++) {
			String tempStr = lst.get(i);
			tempStr = replaceString(tempStr, " ", "&nbsp;");
			tempStr = replaceString(tempStr, "<", "&lt");
			tempStr = replaceString(tempStr, ">", "&gt");
			tempStr = replaceString(tempStr, "\"", "&quot;");
			tempStr = replaceString(tempStr, "\r\n", "<br>");
			html += tempStr;
		}
		lst.clear();
		lst = null;

		html = replaceString(html, "\r\n", "<br>");

		return html;
	}

	/**
	 * 去除左边多余的空格。
	 * 
	 * @param value 待去左边空格的字符串
	 * @return 去掉左边空格后的字符串
	 */
	public static String trimLeft(String value) {

		String result = value;
		if (result == null)
			return result;
		char[] ch = result.toCharArray();
		int index = -1;
		for (int i = 0; i < ch.length; i++) {
			if (Character.isWhitespace(ch[i])) {
				index = i;
			} else {
				break;
			}
		}
		if (index != -1) {
			result = result.substring(index + 1);
		}
		return result;
	}

	/**
	 * 去除右边多余的空格。
	 * 
	 * @param value 待去右边空格的字符串
	 * @return 去掉右边空格后的字符串
	 */
	public static String trimRight(String value) {
		String result = value;
		if (result == null)
			return result;
		char[] ch = result.toCharArray();
		int endIndex = -1;
		for (int i = ch.length - 1; i > -1; i--) {
			if (Character.isWhitespace(ch[i])) {
				endIndex = i;
			} else {
				break;
			}
		}
		if (endIndex != -1) {
			result = result.substring(0, endIndex);
		}
		return result;
	}

	/**
	 * @param str
	 * @return boolean
	 */
	public static boolean isNotNullAndBlank(String str) {
		return !isNullOrBlank(str);
	}

	public static boolean isNullOrBlank(String str) {
		return str == null || "".equals(str.trim()) || str.equalsIgnoreCase("null");
	}

	public static String ifNullToBlank(String str) {
		if (str != null && !("null".equals(str.trim()))) {
			return str.trim();
		} else {
			return "";
		}
	}

	public static String ifNullToBlank(Object obj) {
		String ret = "";
		String s = String.valueOf(obj);

		if (s == null || "".equals(s) || "null".equals(s) || "NULL".equals(s)) {
			ret = "";
		} else {
			ret = s;
		}

		return ret;
	}

	public static String toLower(String str) {
		if (str != null && !(str.trim().equals("null"))) {
			return str.trim().toLowerCase();
		} else {
			return "";
		}
	}

	/**
	 * @param str
	 * @param length
	 * @return String
	 */
	public static String formatString(String str, int length) {
		StringBuilder sb = new StringBuilder();

		if (str != null && !"null".equals(str.trim())) {

			if (str.length() < length) {
				for (int i = 0; i < length - str.length(); i++) {
					sb.append("0");
				}
				sb.append(str);
			} else {

				sb.append(str);
			}
		} else {
			return "";
		}

		return sb.toString();
	}

	/**
	 * @param intStr
	 * @param length
	 * @return String
	 */
	public static String formatString(int intStr, int length) {
		return formatString(String.valueOf(intStr), length);
	}

	/**
	 * @param intStr
	 * @param length
	 * @return String
	 */
	public static String formatString(long intStr, int length) {
		return formatString(String.valueOf(intStr), length);
	}

	/**
	 * @param intStr
	 * @param length
	 * @return String
	 */
	public static String formatString(BigDecimal intStr, int length) {
		return formatString(String.valueOf(intStr), length);
	}

	public static String formatString(String str) {
		return formatString(str, 2);
	}

	public static String formatString(int intStr) {
		return formatString(Integer.toString(intStr), 2);
	}

	/**
	 * @param firstStr
	 * @param defaultStr
	 * @return String
	 */
	public static String getDefaultOrSpecifiedValue(String firstStr, String defaultStr) {
		if (isNullOrBlank(firstStr)) {
			return defaultStr;
		} else {
			return firstStr;
		}
	}

	/**
	 * @param sourceStr
	 * @return Collection
	 */
	public static List<String> filterStrBySpeciaChar2List(String sourceStr) {
		List<String> target = new ArrayList<String>();
		String tempStr = null;
		int begin = 0;

		while (sourceStr.indexOf(";", begin) != -1) {
			tempStr = sourceStr.substring(begin, sourceStr.indexOf(";", begin));
			target.add(tempStr);
			begin = sourceStr.indexOf(";", begin) + 1;
		}

		return target;
	}

	/**
	 * @param sourceStr
	 * @return Collection
	 */
	public static List<String> filterStrBySpeciaChar2List(String sourceStr, String sign) {
		List<String> target = new ArrayList<String>();
		String tempStr = null;
		int begin = 0;

		while (sourceStr.indexOf(sign, begin) != -1) {
			tempStr = sourceStr.substring(begin, sourceStr.indexOf(sign, begin));
			target.add(tempStr);
			begin = sourceStr.indexOf(sign, begin) + 1;
		}

		return target;
	}

	/**
	 * @param sourceStr
	 * @param oldStr
	 * @param newStr
	 * @return String
	 */
	public static String filter(String sourceStr, char filterChar) {
		if (sourceStr == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < sourceStr.length(); i++) {
			if (sourceStr.charAt(i) != filterChar) {
				sb.append(sourceStr.substring(i, i + 1));
			}

		}
		return sb.toString();
	}

	/**
	 * @param sourceStr String
	 * @return String
	 */
	public static String firstToUpperCase(String sourceStr) {
		if (isNullOrBlank(sourceStr))
			return sourceStr;
		if (sourceStr.length() == 1)
			return sourceStr.toUpperCase();

		return formatString(sourceStr.substring(0, 1).toUpperCase() + sourceStr.substring(1));

	}

	/**
	 * @param source String
	 * @param target String
	 * @return int
	 */
	public static int indexOfIgnoreCase(String source, String target) {
		if (source == null || target == null)
			return -1;
		String src = source.toLowerCase();
		String tgt = target.toLowerCase();
		return src.indexOf(tgt);
	}

	/**
	 * @param source String
	 * @param target String
	 * @return int
	 */
	public static int lastIndexOfIgnoreCase(String source, String target) {
		if (source == null || target == null)
			return -1;
		String src = source.toLowerCase();
		String tgt = target.toLowerCase();
		return src.lastIndexOf(tgt);
	}

	/**
	 * @param source String
	 * @param target String
	 * @param index int
	 * @return int
	 */
	public static int indexOfIgnoreCase(String source, String target, int index) {
		String src = source.toLowerCase();
		String tgt = target.toLowerCase();

		return src.indexOf(tgt, index);
	}

	/**
	 * @param str1 String
	 * @param str2 String
	 * @return boolean
	 */
	public static boolean strTrimedEqual(String str1, String str2) {
		if (str1 != null && str2 != null)
			return str1.trim().equals(str2.trim());
		else
			return str1.equals(str2);
	}

	/**
	 * @param source String
	 * @param length int
	 * @return String
	 */
	public static String addLeftZero2Len(String source, int length) {
		if (isNullOrBlank(source))
			return ZEROSTR.substring(0, length);
		else {
			if (source.length() > length)
				return source.substring(source.length() - length);
			else
				return ZEROSTR.substring(0, length - source.length()) + source;
		}
	}

	/**
	 * @param source String
	 * @param length int
	 * @return String
	 */
	public static String addRightZero2Len(String source, int length) {
		if (isNullOrBlank(source))
			return ZEROSTR.substring(0, length);
		else {
			if (source.length() > length)
				return source.substring(source.length() - length);
			else
				return source + ZEROSTR.substring(0, length - source.length());
		}
	}

	/**
	 * @param source String
	 * @param length int
	 * @return String
	 */
	public static String addRightBlank2Len(String source, int length) {
		if (isNullOrBlank(source))
			return BLANKSTR.substring(0, length);
		else {
			if (source.length() > length)
				return source.substring(source.length() - length);
			else
				return source + BLANKSTR.substring(0, length - source.length());
		}

	}

	/**
	 * @param source String
	 * @param length int
	 * @return String
	 */
	public static String addLeftBlank2Len(String source, int length) {
		String result;
		if (isNullOrBlank(source))
			result = BLANKSTR.substring(0, length);
		else {
			if (source.length() > length)
				result = source.substring(source.length() - length);
			else
				result = BLANKSTR.substring(0, length - source.length()) + source;
		}

		return result;
	}

	/**
	 * @param result
	 * @param chars
	 */
	public static void mixChars(StringBuilder result, char[] chars) {
		if (chars != null && chars.length > 0) {
			int pos = (int) Math.floor(Math.random() * chars.length);
			if (result == null)
				result = new StringBuilder();
			result.append(chars[pos]);
			if (chars.length > 1) {
				char[] tmp = new char[chars.length - 1];
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (i != pos) {
						tmp[index] = chars[i];
						index++;
					}
				}
				mixChars(result, tmp);
			}
		}
	}

	/**
	 * @param is
	 * @return
	 */
	public static StringBuilder inputStream2Buffer(InputStream is) {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = "";
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			logger.error("转换出错!" + e.getMessage());
		}
		return sb;
	}

	/**
	 * @param source
	 * @param template
	 * @param target
	 * @return
	 */
	public static String replaceStr(String source, String template, String target) {
		int index = source.indexOf(template);
		if (index != -1) {
			source = source.substring(0, index) + target + source.substring(index + template.length());
		}
		return source;
	}

	/**
	 * @param source
	 * @param template
	 * @param target
	 * @return
	 */
	public static String replaceAllStr(String source, String template, String target) {
		int index = source.indexOf(template);
		while (index != -1) {
			source = source.substring(0, index) + target + source.substring(index + template.length());
			index = source.indexOf(template);
		}
		return source;
	}

	/**
	 * @param beginMarkSign
	 * @param endMarkSign
	 * @param source
	 * @param index
	 * @return
	 */
	public static int getMarkEndIndex(String beginMarkSign, String endMarkSign, String source, int startIndex) {
		int index = source.indexOf(endMarkSign, startIndex);
		int beginSignIndex = source.indexOf(beginMarkSign, startIndex);
		if (beginSignIndex == -1)
			return index;
		int tmpIndex = 0;

		while (index > beginSignIndex) {
			beginSignIndex = source.indexOf(beginMarkSign, beginSignIndex + 1);
			if (beginSignIndex == -1 || beginSignIndex > index)
				return index;
			tmpIndex = index;
			index = source.indexOf(endMarkSign, index + 1);
			if (beginSignIndex == -1) {
				if (index == -1)
					return tmpIndex;
				break;
			}
		}
		return index;
	}

	/**
	 * @param beginMarkSign
	 * @param endMarkSign
	 * @param source
	 * @param index
	 * @return
	 */
	public static int getIgnoreCaseMarkEndIndex(String beginMarkSign, String endMarkSign, String source, int startIndex) {
		String tmpSource = source.toLowerCase();
		String beginMarkSignTmp = beginMarkSign.toLowerCase();
		String endMarkSignTmp = endMarkSign.toLowerCase();
		return getMarkEndIndex(beginMarkSignTmp, endMarkSignTmp, tmpSource, startIndex);
	}

	/**
	 * @param source
	 * @param regex
	 * @return
	 */
	public static boolean matchs(String source, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(source);
		return m.find();
	}

	/**
	 * 处理XXXXXXXXXXXXXXX。
	 * 
	 * @author lidan
	 * @Date:2016-11-21
	 * @param str
	 * @param len
	 * @param position 0:在前面补足"0",其他值在后面补足"0"
	 * @return
	 */
	public static String repairZero(String str, int len, String position) {
		StringBuffer sb = new StringBuffer();
		StringBuffer strBuf = new StringBuffer();
		if (str.length() < len) {
			for (int j = 0; j < len - str.length(); j++) {
				strBuf.append("0");
			}
		}
		if ("0".equals(position)) {
			sb.append(strBuf.toString()).append(str);
		} else {
			sb.append(str).append(strBuf.toString());
		}
		System.out.println("sb:" + sb.toString());
		return sb.toString();
	}

	/**
	 * 去除数组里面重复的数据
	 * 
	 * @param strArr
	 * @return str
	 */
	public static String StrdeDuplication(String[] strArr) {
		String str = "";

		if (strArr.length > 0) {
			List<String> listAcno = new ArrayList<String>();
			for (int i = 0; i < strArr.length; i++) {
				if (!listAcno.contains(strArr[i])) {
					listAcno.add(strArr[i]);
				}
			}
			for (String stracno : listAcno) {
				str += stracno + ",";
			}

		}

		return str;
	}

	/**
	 * <p>
	 * 处理一个字符串儿在另外的字符串中出现的次数
	 * </p>
	 * 
	 * @param str
	 * @param key
	 * @return
	 */
	public static int countStrShowAllNum(String str, String key) {
		int count = 0;
		String newStr = str;
		int index = newStr.indexOf(key);
		while (index != -1) {
			newStr = newStr.substring(index + key.length(), newStr.length());
			count++;
			index = newStr.indexOf(key);
		}
		return count;
	}

	/**
	 * @param source
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean between(String source, String start, String end) {
		return source.compareTo(start) >= 0 && source.compareTo(end) <= 0;
	}

	public static void main(String[] args) {

	}
}
