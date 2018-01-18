package com.spinach.example.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/login").permitAll()// 根路径和/login路径不拦截
				.anyRequest().authenticated().and().formLogin().loginPage("/login") // 2登陆页面路径为/login
				.defaultSuccessUrl("/chat") // 3登陆成功转向chat页面
				.permitAll().and().logout().permitAll();
		http.csrf().disable();//在原本的配置文件下添加这行代码，禁用security的csrf 
	}

	// 4在内存中配置两个用户 admin 和 test ,密码和用户名一致,角色是 USER
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("USER").and().withUser("test").password("test").roles("USER");
	}

	// 5忽略静态资源的拦截
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**").antMatchers("/public/**").antMatchers("/META-INF/**");
	}

}