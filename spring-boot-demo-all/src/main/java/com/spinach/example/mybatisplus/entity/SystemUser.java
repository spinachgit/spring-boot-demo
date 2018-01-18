package com.spinach.example.mybatisplus.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;

/**
 * <p>
 * 
 * </p>
 *
 * @author spinach123
 * @since 2018-01-16
 */
public class SystemUser extends Model<SystemUser> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String id;
	/**
	 * 登陆名
	 */
	private String loginName;
	/**
	 * 密码
	 */
	private String loginPwd;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 邮件
	 */
	private String email;
	/**
	 * 工号
	 */
	private String remark;
	/**
	 * 工号
	 */
	private String jobNumber;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 创建时间 
	 */
	private Date createTime;
	/**
	 * 更新时间 
	 */
	private Date updateTime;

	@Override
	public String toString() {
		return "SystemUser{" + ", id=" + id + ", loginName=" + loginName + ", loginPwd=" + loginPwd + ", userName=" + userName + ", mobile=" + mobile + ", email=" + email + ", remark=" + remark
				+ ", jobNumber=" + jobNumber + ", status=" + status + ", createTime=" + createTime + ", updateTime=" + updateTime + "}";
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
