package com.spinach.example.datajpa.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TestUserLog implements Serializable{
	private static final long serialVersionUID = 7357320369220967502L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column
	private Date createTime;

	@Column
	private String userName;

	@Column
	private String userIp;
	/**
	 * 经过测试：jpa可以动态的增加字段，增加完成之后，再删除实体，JPA没有删除
	 */
	@Column
	private String addCol1;

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
		this.userName = userName;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getAddCol1() {
		return addCol1;
	}

	public void setAddCol1(String addCol1) {
		this.addCol1 = addCol1;
	}

	@Override
	public String toString() {
		return "UserLog [id=" + id + ", createTime=" + createTime + ", userName=" + userName + ", userIp=" + userIp + "]";
	}

}
