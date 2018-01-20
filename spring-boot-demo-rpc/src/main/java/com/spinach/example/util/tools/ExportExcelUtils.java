package com.spinach.example.util.tools;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>
 * 导出excel
 * </p>
 * @version 1.0
 * @date 2017/08/03
 */
public class ExportExcelUtils {
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Logger logger = LoggerFactory.getLogger(ExportExcelUtils.class);
	private static String defaultPath;
	static {
		try {
			defaultPath = ResourceUtils.getPropertyInSystem("excel.download");
		} catch (Exception e) {
			logger.error("请在ystem.properties中配置excel.download属性。");
		}
	}

	/**
	 * 导出Excel文件到指定位置
	 * 
	 * @param head Excel文件头部内容数组
	 * @param key Excel文件头部对应的字段
	 * @param list 要导到excel的数据
	 * @param name 文件名
	 */
	public static void exportExcel(String[] head, String[] key, List list, String name, String path) {
		FileOutputStream file = null;
		try {
			/*
			 * HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); String url =
			 * request.getSession().getServletContext().getRealPath("/WEB-INF/download"); String fileName = name + sf.format(new Date()) + ".xls"; String path =
			 * url + "\\" + fileName;
			 */
			String temp = (path == null ? defaultPath : path) + "/" + name + sf.format(new Date()) + ".xls";
			file = new FileOutputStream(temp);
			HSSFWorkbook wb = witeWorkbook(head, key, list);
			wb.write(file);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				file.flush();
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 读写excel
	 * 
	 * @param head excel文件头部内容数组
	 * @param key excel文件头部对应的字段数组
	 * @param list excel数据
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static HSSFWorkbook witeWorkbook(String[] head, String[] key, List<Map<String, Object>> list) {
		HSSFWorkbook wb = new HSSFWorkbook();
		// 在excel中创建一个sheet
		HSSFSheet sheet = wb.createSheet();
		// 添加表头 第一行
		HSSFRow row = sheet.createRow((int) 0);
		// 添加居中样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		for (int i = 0; i < head.length; i++) {
			// 创建列
			HSSFCell cell = row.createCell((short) i);
			cell.setCellStyle(style);
			cell.setCellValue(head[i]);
		}
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> obj = list.get(i);
			row = sheet.createRow((int) i + 1);
			for (int j = 0; j < key.length; j++) {
				HSSFCell cell = row.createCell((short) j);
				cell.setCellStyle(style);
				String val = obj.get(key[j]).toString();
				cell.setCellValue(val == null || "null".equals(val) ? "" : val);
			}
		}
		return wb;
	}

	/**
	 * <p>
	 * 导出list到EXCEL中
	 * </p>
	 * 
	 * @author wanghuihui
	 * @date 2017-8-14上午10:35:40
	 * @param head 标题
	 * @param key 标题对应的字段名
	 * @param list 集合
	 * @param excelName 文件名
	 * @param path 路径，如果为空，默认为取默认路径。
	 */
	public static void exportExcelFormList(String[] head, String[] key, List<Map<String, Object>> list, String excelName, String path) {
		FileOutputStream file = null;
		try {
			String temp = (CommonTool.isNull(path) ? defaultPath : path) + "/" + excelName + sf.format(new Date()) + ".xls";
			file = new FileOutputStream(temp);
			HSSFWorkbook wb = witeWorkbookFormList(head, key, list);
			wb.write(file);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				file.flush();
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <p>
	 * 获得 HSSFWorkbook 流。
	 * </p>
	 * 
	 * @author wanghuihui
	 * @date 2017-8-14上午10:38:16
	 * @param head
	 * @param key
	 * @param list
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static HSSFWorkbook witeWorkbookFormList(String[] head, String[] key, List<Map<String, Object>> list) {
		HSSFWorkbook wb = new HSSFWorkbook();

		// 添加居中样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		int count = list.size();
		if (count > 50000) {
			int number = 50000;
			long forCount = 0;
			if (count % number == 0) {
				forCount = count / number;
			} else {
				forCount = count / number + 1;
			}
			for (int flag = 0; flag < forCount; flag++) {
				// 在excel中创建i个sheet
				HSSFSheet sheet_flag = wb.createSheet();
				// 添加表头 第一行
				HSSFRow row1 = sheet_flag.createRow((int) 0);
				for (int i = 0; i < head.length; i++) {
					// 创建列
					HSSFCell cell = row1.createCell((short) i);
					cell.setCellStyle(style);
					cell.setCellValue(head[i]);
				}

				int index = 50000;
				if (flag == forCount - 1) {
					index = count % 50000;
				}
				for (int i = 0; i < index; i++) {
					int temp = flag * 50000 + i;
					Map<String, Object> obj = list.get(temp);
					row1 = sheet_flag.createRow((int) i + 1);
					for (int j = 0; j < key.length; j++) {
						HSSFCell cell = row1.createCell((short) j);
						cell.setCellStyle(style);
						Object val = obj.get(key[j]);
						cell.setCellValue(val == null || "null".equals(val) ? "" : val.toString());
					}
				}
			}

		} else {
			// 在excel中创建一个sheet
			HSSFSheet sheet = wb.createSheet();
			// 添加表头 第一行
			HSSFRow row = sheet.createRow((int) 0);
			for (int i = 0; i < head.length; i++) {
				// 创建列
				HSSFCell cell = row.createCell((short) i);
				cell.setCellStyle(style);
				cell.setCellValue(head[i]);
			}
			for (int i = 0; i < count; i++) {
				Map<String, Object> obj = list.get(i);
				row = sheet.createRow((int) i + 1);
				for (int j = 0; j < key.length; j++) {
					HSSFCell cell = row.createCell((short) j);
					cell.setCellStyle(style);
					Object val = obj.get(key[j]);
					cell.setCellValue(val == null || "null".equals(val) ? "" : val.toString());
				}
			}
		}
		return wb;
	}

	/**
	 * 从浏览器导出Excel
	 * 
	 * @param response
	 * @param head
	 * @param key
	 * @param array
	 * @param fileName
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public static void dowond(HttpServletResponse response, String[] head, String[] key, List<Map<String, Object>>list, String fileName) throws UnsupportedEncodingException {
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		String ename = fileName + sf.format(new Date()) + ".xls";
		// 导出excel中文名
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(ename.getBytes("gbk"), "iso8859-1") + ".xls");
		OutputStream os = null;
		try {
			HSSFWorkbook wb = witeWorkbookFormList(head, key, list);
			os = response.getOutputStream();
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
