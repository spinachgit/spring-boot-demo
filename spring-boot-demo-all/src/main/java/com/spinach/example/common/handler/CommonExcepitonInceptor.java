package com.spinach.example.common.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.spinach.example.common.utils.StringUtil;

/**
 * 通用异常封装
 * @author:whh
 * @date:2017年12月25日下午5:21:04
 */
@ControllerAdvice
public class CommonExcepitonInceptor {

	private static final Logger logger = LoggerFactory.getLogger(CommonExcepitonInceptor.class);

	/**
	 * 统一异常处理
	 * 
	 * @param CommonException
	 * @return
	 */
	@ExceptionHandler({ CommonException.class })
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processException(CommonException exception) {
		logger.info("自定义异常处理-CommonException");
		ModelAndView m = new ModelAndView();
		if (StringUtil.isNotEmpty(exception.getMessage())) {
			List<String> list = new ArrayList<>();
			list.add(exception.getMessage());
			m.addObject("commonException", list);
		} else {
			m.addObject("commonException", exception.getMessageList());
		}
		m.setViewName("error/500");
		return m;
	}

	/**
	 * 统一异常处理
	 * 
	 * @param RuntimeException
	 * @return
	 */
	@ExceptionHandler({ RuntimeException.class })
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processException(RuntimeException exception) {
		logger.info("自定义异常处理-RuntimeException");
		ModelAndView m = new ModelAndView();
		List<String> list = new ArrayList<>();
		if(null != exception.getMessage()){
			list.add(exception.getMessage());
		}
		m.addObject("commonException", list);
		m.setViewName("error/500");
		return m;
	}

	/**
	 * 统一异常处理
	 * 
	 * @param Exception
	 * @return
	 */
	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processException(Exception exception) {
		logger.info("自定义异常处理-Exception");
		ModelAndView m = new ModelAndView();
		List<String> list = new ArrayList<>();
		if(null != exception.getMessage()){
			list.add(exception.getMessage());
		}
		m.addObject("commonException", list);
		m.setViewName("error/500");
		return m;
	}

}
