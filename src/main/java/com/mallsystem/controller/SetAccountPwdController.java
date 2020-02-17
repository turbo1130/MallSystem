package com.mallsystem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mallsystem.entities.SysUser;
import com.mallsystem.mapper.UserInfoMapper;

/**
 * Created with Eclipse
 * @author heroC
 * @see com.mallsystem.mapper.UserInfoMapper
 * @since JDK1.8
 * @version 1.0
 * Description: 该类处理密码与账户重置密码模块的所有请求
 */
@Controller
public class SetAccountPwdController {
	
	@Autowired
	UserInfoMapper userInfoMapper;
	
	/**
	 * Description: 处理重置密码发送的请求，处理表单，将其更新到数据库。
	 * 	接收到前端发送来到两个密码数据，先对比两个密码数据是否相等，如果
	 * 	相等则重置密码操作，两个密码数据不相等，则返回设置密码不一致信息。
	 * @param sysUser
	 * @param pwd
	 * @param map
	 * @return 返回String类型，值为setAccountPwd，可自动将setAccountPwd.html页面的代码传递
	 * 	到浏览器进行解析
	 */
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
