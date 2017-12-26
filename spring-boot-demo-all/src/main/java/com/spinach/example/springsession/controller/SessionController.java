package com.spinach.example.springsession.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/session")
public class SessionController {
	private static final Logger logger =  LoggerFactory.getLogger(SessionController.class);
	@Value("${spinach.name}")
	private String name ;
	
	@RequestMapping(value = "/index")
	public String index(ModelMap map, HttpSession httpSession) {
		map.put("title", "第一个应用");
		map.put("sessionId", httpSession.getId());
		map.put("name", name);
		logger.info("sessionID=" + httpSession.getId());
		return "index";
	}
	
	@RequestMapping(value = "/map")
	@ResponseBody
	public Map<String, Object> map(HttpSession httpSession) {
		Map<String, Object>map = new HashMap<>(); 
		map.put("title", "第一个应用");
		map.put("sessionId", httpSession.getId());
		map.put("name", name);
		logger.info("sessionID=" + httpSession.getId());
		return map;
	}

	
	@RequestMapping("error")
	public String error(ModelMap map){
		throw new RuntimeException("测试异常");
	}

}
