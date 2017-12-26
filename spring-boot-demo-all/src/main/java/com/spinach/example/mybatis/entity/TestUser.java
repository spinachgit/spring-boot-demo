package com.spinach.example.mybatis.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Entity实体类:
 * 
 * @author spinach
 */
public class TestUser {

	private Integer id;

	private Date createTime;

	private String userName;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}
}