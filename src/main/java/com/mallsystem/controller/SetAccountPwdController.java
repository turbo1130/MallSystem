package com.mallsystem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mallsystem.entities.SysUser;
import com.mallsystem.mapper.UserInfoMapper;

@Controller
public class SetAccountPwdController {
	
	@Autowired
	UserInfoMapper userInfoMapper;
	
	// 处理修改密码的表单，更新到数据库
	@PostMapping(value="/setCode")
	public String setNewCode(SysUser sysUser,
			   				 @RequestParam("Pwd")String pwd,
			   				 Map<String,Object> map) {
		if( pwd.equals( sysUser.getSysPwd() ) ) {
			userInfoMapper.updateCode(sysUser.getSysName(), pwd);
			map.put("chErr", "密码重置成功！");
			return "setAccountPwd";
		}else {
			map.put("chErr", "设置密码不一致，请重新设置！");
			return "setAccountPwd";
		}
	}
}
