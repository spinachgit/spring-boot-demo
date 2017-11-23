package com.roncoo.example.util.intercepetor;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.async.AsyncWebRequest;
import org.springframework.web.context.request.async.WebAsyncManager;
import org.springframework.web.context.request.async.WebAsyncUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.roncoo.example.util.ReflectionUtils;
import com.roncoo.example.util.intercepetor.entity.LogHolder;
import com.roncoo.example.util.intercepetor.entity.LogModel;
import com.roncoo.example.util.intercepetor.entity.ResponseHolder;

/**
 * 自定义拦截器2
 */
public class LogInterceptor implements HandlerInterceptor {
	@Autowired
	private ResponseHolder responseHolder;
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		LogModel log = new LogModel();
		log.setHostName(request.getRemoteHost());
		log.setIpAddress(request.getRemoteAddr());
		LogHolder.setLog(log);
		return true;// 只有返回true才会继续向下执行，返回false取消当前请求
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		System.out.println(">>>LogInterceptor>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			ResponseBody responseBody = method.getMethodAnnotation(ResponseBody.class);
			if (responseBody != null) {
				Object bean = method.getBean();
				Class<?> beanType = method.getBeanType();
				String beanName = beanType.getName();
				Field temp = ReflectionUtils.getDeclaredField(method.getReturnType(), "reflectionData");
				// InvocableHandlerMethod.doInvoke(beanName);
				//org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(NativeWebRequest, ModelAndViewContainer, Object...)
				
				MethodParameter returnType = method.getReturnType();
				returnType.getMember();
				System.out.println(method.getBean());
				System.out.println(method.getBeanType());
				System.out.println(method.getReturnType());
				
				
				ServletWebRequest webRequest = new ServletWebRequest(request, response);
				ModelAndViewContainer mavContainer = new ModelAndViewContainer();
				mavContainer.addAllAttributes(RequestContextUtils.getInputFlashMap(request));
				//modelFactory.initModel(webRequest, mavContainer, invocableMethod);
				//mavContainer.setIgnoreDefaultModelOnRedirect(this.ignoreDefaultModelOnRedirect);

				//AsyncWebRequest asyncWebRequest = WebAsyncUtils.createAsyncWebRequest(request, response);
				//asyncWebRequest.setTimeout(this.asyncRequestTimeout);

				//WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
				//asyncManager.setTaskExecutor(this.taskExecutor);
				//asyncManager.setAsyncWebRequest(asyncWebRequest);
				//asyncManager.registerCallableInterceptors(this.callableInterceptors);
				//asyncManager.registerDeferredResultInterceptors(this.deferredResultInterceptors);

				/*if (asyncManager.hasConcurrentResult()) {
					Object result = asyncManager.getConcurrentResult();
					mavContainer = (ModelAndViewContainer) asyncManager.getConcurrentResultContext()[0];
					asyncManager.clearConcurrentResult();
					if (logger.isDebugEnabled()) {
						logger.debug("Found concurrent result value [" + result + "]");
					}
					invocableMethod = invocableMethod.wrapConcurrentResult(result);
				}

				invocableMethod.invokeAndHandle(webRequest, mavContainer);*/
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		System.out.println(">>>LogInterceptor>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Object bean = handlerMethod.getBean();
			Class<?> beanType = handlerMethod.getBeanType();
			MethodParameter returnType = handlerMethod.getReturnType();
			System.out.println(handlerMethod.getBean());
			System.out.println(handlerMethod.getBeanType());
			System.out.println(handlerMethod.getReturnType());
		}
	}

}