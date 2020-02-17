package com.mallsystem.controller;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mallsystem.config.Classification;
import com.mallsystem.entities.GoodsClass;
import com.mallsystem.entities.Staff;
import com.mallsystem.entities.SysUser;
import com.mallsystem.mapper.ClassificationInfoMapper;
import com.mallsystem.mapper.StaffInfoMapper;
import com.mallsystem.mapper.UserInfoMapper;

/**
 * Created with Eclipse
 * @author heroC
 * @see com.mallsystem.mapper.ClassificationInfoMapper
 * @since JDK1.8
 * @version 1.0
 * Description: 该类欢迎页(登录页)发送的所有请求处理
 */
@Controller
public class SysUserController {
	@Autowired //自动注入
	UserInfoMapper userInfoMapper;
	@Autowired
	ClassificationInfoMapper classificationInfoMapper;
	@Autowired
	StaffInfoMapper staffInfoMapper;
	
	/**
	 * Description: 对登录页面的登录按钮发送的请求做逻辑处理。通过获取请求中的登录名，
	 * 	密码，对账户表中进行查询检查，是否存在该用户以及密码是否正确。如果正确，则检查
	 * 	用户的权限，权限设置之后，然后再登录进去。
	 * @param sysName
	 * @param sysPwd
	 * @param map
	 * @param session
	 * @return
	 */
	@PostMapping(value="/user/login")
	public String login(@RequestParam("sysName")String sysName, 
						@RequestParam("sysPwd")String sysPwd, 
						Map<String,Object> map,
						HttpSession session) {
		SysUser user = userInfoMapper.login(sysName);
		if( user!=null && sysName.equals(user.getSysName()) && sysPwd.equals(user.getSysPwd())) {
			session.setAttribute("loginUser", sysName); // HttpSession可以跟踪并储存用户信息，把值设置到属性中，session机制采用的是在服务器端保持 HTTP 状态信息的方案
			
			/*
			 * 	其中通过查询商品类别，将获取的商品类别通过Classification类进行筛选出
			 * 	大类别，在将重复的大类别剔除，并对大类别设定一个id，返回一个分类的列表，
			 * 	存储到session中，可更新侧边的分类菜单列表。
			 */
			Classification classification = new Classification();
			Collection<GoodsClass> collClass = classification.classificatonMethod(classificationInfoMapper.getGoodsClass());
			session.setAttribute("sClasses",collClass);
			
			if(!sysName.equals("admin")) {
				session.setAttribute("staff", 0);
				Staff staff = staffInfoMapper.getStaffByLoginName(sysName);
				if(staff!=null) {
					if(staff.getAddOp().equals("有")) {
						session.setAttribute("addOp", 1); // 有添加权限
					}else {
						session.setAttribute("addOp", 0); // 无添加权限
					}
					if(staff.getEditOp().equals("有")) {
						session.setAttribute("editOp", 1); // 有编辑权限
					}else {
						session.setAttribute("editOp", 0); // 无编辑权限
					}
					if(staff.getDelOp().equals("有")) {
						session.setAttribute("delOp", 1); // 有删除权限
					}else {
						session.setAttribute("delOp", 0); // 无删除权限
					}
				}else {
					return "index";
				}
			}else {
				session.setAttribute("staff", 1);
				session.setAttribute("addOp", 1);
				session.setAttribute("editOp", 1);
				session.setAttribute("delOp", 1);
			}
			
			return "redirect:/goods";  // 重定义响应路径，在MvcConfig中配置
		}else {
			map.put("msg", "请输入正确的用户名和密码"); // 向map中添加键和值，当密码输入错误时，在index.html中可以通过thymeleaf模板来判断msg是否有值来显示该键值
			return "index";
		}
	}
	
	/**
	 * Description: 顶部栏退出发送的请求，退出账户，退出系统。
	 * @param session
	 * @return 返回String类型，值为index，可自动将index.html页面的代码传递
	 * 	到浏览器进行解析
	 */
	@GetMapping(value="/signOut")
	public String signOut(HttpSession session) {
		session.setAttribute("loginUser", null);
		return "index";
	}
	
	/**
	 * Description: 对登录页右上角找回密码发送的请求做处理，跳转到找回密码的安全中心页面
	 * @return 返回String类型，值为findUser，可自动将findUser.html页面的代码传递
	 * 	到浏览器进行解析
	 */
	@GetMapping(value="/findUser")
	public String findUser() {
		return "findUser";
	}
	
	/**
	 * Description: 对找回密码安全中心页面的找回密码按钮发送的请求做处理。
	 * 	查找成功，则返回密码；查找失败，则返回密码找回失败
	 * @param sysName
	 * @param sysEncrypted
	 * @param sysAnswer
	 * @param map
	 * @return 返回String类型，值为findUser，可自动将findUser.html页面的代码传递
	 * 	到浏览器进行解析
	 */
	@PostMapping(value="/findPwd")
	public String findUserPwd(@RequestParam("sysName")String sysName,
			   				  @RequestParam("sysEncrypted")String sysEncrypted,
			   				  @RequestParam("sysAnswer")String sysAnswer,
			   				  Map<String,Object> map) {
		SysUser user = userInfoMapper.findCode(sysName);
		if(user!=null && sysName.equals(user.getSysName()) && sysEncrypted.equals(user.getSysEncrypted()) && sysAnswer.equals(user.getSysAnswer())) {
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
