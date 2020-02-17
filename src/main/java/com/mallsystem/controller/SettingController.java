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

/**
 * Created with Eclipse
 * @author heroC
 * @see com.mallsystem.mapper.UserInfoMapper
 * @since JDK1.8
 * @version 1.0
 * Description: 该类处理安全中心修改密码和密保时需要的密码验证页面发送的请求
 * 
 * 	info值为code，则在密码验证完成后，返回到重置密码页面
 * 	info值为enc，则在密码验证完成后，返回到重置密保页面
 * 	aciveSign值为pwd，则在重置密码页面时，依旧高亮 账户和密码
 * 	aciveSign值为enc，则在重置密码页面时，依旧高亮 密保问题设置
 * 
 */
@Controller
public class SettingController {
	
	@Autowired
	UserInfoMapper userInfoMapper;
	
	/**
	 * Description: 处理菜单栏 账户和密码 发送的请求，info 用于判定跳转哪个页面，
	 * 	activeSign 用于判断 账户和密码 是否为激活显示。
	 * @param map
	 * @return 返回String类型，值为setting，可自动将setting.html页面的代码传递
	 * 	到浏览器进行解析
	 */
	@GetMapping(value="/settingforCode")
	public String setPwd(Map<String,Object> map) {
		map.put("activeSign", "pwd");
		map.put("info", "code");
		return "setting";
	}
	
	/**
	 * Description: 处理菜单栏 密保问题设置 发送的请求,info 用于判定跳转哪个页面，
	 * 	activeSign 用于判断 密保问题设置 是否为高亮显示
	 * @param map
	 * @return 返回String类型，值为setting，可自动将setting.html页面的代码传递
	 * 	到浏览器进行解析
	 */
	@GetMapping(value="/settingforEnc")
	public String setEnc(Map<String,Object> map) {
		map.put("activeSign", "enc");
		map.put("info", "enc");
		return "setting";
	}
	
	/**
	 * Description: 通过密码验证页面确认按钮发的请求，以及传递过来的值，做对应的逻辑处理。
	 * 	如果info值为code，首先查询密码是否正确，不正确不跳转，正确跳转到密码重置页面。
	 * 	如果info值为enc，首先查询密码是否正确，不正确不跳转，正确跳转到密保重置页面。
	 * @param info
	 * @param sysName
	 * @param sysPwd
	 * @param map
	 * @return
	 */
	@PostMapping(value="/settingforCode/{info}/{loginUser}")
	public String verifyCode(@PathVariable("info")String info,
						  	 @PathVariable("loginUser")String sysName,
							 @RequestParam("sysPwd")String sysPwd,
							 Map<String,Object> map) {
		if(info.equals("code")) {
			SysUser sysUser = userInfoMapper.login(sysName);
			if(sysUser!=null && sysName.equals(sysUser.getSysName()) && sysPwd.equals(sysUser.getSysPwd())) {
				map.put("activeSign", "pwd");
				return "setAccountPwd";
			}else {
				return "redirect:/settingforCode";
			}
		}else {
			SysUser sysUser = userInfoMapper.login(sysName);
			if(sysUser!=null && sysName.equals(sysUser.getSysName()) && sysPwd.equals(sysUser.getSysPwd())) {
				map.put("activeSign", "enc");
				return "setEncrypted";
			}else {
				return "redirect:/settingforEnc";
			}
		}
	}
}
