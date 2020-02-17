package com.mallsystem.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with Eclipse
 * @author heroC
 * @since JDK1.8
 * @version 1.0
 * Description: 该类的作用就是用于登录检查，继承了HandlerInterceptor拦截器
 * 	未登录则不能访问除登录页和找回密码以外的其他网页
 */
public class LoginHandlerInterceptor implements HandlerInterceptor{
	// 目标方法执行前
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object user = request.getSession().getAttribute("loginUser"); // 获取在controller包里SysUserController.java里设置的HttpSession节点
		if(user == null) {
			// 没有登陆，返回登录页面
			request.getRequestDispatcher("/").forward(request, response);
			return false;
		}else {
			// 已登录，放行请求
			return true;
		}
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	

}

