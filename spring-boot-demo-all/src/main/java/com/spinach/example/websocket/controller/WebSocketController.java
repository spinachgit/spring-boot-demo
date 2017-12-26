package com.spinach.example.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spinach.example.websocket.entity.Message;
import com.spinach.example.websocket.entity.Response;
import com.spinach.example.websocket.service.WebSocketService;

import java.security.Principal;

/**
 * WebSocketController，信息接收类
 * @author:whh
 * @date:2017年12月26日下午2:36:15
 */
@CrossOrigin
@Controller
public class WebSocketController {
	@Autowired
	private WebSocketService ws;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/ws")
	public String ws() {
		return "websocket/ws";
	}

	@RequestMapping(value = "/chat")
	public String chat() {
		return "websocket/chat";
	}

	// http://localhost:8080/ws
	@MessageMapping("/welcome") // 浏览器发送请求通过@messageMapping 映射/welcome 这个地址。
	@SendTo("/topic/getResponse") // 服务器端有消息时,会订阅@SendTo 中的路径的浏览器发送消息。
	public Response say(Message message) throws Exception {
		Thread.sleep(1000);
		return new Response("Welcome, " + message.getName() + "!");
	}

	// http://localhost:8080/Welcome1
	@RequestMapping("/Welcome1")
	@ResponseBody
	public String say2(Principal principal) throws Exception {
		ws.sendMessage(principal.getName());
		return "is ok";
	}
	
	/**
	 * 通过当前用户,然后查找消息,如果查找到未读消息,则发送给当前用户。
	 * @author:whh
	 * @date:2017年12月26日下午2:38:52
	 * @param principal
	 * @param message
	 */
	@MessageMapping("/chat")
	// 在springmvc 中可以直接获得principal,principal 中包含当前用户的信息
	public void handleChat(Principal principal, Message message) {
		/**
		 * 此处是一段硬编码。发送给对应的用户。 
		 */
		if (principal.getName().equals("test")) {
			// 通过convertAndSendToUser 向用户发送信息,
			// 第一个参数是接收消息的用户,第二个参数是浏览器订阅的地址,第三个参数是消息本身
			//messagingTemplate.convertAndSendToUser("test", "/queue/notifications", principal.getName() + "-send:" + message.getName());
			//messagingTemplate.convertAndSend("/user/test/queue/notifications",principal.getName() +"-send:" + message.getName());
		} else {
			messagingTemplate.convertAndSendToUser("admin", "/queue/notifications", principal.getName() + "-send:" + message.getName());
		}
	}
}
