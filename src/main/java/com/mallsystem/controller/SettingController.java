package com.mallsystem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mallsystem.entities.SysUser;
import com.mallsystem.mapper.UserInfoMapper;

@Controller
public class SettingController {
	
	@Autowired
	UserInfoMapper userInfoMapper;
	
	// 处理 账户和密码 发送的请求
	// info 用于判定跳转哪个页面
	// activeSign 用于判断 账户和密码 是否为激活显示
	@GetMapping(value="/settingforCode")
	public String setPwd(Map<String,Object> map) {
		map.put("activeSign", "pwd");
		map.put("info", "code");
		return "setting";
	}
	
	// 处理 密保问题设置 发送的请求
	// activeSign 用于判断 密保问题设置 是否为激活显示
	@GetMapping(value="/settingforEnc")
	public String setEnc(Map<String,Object> map) {
		map.put("activeSign", "enc");
		map.put("info", "enc");
		return "setting";
	}
	
	// 验证提交的密码是否正确
	@PostMapping(value="/settingforCode/{info}/{loginUser}")
	public String verifyCode(@PathVariable("info")String info,
						  	 @PathVariable("loginUser")String sysName,
							 @RequestParam("sysPwd")String sysPwd,
							 Map<String,Object> map) {
		if(info.equals("code")) {
			SysUser sysUser = userInfoMapper.login();
			if(sysName.equals(sysUser.getSysName()) && sysPwd.equals(sysUser.getSysPwd())) {
				map.put("activeSign", "pwd");
				return "setAccountPwd";
			}else {
				return "redirect:/settingforCode";
			}
		}else {
			SysUser sysUser = userInfoMapper.login();
			if(sysName.equals(sysUser.getSysName()) && sysPwd.equals(sysUser.getSysPwd())) {
				map.put("activeSign", "enc");
				return "setEncrypted";
			}else {
				return "redirect:/settingforEnc";
			}
		}
	}
}
