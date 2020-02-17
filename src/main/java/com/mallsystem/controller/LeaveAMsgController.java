package com.mallsystem.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mallsystem.entities.LeaveAMsg;
import com.mallsystem.mapper.LeaveAMsgInfoMapper;

/**
 * Created with Eclipse
 * @author 刘志远
 * @see com.mallsystem.mapper.LeaveAMsgInfoMapper
 * @since JDK1.8
 * @version 1.0
 * Description: 该类处理客户反馈模块的所有请求
 */
@Controller
public class LeaveAMsgController {
	
	@Autowired
	LeaveAMsgInfoMapper leaveAMsgInfoMapper;
	
	/**
	 * Description: 左侧菜单栏客户反馈发动的请求，获取客户反馈的所有信息显示到页面
	 * @param model
	 * @return 返回String类型，值为leaveAMsg，可自动将leaveAMsg.html页面的代码传递
	 * 	到浏览器进行解析
	 */
	@GetMapping(value="/msges")
	public String msges(Model model) {
		Collection<LeaveAMsg> msg = leaveAMsgInfoMapper.getAllMsg(); // 通过调用方法，获取全部客户反馈的信息
		model.addAttribute("forMsges", msg);
		return "leaveAMsg";
	}
	
	/**
	 * Description: 对删除按钮发送的请求进行处理
	 * @param customer
	 * @return 返回重定向
	 */
	@PostMapping(value="/delMsg/{customer}")
	public String delMsg(@PathVariable("customer")String customer) {
		leaveAMsgInfoMapper.delMsg(customer);
		return "redirect:/msges"; // 重定向 执行本类的第一个方法
	}
}
