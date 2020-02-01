package com.mallsystem.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mallsystem.mapper.UserInfoMapper;

@Controller
public class SetEncryptedController {
	
	@Autowired
	UserInfoMapper userInfoMapper;
	
	// 处理提交的密保重置表单
	@PostMapping(value="/setEnc")
	public String setEnc(@RequestParam("sysEncrypted")String sysEnc,
						 @RequestParam("sysAnswer")String sysAnswer,
				 		 Map<String,Object> map,
				 		 HttpSession session) {
		String sysName = (String) session.getAttribute("loginUser"); // 在SysUserController类中设置了loginUser的session节点，可获取当前登录的用户名
		int result = userInfoMapper.updateEnc(sysEnc, sysAnswer, sysName);
		if( result != 0 ) {
			map.put("chErr", "密保重置成功！");
			return "setEncrypted";
		}else {
			map.put("chErr", "密保重置失败！");
			return "setEncrypted";
		}
	}
}
