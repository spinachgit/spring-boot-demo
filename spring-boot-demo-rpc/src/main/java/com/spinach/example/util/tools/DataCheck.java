package com.spinach.example.util.tools;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

public class DataCheck {
	@NotBlank(message = "id不能为空!")
	private String id;
	
	@NotBlank(message = "Name不能为空!")
	@Size(max = 11,min= 1,message = "长度为1至11位!")
	@Length(max = 11,min= 1,message = "长度为1至11位!")
	private String name;
	
	@Email(message = "邮件格式不正确!")
	private String mail;
	
	@NumberFormat(pattern="####.##")
	@DecimalMin(value = "10",message="该值为数字且值必须大于等于10")
	@Max(value=100,message="最大值为100")
	private BigDecimal sortVal;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private String fltDate;
	

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public BigDecimal getSortVal() {
		return sortVal;
	}
	public void setSortVal(BigDecimal sortVal) {
		this.sortVal = sortVal;
	}
	public String getFltDate() {
		return fltDate;
	}
	public void setFltDate(String fltDate) {
		this.fltDate = fltDate;
	}
	/**
	 * 正则表达式:判断是否数字
	 * 
	 * @param str
	 * @return
	 */
	@Valid
	public static boolean isNumberic(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();

	}
}
