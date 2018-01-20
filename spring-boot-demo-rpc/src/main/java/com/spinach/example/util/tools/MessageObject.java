package com.spinach.example.util.tools;

/**
 * 后台消息返回前台
 * 
 * @author yule
 */
public class MessageObject {

	private boolean success = true;// 成功，

	private boolean valid = true;// 验证

	private String msg;// 消息

	private String adjustId;// 方案名称

	private String adjustRes;// 调整原因

	private String subject;// 主题

	private String legId;// 航班legId

	private String isNew;// 是否是新增航班 1：是 0：否

	private String alertKey;// redies上保存的key

	public String getAdjustId() {
		return adjustId;
	}

	public void setAdjustId(String adjustId) {
		this.adjustId = adjustId;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getAdjustRes() {
		return adjustRes;
	}

	public void setAdjustRes(String adjustRes) {
		this.adjustRes = adjustRes;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getLegId() {
		return legId;
	}

	public void setLegId(String legId) {
		this.legId = legId;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getAlertKey() {
		return alertKey;
	}

	public void setAlertKey(String alertKey) {
		this.alertKey = alertKey;
	}

	@Override
	public String toString() {
		return "MessageObject [success=" + success + ", valid=" + valid + ", msg=" + msg + ", adjustId=" + adjustId + ", adjustRes=" + adjustRes + ", subject="
				+ subject + ", legId=" + legId + ", isNew=" + isNew + ", alertKey=" + alertKey + "]";
	}

}
