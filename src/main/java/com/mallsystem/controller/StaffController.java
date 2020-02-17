package com.mallsystem.controller;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mallsystem.entities.Staff;
import com.mallsystem.mapper.StaffInfoMapper;
import com.mallsystem.mapper.UserInfoMapper;

/**
 * Created with Eclipse
 * @author heroC
 * @see com.mallsystem.mapper.StaffInfoMapper
 * @see com.mallsystem.mapper.UserInfoMapper
 * @since JDK1.8
 * @version 1.0
 * Description: 该类处理员工管理模块发送的所有请求
 * 
 */
@Controller
public class StaffController {
	
	@Autowired
	StaffInfoMapper staffInfoMapper;
	@Autowired
	UserInfoMapper userInfoMapper;
	
	/**
	 * Description: 对菜单栏 员工管理 发送的请求做逻辑处理。
	 * 	查询数据库中员工所有信息，并存入model中，前端对model
	 * 	节点中的数据进行遍历显示。
	 * @param model
	 * @param map
	 * @return 返回String类型，值为staff，可自动将staff.html页面的代码传递
	 * 	到浏览器进行解析
	 */
	@GetMapping(value="/staff")
	public String staff(Model model, Map<String, Object> map) {
		Collection<Staff> collAllStaff = staffInfoMapper.getAllStaff();
		model.addAttribute("staffs", collAllStaff);
		
		Staff staff = new Staff(20200001,"刘志远","admin","有","有","有");
		model.addAttribute("sta", staff); // 这一步是为了初始化前端编辑按钮发起的弹窗信息
		map.put("showPop", 0); // 该语句判断弹窗是否被显示，0则表示不被显示
		return "staff";
	}

	/**
	 * Description: 通过编辑按钮发送的请求，在发送请求的同时也会有窗口弹出，
	 * 	在窗口会显示需要被修改的员工信息。逻辑修改成功之后窗口会消失。
	 * @param id
	 * @param model
	 * @param map
	 * @return 返回String类型，值为staff，可自动将staff.html页面的代码传递
	 * 	到浏览器进行解析
	 */
	@GetMapping(value="/s/{id}")
	public String staffEdit(@PathVariable("id")int id, Model model, Map<String, Object> map) {
		// 确保在窗口弹出之后，窗口下的背景中的所有员工数据还存在
		Collection<Staff> collAllStaff = staffInfoMapper.getAllStaff();
		model.addAttribute("staffs", collAllStaff);
		
		Staff collStaff = staffInfoMapper.getStaffByID(id);
		model.addAttribute("sta", collStaff); // 将查询到需要编辑的员工信息，前端对其进行遍历在对应位置显示
		map.put("showPop", 1); // 该语句判断弹窗是否被显示，1则表示被显示
		return "staff";
	}
	
	/**
	 * Description: 在窗口中通过提交按钮发送的请求，这时对员工信息进行更新处理。
	 * @param staff
	 * @return 返回重定向
	 */
	@PostMapping(value="/editStaff")
	public String editStaff(Staff staff) {
		staffInfoMapper.updateStaff(staff);
		return "redirect:/staff";
	}
	
	/**
	 * Description: 通过添加员工按钮发送的请求，跳转至添加员工的信息的页面。
	 * @param map
	 * @return 返回String类型，值为staffAdd，可自动将staffAdd.html页面的代码传递
	 * 	到浏览器进行解析
	 */
	@GetMapping(value="/addStaff")
	public String addStaff(Map<String, Object> map) {
		map.put("activeUri", "staff"); // 作用是前端接收到staff字符串可以判断高亮菜单栏的 员工管理
		return "staffAdd";
	}
	
	/**
	 * Description: 通过添加员工页面的提交按钮发送的请求，向账户表写入新数据，向员工表写入新数据
	 * @param staff
	 * @return 返回重定向
	 */
	@PostMapping(value="/addStaff")
	public String satffAdd(Staff staff) {
		staffInfoMapper.addStaff(staff);
		String sysName = staff.getLoginName();
		String sysAnswer = staff.getStaffName();
		userInfoMapper.addSysUser(sysName, sysAnswer);
		return "redirect:/staff";
	}
	
	/**
	 * Description: 通过删除员工按钮发送的请求，从员工表中删除，并且删除员工账户信息。
	 * @param id
	 * @return 重定向
	 */
	@PostMapping(value="/s/{id}")
	public String delStaff(@PathVariable("id")int id) {
		Staff staff = staffInfoMapper.getStaffByID(id);
		userInfoMapper.delUser(staff.getLoginName());
		staffInfoMapper.delStaff(id);
		return "redirect:/staff";
	}
}
