package com.spinach.example.common.handler;

import java.util.List;

/**
 * 通用消息异常定义
 */
public class CommonException extends RuntimeException {
	/** serialVersionUID */
	private static final long serialVersionUID = 1;

	/** 输入错误list */
	private List<String> messageList;
	private String msg;

	/**
	 * 构造函数
	 * @param msg
	 */
	public CommonException(String msg) {
		this.msg = msg;
	}
	
	public CommonException(List<String> msgList) {
		messageList = msgList;
	}

	/**
	 * 取得messageList
	 * 
	 * @return messageList
	 */
	public List<String> getMessageList() {
		return messageList;
	}

	@Override
	public String getMessage() {
		return msg;
	}
	

}