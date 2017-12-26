package com.spinach.example.redis;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spinach.example.common.utils.GsonUtils;

@RestController
@RequestMapping("/redis")
public class RedisController {
	private static final Logger logger =  LoggerFactory.getLogger(RedisController.class);
	@Autowired
	private RedisTemplateUtils redisUitl;
	
	@RequestMapping(value = "/add")
	public String add(ModelMap map, HttpSession httpSession,Integer id) {
		map.put("title", "jedis");
		map.put("sessionId", httpSession.getId());
		map.put("content", "content_"+id);
		JedisSingleUtils.setObjectByKey("Jedis_map_"+id, map,true);
		return "index";
	}
	
	@RequestMapping(value = "/select")
	public Object map(HttpSession httpSession,Integer id) {
		Object object = JedisSingleUtils.getObjectByKey("Jedis_map_"+id);
		return object;
	}
	
	@RequestMapping(value = "/add2")
	public String add2(ModelMap map, HttpSession httpSession,Integer id) {
		map.put("title", "redisTemplate");
		map.put("sessionId", httpSession.getId());
		map.put("content", "content_"+id);
		redisUitl.set("Template_map_"+id,GsonUtils.toJson(map));
		return "index";
	}
	
	@RequestMapping(value = "/select2")
	public Object map2(HttpSession httpSession,Integer id) {
		String object = redisUitl.get("Template_map_"+id);;
		return object;
	}
	
	

}
