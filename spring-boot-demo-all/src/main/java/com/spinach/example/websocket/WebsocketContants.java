package com.spinach.example.websocket;

public class WebsocketContants {
	public static final String BASE_WS_PATH = "/ws/message/";
	public static final String BASE_SOCKJS_PATH = "/wss/message/";
	/**
	 * 消息1
	 */
	public static final String MESSAGE_SYS_URL = BASE_WS_PATH+"system";
	public static final String MESSAGE_SYS_SOCKJS_URL = BASE_SOCKJS_PATH+"system";
	/**
	 * 消息2
	 */
	public static final String MESSAGE_AIR_URL = BASE_WS_PATH+"air";
	public static final String MESSAGE_AIR_SOCKJS_URL = BASE_SOCKJS_PATH+"air";;
}
