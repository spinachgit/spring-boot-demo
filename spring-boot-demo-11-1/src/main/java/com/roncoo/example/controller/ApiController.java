package com.roncoo.example.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://domain2.com:8080")
public class ApiController {

	//@CrossOrigin(origins = {"*"}, maxAge = 3600)
	@RequestMapping(value = "/get/{name}", method = {RequestMethod.POST,RequestMethod.GET})
	public HashMap<String, Object> get(@PathVariable String name) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", "hello world");
		map.put("name", name);
		return map;
	}
}
