package com.roncoo.example.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.roncoo.example.util.intercepetor.entity.LogHolder;
import com.roncoo.example.util.intercepetor.entity.LogModel;

/**
 * 经过测试，@RequestMapping对应的VLUE前 加不加/都可以正确访问。
 * 
 * @date:2017年11月22日下午1:11:33
 */
@RequestMapping("web")
@Controller
public class WebController {

	private static final Logger logger = LoggerFactory.getLogger(WebController.class);

	@RequestMapping("index")
	// @StringResult("aaa")
	public String index(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		LogModel temp = LogHolder.getLog();
		logger.info("这里是controller");
		map.put("title", "hello world");
		return "index"; // 注意，不要在最前面加上/，linux下面会出错
	}
	@RequestMapping("index2")
	public String index2(Model model, HttpServletRequest request, HttpServletResponse response) {
		LogModel temp = LogHolder.getLog();
		logger.info("这里是controller");
		model.addAttribute("title", "hello world!");
		return "index"; // 注意，不要在最前面加上/，linux下面会出错
	}

	@RequestMapping("data")
	@ResponseBody
	public Map<String, Object> indexData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		map.put("title", "hello world");
		return map;
	}

	@RequestMapping("data2")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> indexData2(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		map.put("title", "hello world");
		ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping("download")
	public ResponseEntity download(String filepath) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "filename.suffix");
		File file = new File(filepath);
		byte[] bytes = FileUtils.readFileToByteArray(file);
		return new ResponseEntity(bytes, headers, HttpStatus.CREATED);
	}

	@RequestMapping("error")
	public String error(ModelMap map) {
		throw new RuntimeException("测试异常");
	}

}
