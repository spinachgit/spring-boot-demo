package com.spinach.example.util.tools.email;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.spinach.example.util.tools.DateUtil;
import com.spinach.example.util.tools.StrUtil;

public class EmailUtil {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(EmailUtil.class);
	private MailSender mailSender;
	private MimeMessage mimeMessage;
	private SimpleMailMessage smm;
	private MimeMessageHelper helper;
	/** 邮件模板  **/
	//private VelocityEngine velocityEngine;
	
	public void sendEmail(RuntimeException e,String mailSubject){
		EmailEntity mailEntity = new EmailEntity();
		String username = "userName";
		String password = "password";
		String rescieveUser = "123@qq.com";
		String host = "http://www.test.com:8080";
		
		if (StrUtil.isNull(rescieveUser) || StrUtil.isNull(username)) {
			logger.info("[发送告警邮件失败,没有接收人] " + DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss"));
			return;
		}
		
		String[] mu = rescieveUser.split(";");
		mailEntity.setMailFrom(username);	//发送人
		mailEntity.setMailTo(mu);			//接收人
		mailEntity.setMailCopy(null);		//抄送人
		mailEntity.setMailSubject(mailSubject);//主题
		RuntimeException body = new RuntimeException(e);
		mailEntity.setMailBody(body.toString());//邮件内容
		try {
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			mailSender.setHost(host);
			mailSender.setUsername(username);
			mailSender.setPassword(password);
			Properties javaMailProperties = new Properties();
			javaMailProperties.put("mail.smtp.auth", "true");
			javaMailProperties.put("mail.smtp.debug", "true");
			mailSender.setJavaMailProperties(javaMailProperties);
			
			//mimeMessage.setFrom(new InternetAddress(mailEntity.getMailFrom()));
			smm.setFrom(mailEntity.getMailFrom());
			smm.setFrom(mailSender.getUsername());
	        smm.setTo(mailEntity.getMailTo());
	        smm.setCc(mailEntity.getMailCopy());
	        smm.setSubject(mailEntity.getMailSubject());
	        smm.setText(mailEntity.getMailBody());
	        mailSender.send(smm);
			
		} catch (RuntimeException ec) {
			logger.error("发送告警邮件出错 .");
		}
	}
	
	
}
