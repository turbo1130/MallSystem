package com.mallsystem.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mallsystem.entities.SysUser;
import com.mallsystem.mapper.UserInfoMapper;

@Controller
public class SysUserController {
	@Autowired //自动注入
	UserInfoMapper userInfoMapper;
	
	// 响应index.html的表单post请求
	@PostMapping(value="/user/login")
	public String login(@RequestParam("sysName")String sysName, 
						@RequestParam("sysPwd")String sysPwd, 
						Map<String,Object> map,
						HttpSession session) {
		SysUser user = userInfoMapper.login();
		if(sysName.equals(user.getSysName()) && sysPwd.equals(user.getSysPwd())) {
			session.setAttribute("loginUser", sysName); // HttpSession可以跟踪并储存用户信息，把值设置到属性中，session机制采用的是在服务器端保持 HTTP 状态信息的方案
			return "redirect:/goods";  // 重定义响应路径，在MvcConfig中配置
		}else {
			map.put("msg", "请输入正确的用户名和密码"); // 向map中添加键和值，当密码输入错误时，在index.html中可以通过thymeleaf模板来判断msg是否有值来显示该键值
			return "index";
		}
	}
	
	// 响应退出登录请求
	@GetMapping(value="/signOut")
	public String signOut(HttpSession session) {
		session.setAttribute("loginUser", null);
		return "index";
	}
	
	// 响应index.html的找回密码,跳转到找回密码的安全中心页面
	@GetMapping(value="/findUser")
	public String findUser() {
		return "findUser";
	}
	
	// 响应findUser.html的找回密码表单
	@PostMapping(value="/findPwd")
	public String findUserPwd(@RequestParam("sysName")String sysName,
			   				  @RequestParam("sysEncrypted")String sysEncrypted,
			   				  @RequestParam("sysAnswer")String sysAnswer,
			   				  Map<String,Object> map) {
		SysUser user = userInfoMapper.login();
		user.toString();
		if(sysName.equals(user.getSysName()) && sysEncrypted.equals(user.getSysEncrypted()) && sysAnswer.equals(user.getSysAnswer())) {
			map.put("findPwd", "您的密码是："+user.getSysPwd()+"");
			map.put("findResultMsg", null);
			return "findUser";
		}else {
			map.put("findPwd",null);
			map.put("findResultMsg", "密码找回失败！");
			return "findUser";
		}
	}
}
