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


@Controller
public class LeaveAMsgController {
	
	@Autowired
	LeaveAMsgInfoMapper leaveAMsgInfoMapper;
	
	// 通过处理客户留言按钮发送的请求，查询客户留言所有信息显示到页面
	@GetMapping(value="/msges")
	public String msges(Model model) {
		Collection<LeaveAMsg> msg = leaveAMsgInfoMapper.getAllMsg();
		model.addAttribute("forMsges", msg);
		return "leaveAMsg";
	}
	
	// 对删除请求进行处理
	@PostMapping(value="/delMsg/{customer}")
	public String delMsg(@PathVariable("customer")String customer) {
		leaveAMsgInfoMapper.delMsg(customer);
		return "redirect:/msges";
	}
}
