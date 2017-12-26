package com.spinach.example.common.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class ResourceUtils {
	private static final Logger logger = LoggerFactory.getLogger(ResourceUtils.class);
	public static String system_prop_path = "";
	public static Map<String, String> propMap = new HashMap<>();
	static ResourceBundle bundle = ResourceBundle.getBundle("config.application",Locale.CHINESE);
	private static String env = bundle.getString("spring.profiles.active");
	private static Properties system_props ;
	
	static{
		switch (env) {
		case "dev":
			system_prop_path = "config/application-dev.properties";
			break;
		case "test":
			system_prop_path = "config/application-test.properties";
			break;
		case "prod":
			system_prop_path = "config/application-prod.properties";
			break;
		default:
			system_prop_path = "config/application.properties";
			break;
		}
		system_props = getProperties(system_prop_path);
	}
	
	public static String getPropertyInSystem(String key) throws Exception {
		String value = null;
		if (propMap.containsKey(key)) {
			value = propMap.get(key);
		} else {
			value = (String) system_props.get(key);
			if (StringUtils.isBlank(value)) {
				throw new Exception(system_prop_path+"文件中没有对应的KEY:"+key);
			}else{
				propMap.put(key, value);
			}
		}
		return value;
	}

	public static Properties getProperties(String filepath) {
		ClassPathResource resouce = new ClassPathResource(filepath);
		FileInputStream in = null;
		Properties property = new Properties();
		try {
			in = new FileInputStream(resouce.getFile());
			property.load(in);
		} catch (FileNotFoundException e) {
			logger.error("文件载入失败，请检查路径~！", e);
		} catch (IOException e) {
			logger.error("文件载入失败，请检查路径~！", e);
		}finally {
			try {
				if (in != null) {
					in.close();
					in = null;
				}
			} catch (Exception e) {

			}
		}
		return property;
	}

	
}
