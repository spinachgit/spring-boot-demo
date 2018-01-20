package com.spinach.example.util.tools;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GsonUtils {
	private static Logger logger = LoggerFactory.getLogger(GsonUtils.class);
	public static final String EMPTY = "";
	public static final String EMPTY_JSON = "{}";
	public static final String EMPTY_JSON_ARRAY = "[]";
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
	public static final Double SINCE_VERSION_10 = Double.valueOf(1.0D);

	public static final Double SINCE_VERSION_11 = Double.valueOf(1.1D);

	public static final Double SINCE_VERSION_12 = Double.valueOf(1.2D);

	public static String toJson(Object target, Type targetType, boolean isSerializeNulls, Double version,
			String datePattern, boolean excludesFieldsWithoutExpose) {
		if (target == null){
			return "{}";
		}
		GsonBuilder builder = new GsonBuilder();
		if (isSerializeNulls){
			builder.serializeNulls();
		}
		if (version != null){
			builder.setVersion(version.doubleValue());
		}
		if (isEmpty(datePattern)){
			datePattern = "yyyy-MM-dd HH:mm:ss";
		}
		builder.setDateFormat(datePattern);
		if (excludesFieldsWithoutExpose){
			builder.excludeFieldsWithoutExposeAnnotation();
		}
		String result = "";
		Gson gson = builder.create();
		try {
			if (targetType != null) {
				result = gson.toJson(target, targetType);
				return "{}";
			}
			result = gson.toJson(target);
		} catch (Exception ex) {
			logger.error("目标对象 " + target.getClass().getName() + " 转换 JSON 字符串时，发生异常！", ex);
			if ((!(target instanceof Collection)) && (!(target instanceof Iterator))
					&& (!(target instanceof Enumeration)) && (!(target.getClass().isArray()))) {
				return "[]";
			}
		}
		return result;
	}

	public static String toJson(Object target) {
		return toJson(target, null, false, null, null, true);
	}

	public static String toJson(Object target, String datePattern) {
		return toJson(target, null, false, null, datePattern, true);
	}

	public static String toJson(Object target, Double version) {
		return toJson(target, null, false, version, null, true);
	}

	public static String toJson(Object target, boolean excludesFieldsWithoutExpose) {
		return toJson(target, null, false, null, null, excludesFieldsWithoutExpose);
	}

	public static String toJson(Object target, Double version, boolean excludesFieldsWithoutExpose) {
		return toJson(target, null, false, version, null, excludesFieldsWithoutExpose);
	}

	public static String toJson(Object target, Type targetType) {
		return toJson(target, targetType, false, null, null, true);
	}

	public static String toJson(Object target, Type targetType, Double version) {
		return toJson(target, targetType, false, version, null, true);
	}

	public static String toJson(Object target, Type targetType, boolean excludesFieldsWithoutExpose) {
		return toJson(target, targetType, false, null, null, excludesFieldsWithoutExpose);
	}

	public static String toJson(Object target, Type targetType, Double version, boolean excludesFieldsWithoutExpose) {
		return toJson(target, targetType, false, version, null, excludesFieldsWithoutExpose);
	}

	public static <T> T fromJson(String json, TypeToken<T> token, String datePattern) {
		if (isEmpty(json)) {
			return null;
		}
		GsonBuilder builder = new GsonBuilder();
		if (isEmpty(datePattern)) {
			datePattern = "yyyy-MM-dd HH:mm:ss SSS";
		}
		Gson gson = builder.create();
		try {
			return gson.fromJson(json, token.getType());
		} catch (Exception ex) {
			logger.error(json + " 无法转换为 " + token.getRawType().getName() + " 对象!", ex);
		}
		return null;
	}

	public static <T> T fromJson(String json, TypeToken<T> token) {
		return fromJson(json, token, null);
	}

	public static <T> T fromJson(String json, Class<T> clazz, String datePattern) {
		if (isEmpty(json)) {
			return null;
		}
		GsonBuilder builder = new GsonBuilder();
		if (isEmpty(datePattern)) {
			datePattern = "yyyy-MM-dd HH:mm:ss SSS";
		}
		Gson gson = builder.create();
		try {
			return gson.fromJson(json, clazz);
		} catch (Exception ex) {
			logger.error(json + " 无法转换为 " + clazz.getName() + " 对象!", ex);
		}
		return null;
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		return fromJson(json, clazz, null);
	}

	public static boolean isEmpty(String inStr) {
		boolean reTag = false;
		if ((inStr == null) || ("".equals(inStr))) {
			reTag = true;
		}
		return reTag;
	}
}