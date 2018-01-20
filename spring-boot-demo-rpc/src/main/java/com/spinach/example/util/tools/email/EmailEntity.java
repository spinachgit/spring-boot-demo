package com.spinach.example.util.tools.email;

public class EmailEntity {
	/**  邮件发送人 **/
	private String mailFrom;
	/**  邮件接收人 **/
	private String[] mailTo;
	/**  邮件抄送人 **/
	private String[] mailCopy;
	/**  邮件主题 **/
	private String mailSubject;
	/**  邮件内容 **/
	private String mailBody;
	
	public final String getMailFrom() {
		return mailFrom;
	}
	public final void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	public final String[] getMailTo() {
		return mailTo;
	}
	public final void setMailTo(String[] mailTo) {
		this.mailTo = mailTo;
	}
	public final String[] getMailCopy() {
		return mailCopy;
	}
	public final void setMailCopy(String[] mailCopy) {
		this.mailCopy = mailCopy;
	}
	public final String getMailSubject() {
		return mailSubject;
	}
	public final void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}
	public final String getMailBody() {
		return mailBody;
	}
	public final void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}
	
}
