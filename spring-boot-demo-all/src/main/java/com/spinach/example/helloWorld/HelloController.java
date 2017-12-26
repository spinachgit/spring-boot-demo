package com.spinach.example.helloWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spinach.example.common.handler.CommonException;
import com.spinach.example.common.utils.ResourceUtils;
import com.spinach.example.redis.JedisSingleUtils;

@Controller
@RequestMapping("/hello")
public class HelloController {
	private static final Logger logger =  LoggerFactory.getLogger(HelloController.class);
	@Value("${spinach.name}")
	private String name ;
	
	@RequestMapping(value = "/index")
	public String index(ModelMap map, HttpSession httpSession) {
		map.put("title", "第一个应用");
		map.put("sessionId", httpSession.getId());
		map.put("name", name);
		logger.info("sessionID=" + httpSession.getId());
		JedisSingleUtils.setObjectByKey("spinach_map", map,true);
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
		
		try {
			String temp = ResourceUtils.getPropertyInSystem("logging.config");
			System.out.println(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping(value = "/map/{name}")
	@ResponseBody
	public Map<String, Object> map(@PathVariable String name) {
		Map<String, Object>map = new HashMap<>(); 
		map.put("title", "第一个resful风格的例子！");
		map.put("name", name);
		return map;
	}

	
	@RequestMapping("error")
	public String error(ModelMap map){
		throw new RuntimeException("RuntimeException:aaa");
	}
	@RequestMapping("error2")
	public String error2(ModelMap map){
		throw new CommonException("CommonException:bbbbbbbbbbb ");
	}
	@RequestMapping("error3")
	public String error3(ModelMap map){
		List<String> list = new ArrayList<>();
		list.add("CommonException:error1");
		list.add("CommonException:error2");
		list.add("CommonException:error3");
		throw new CommonException(list);
	}

}
