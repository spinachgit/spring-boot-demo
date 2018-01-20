package com.spinach.example.util.tools;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spinach.example.base.service.UserService;

public class HttpClientRequest {

	private final static Logger logger = LoggerFactory.getLogger(HttpClientRequest.class);

	private static UserService userService;

	static {
		userService = (UserService) SpringContextUtils.getBean("dictionaryDetailServiceImpl");
	}

	/**
	 * webservice 接口调用获取数据
	 */
	public static String getWebService(Map<String, String> map) {
		logger.info("调用接口开始............");
		String str = "", tempStr = "";
		try {
			HttpURLConnection connection = null;
			OutputStream outputStream = null;
			URL url = null;
			InputStreamReader reader = null;

			StringBuilder builer = new StringBuilder();
			builer.append(map.get("xml"));
			logger.info("调用机场数据接口xml:" + map.get("xml"));
			StringBuilder bus = new StringBuilder();
			logger.info("调用机场数据接口:" + map.get("path"));
			bus.append(map.get("path"));

			url = new URL(bus.toString());
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			try {
				outputStream = connection.getOutputStream();
			} catch (Exception e) {
				logger.error("[定时获取机场信息失败]" + "时间:" + DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss") + ",错误信息:" + e.getMessage());
				e.printStackTrace();
			}

			byte[] bytes = builer.toString().getBytes();
			outputStream.write(bytes);
			outputStream.flush();
			InputStream input = connection.getInputStream();
			reader = new InputStreamReader(input);
			char charset[] = new char[1];
			StringBuffer result = new StringBuffer();
			while (reader.read(charset) != -1) {
				result.append(new String(charset));
			}
			tempStr = result.toString();
			str = result.toString().substring(result.toString().indexOf("<data>") + "<data>".length(), result.toString().lastIndexOf("</data>"));
		} catch (Exception e) {
			logger.error("[机场数据接口返回数据:" + tempStr + "] " + DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss") + " 错误信息:" + e.getMessage());
		}
		logger.info("调用接口结束............");
		return str;
	}

	/**
	 * webservice 接口调用获取数据
	 * 
	 * @param map
	 * @param interfaceName 接口名称
	 * @return
	 */
	public static String getWebServiceInfo(Map<String, String> map, String interfaceName) {
		logger.info("调用" + interfaceName + "接口开始............" + "时间:" + DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss"));
		String str = "";
		try {
			HttpURLConnection connection = null;
			OutputStream outputStream = null;
			URL url = null;
			InputStreamReader reader = null;

			StringBuilder builer = new StringBuilder();
			builer.append(map.get("xml"));
			logger.info("调用数据接口xml:" + map.get("xml"));
			StringBuilder bus = new StringBuilder();
			logger.info("调用数据接口path:" + map.get("path"));
			bus.append(map.get("path"));

			url = new URL(bus.toString());
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			try {
				outputStream = connection.getOutputStream();
			} catch (Exception e) {
				logger.error("[定时获取" + interfaceName + "数据接口]" + "时间:" + DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss") + ",错误信息:" + e.getMessage());
			}

			byte[] bytes = builer.toString().getBytes();
			outputStream.write(bytes);
			outputStream.flush();
			InputStream input = connection.getInputStream();
			reader = new InputStreamReader(input, "utf-8");
			char charset[] = new char[1];
			StringBuffer result = new StringBuffer();
			while (reader.read(charset) != -1) {
				result.append(new String(charset));
			}
			str = result.toString().substring(result.toString().indexOf("<return>") + "<return>".length(), result.toString().lastIndexOf("</return>"));
		} catch (Exception e) {
			logger.error("[" + interfaceName + "返回数据:" + str + "] " + DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss") + " 错误信息:" + e.getMessage());
		}
		logger.info("调用" + interfaceName + "接口结束............" + "时间:" + DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss"));
		return str;
	}
}
