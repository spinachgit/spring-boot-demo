package com.spinach.example.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Spring工具。
 * </p>
 * 
 * @version 1.0
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtils.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getBean(String beanName) throws BeansException {
		return applicationContext.getBean(beanName);
	}

}
