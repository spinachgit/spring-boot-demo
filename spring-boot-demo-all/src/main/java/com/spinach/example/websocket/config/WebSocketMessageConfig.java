package com.spinach.example.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * websocket: 配置 
 * 通过EnableWebSocketMessageBroker 开启使用STOMP协议来传输基于代理(message broker)的消息,
 * 此时浏览器支持使用@MessageMapping 就像支持@RequestMapping一样。
 * 
 * @author:whh
 * @date:2017年12月26日下午2:32:48
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
	
	/**
	 * endPoint 注册协议节点,并映射指定的URl
	 * @author:whh
	 * @date:2017年12月26日下午2:35:10
	 * @param registry
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 注册一个Stomp 协议的endpoint,并指定 SockJS协议
		registry.addEndpoint("/endpointWisely").withSockJS();

		// 注册一个名字为"endpointChat" 的endpoint,并指定 SockJS协议。 点对点-用
		registry.addEndpoint("/endpointChat").withSockJS();
	}
	
	/**
	 * 配置消息代理(message broker)
	 * @author:whh
	 * @date:2017年12月26日下午2:35:19
	 * @param registry
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 广播式应配置一个/topic 消息代理
		registry.enableSimpleBroker("/topic");
		// 点对点式增加一个/queue 消息代理
		registry.enableSimpleBroker("/queue", "/topic");

	}
}
