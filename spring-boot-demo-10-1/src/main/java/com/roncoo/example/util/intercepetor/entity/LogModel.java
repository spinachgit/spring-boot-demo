package com.roncoo.example.util.intercepetor.entity;

import java.io.Serializable;

public class LogModel implements Serializable{
	private static final long serialVersionUID = 5918078434145965713L;
	private String ipAddress;
	private String hostName;
	private String userId;
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
