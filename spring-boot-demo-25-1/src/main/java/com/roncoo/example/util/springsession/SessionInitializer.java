package com.roncoo.example.util.springsession;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

//初始化Session配置
public class SessionInitializer extends AbstractHttpSessionApplicationInitializer{
	public SessionInitializer() {
		super(SessionConfig.class);
	}
}