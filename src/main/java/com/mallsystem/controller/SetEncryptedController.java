package com.mallsystem.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mallsystem.mapper.UserInfoMapper;

/**
 * Created with Eclipse
 * @author 刘志远
 * @see com.mallsystem.mapper.UserInfoMapper
 * @since JDK1.8
 * @version 1.0
 * Description: 该类处理重置密保模块的所有请求
 */
@Controller
public class SetEncryptedController {
	
	@Autowired
	UserInfoMapper userInfoMapper;
	
	/**
	 * Description: 处理重置密保发送的请求，处理表单，将其更新到数据库。
	 * 	如果数据库没有更新成功，那么密保重置失败，否则重置成功。
	 * @param sysUser
	 * @param pwd
	 * @param map
	 * @return 返回String类型，值为setAccountPwd，可自动将setAccountPwd.html页面的代码传递
	 * 	到浏览器进行解析
	 */
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
