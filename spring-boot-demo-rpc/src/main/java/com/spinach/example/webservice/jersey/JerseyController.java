package com.spinach.example.webservice.jersey;
import java.util.HashMap;  
import java.util.Map;  
  
import javax.ws.rs.GET;  
import javax.ws.rs.Path;  
import javax.ws.rs.Produces;  
import javax.ws.rs.core.MediaType;  
  
import org.springframework.stereotype.Component;  

/**
 * {"code":1,"codeMsg":"success"}
 * @author:spinach
 * @date:2018年2月1日下午7:08:56
 */
@Path("/")  
@Component  
public class JerseyController {
	/**
	 * 返回参数：{"code":1,"codeMsg":"success"}
	 */
    @GET  
    @Produces(MediaType.APPLICATION_JSON)  
    @Path("/hello")  
    public Map<String,Object> hello() {  
       Map<String,Object> map = new HashMap<String,Object>();  
       map.put("code",1);  
       map.put("codeMsg", "success");  
       return map;  
    }  
}  