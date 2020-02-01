package com.mallsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mallsystem.config.LoginHandlerInterceptor;

@Configuration
public class MvcConfig implements WebMvcConfigurer{
	// 配置SpringMVC访问页面，配置了之后遇到以下路径都会去templates目录下去找对应的网页
	// 也会在此类的webMvcConfigurer()方法中addViewControllers()方法中去找到重定向路径，去访问对应的网页
	
		@Bean //将组建自动注册到容器中
		public WebMvcConfigurer webMvcConfigurer() {
			WebMvcConfigurer adapter = new WebMvcConfigurer() {
				
				//url重定向
				@Override
				public void addViewControllers(ViewControllerRegistry registry) {
					registry.addViewController("/").setViewName("index");
					registry.addViewController("/index").setViewName("index");
					registry.addViewController("/goods").setViewName("goods");
				}

				// 注册拦截器
				@Override
				public void addInterceptors(InterceptorRegistry registry) {
					// 这里的/**是拦截任意路径下的请求
					// excludePathPatterns()是在未登录时需要忽略拦截的地址
					registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
							.excludePathPatterns("/","/index.html","/user/login","/static/**","/asserts/**","/webjars/**","/findUser","/findPwd");
				}
				
				
			};
			return adapter;
		}
}
