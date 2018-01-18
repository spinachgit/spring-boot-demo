package com.spinach.example.mybatisplus.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.spinach.example.mybatisplus.entity.enums.AgeEnum;
import com.spinach.example.mybatisplus.entity.enums.PhoneEnum;

/**
 * <p>
 * 
 * </p>
 *
 * @author spinach123
 * @since 2018-01-16
 */
public class User extends Model<User>{
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @TableId("test_id")
    private Long id;
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 名称
     */
    private String name;
    /**
     * 日期
     */
    private Date testDate;
    /**
     * 测试
     */
    private Long role;
    /**
     * 手机号码
     */
    private PhoneEnum phone;
    /**
     * 年龄
     */
    private AgeEnum age;
    /**
     * 这里故意演示注解可无
     */
    //@TableField("test_type")
    //@TableLogic
    private Integer testType;

    public User() {
    }

    public User(Long id, String name, AgeEnum age, Integer testType) {
        this.setId(id);
        this.name = name;
        this.age = age;
        this.testType = testType;
    }

    public User(String name, AgeEnum age, Integer testType) {
        this.name = name;
        this.age = age;
        this.testType = testType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AgeEnum getAge() {
        return age;
    }

    public void setAge(AgeEnum age) {
        this.age = age;
    }

    public Integer getTestType() {
        return testType;
    }

    public void setTestType(Integer testType) {
        this.testType = testType;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public PhoneEnum getPhone() {
        return phone;
    }

    public void setPhone(PhoneEnum phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
        ", testId=" + id +
        ", tenantId=" + tenantId +
        ", name=" + name +
        ", age=" + age +
        ", testType=" + testType +
        ", testDate=" + testDate +
        ", role=" + role +
        ", phone=" + phone +
        "}";
    }

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
