package com.spinach.example.util.tools;

import java.util.List;

/**
 * <p>
 * 此工具用于数组的处理
 * </p>
 * 
 * @version 1.0
 * @author 赵年年
 * @date 2017/11/07
 */
public class ArrayUtil {
	/**
	 * <p>
	 * 将两个数组合并成一个数组
	 * </p>
	 * 
	 * @param a 数组
	 * @param b 数组
	 * @return
	 */
	public static String[] getOneArray(String aa, String bb) {
		String[] a = StrUtil.isNotNullAndBlank(aa) ? aa.split(",") : new String[0];
		String[] b = StrUtil.isNotNullAndBlank(bb) ? bb.split(",") : new String[0];

		String[] c = new String[a.length + b.length];
		for (int j = 0; j < a.length; ++j) {
			c[j] = a[j];
		}
		for (int j = 0; j < b.length; ++j) {
			c[a.length + j] = b[j];
		}
		return c;
	}

	/**
	 * <p>
	 * 将ArrayList数据转成String[]
	 * </p>
	 * 
	 * @param arrayList
	 * @return
	 */
	public static String[] arrayListToStringList(List<String> arrayList) {
		String[] strArr = new String[arrayList.size()];
		for (int i = 0; i < strArr.length; i++) {
			strArr[i] = arrayList.get(i);
		}
		return strArr;
	}

	/**
	 * <p>
	 * 将array数组转成","分割的string
	 * </p>
	 * 
	 * @param arrayList
	 * @return
	 */
	public static String arrayListToString(List<String> arrayList) {
		String str = "";
		StringBuffer sb = new StringBuffer();
		if (arrayList != null && arrayList.size() > 0) {
			for (int i = 0; i < arrayList.size(); i++) {
				sb.append(",");
				sb.append(arrayList.get(i));
			}
			if (StrUtil.isNotNullAndBlank(sb.toString())) {
				str = sb.toString().substring(1);
			}
		}
		return str;
	}
}
