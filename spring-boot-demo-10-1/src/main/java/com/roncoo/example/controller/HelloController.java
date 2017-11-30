package com.roncoo.example.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello测试类
 * @author Administrator
 *
 */
@RestController   // 等价于@Controller+@ResponseBody
public class HelloController {
	  @RequestMapping("/hello")  
	  public String hello(){  
	    return "Hello world test!";  
	  }  
	  
	  @RequestMapping("/hello2")  
	  public String hello2(String name){  
	    return "Hello "+name+" !";  
	  }  
}