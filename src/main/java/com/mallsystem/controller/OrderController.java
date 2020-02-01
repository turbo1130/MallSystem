package com.mallsystem.controller;

import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mallsystem.bean.Goods;
import com.mallsystem.bean.GoodsName;
import com.mallsystem.bean.Orders;
import com.mallsystem.mapper.GoodsInfoMapper;
import com.mallsystem.mapper.OrderInfoMapper;

@Controller
public class OrderController {
	
	private int orderPageNum = 1;
	private int oldCount; // 记录订单中已售出的商品数量
	private int newCount; // 记录订单中最新售出的商品数量
	private int forEditId;

	@Autowired
	OrderInfoMapper orderInfoMapper;
	@Autowired
	GoodsInfoMapper goodsInfoMapper;
	
	// 该请求查询所有订单信息，并且跳转至订单页面，每一页显示15条信息
	// pay和exp是判断提交表单后，页面显示对应的option，查看本类的searchStat()方法
	// pageSignalForOrder用于给定一个信号，来动态判断是否显示翻页按钮
	@GetMapping(value="/order")
	public String order(Model model, Map<String,Object> map) {
		Collection<Orders> od = orderInfoMapper.getOrdersByPage( (orderPageNum-1) * 15 );
		model.addAttribute("orders",od);
		map.put("pay", "1");
		map.put("exp", "1");
		map.put("pageSignalForOrder",1);
		return "order";
	}
	
	// 上一页按钮的请求处理，每一页显示15条信息
	@GetMapping(value="/ordersPre")
	public String orderPre() {
		if(orderPageNum > 1) {
			orderPageNum -= 1;
		}else {
			orderPageNum = 1;
		}
		return "redirect:/order";
	}
	
	// 下一页按钮的请求处理，每一页显示15条信息
	@GetMapping(value="/ordersAppend")
	public String orderAppend() {
		int orderRows = ( orderInfoMapper.getAllorders().size() ) / 15;
		if(( orderInfoMapper.getAllorders().size() ) % 15 != 0) {
			orderRows += 1;
		}
		if(orderRows > orderPageNum) {
			orderPageNum += 1;
		}else {
			orderPageNum = orderRows;
		}
		return "redirect:/order";
	}
	
	// 对添加订单按钮的请求做处理，获取所有商品名，并跳转至添加订单页面
	@GetMapping(value="/addOrder")
	public String addOrder(Model model) {
		Collection<GoodsName> gdName = goodsInfoMapper.getGoodsName();
		model.addAttribute("gdAllName", gdName);
		return "orderAdd";
	}
	
	// 对添加订单的表单请求做处理，对数据类型不同的变量进行类型转换处理
	@Transactional // 事务处理，必须所有数据在数据库操作完成之后才生效，否则数据不发生变化
	@PostMapping(value="/addOrder")
	public String orderAdd(Orders order,@RequestParam("ExpNum")String ExpNum) {
		if(ExpNum == "") {
			ExpNum = "0";
		}
		long odExpNum = Long.parseLong(ExpNum);
		order.setOdExpNum(odExpNum);
		Calendar cal = Calendar.getInstance();
		cal.setTime(order.getOdTime());
		cal.add(Calendar.DATE, 1);
		order.setOdTime(cal.getTime());
		// 对提交表单存储为String类型的修改的售出商品数量 提取出数字，并转换为整型赋值给newCount进行操作(正则表达式)
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(order.getOdCount());
		newCount = Integer.parseInt(matcher.replaceAll(""));
		// 对库存数据进行处理
		Goods gd = goodsInfoMapper.getGoodByName(order.getOdGood());
		newCount = gd.getGdCount() - newCount;
		if(newCount >= 0) {
			goodsInfoMapper.updateGoodForCount(gd.getGdID(), newCount);
			orderInfoMapper.addOrder(order);
			return "redirect:/order";
		}else {
			return "orderAdd";
		}
	}
	
	// 对支付状态和发货状态的查询进行处理,pay和exp是判断提交表单后，页面显示对应的option
	@GetMapping(value="/order/searchStat")
	public String searchStat(@RequestParam("payStat")String payStat,
							 @RequestParam("expStat")String expStat,
							 Model model,
							 Map<String,Object> map) {
		Collection<Orders> od = orderInfoMapper.getOrderStat(payStat, expStat);
		model.addAttribute("orders",od);
		if(payStat.equals("已付款")) {
			map.put("pay", "2");
		}else{
			map.put("pay", "3");
		}
		if(expStat.equals("已发货")) {
			map.put("exp", "2");
		}else {
			map.put("exp", "3");
		}
		map.put("pageSignalForOrder",null);
		return "order";
	}
	
	// 处理编辑按钮的请求，并通过id查询到订单信息，显示在表单中
	@GetMapping(value="/order/{id}")
	public String eidtOrder(@PathVariable("id")int id, Model model) {
		forEditId = id;
		Orders od = orderInfoMapper.getOrderById(id);
		model.addAttribute("order", od);
		Collection<GoodsName> gdName = goodsInfoMapper.getGoodsName();
		model.addAttribute("gdAllName", gdName);
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(od.getOdCount());
		oldCount = Integer.parseInt(matcher.replaceAll(""));
		return "orderEdit";
	}
	
	// 对编辑完成的表单的请求做处理
	@Transactional
	@PostMapping(value="/editOrder")
	public String orderEdit(Orders order,@RequestParam("ExpNum")String ExpNum) {
		// 如果前端没有填写快递单号，传过来的是''，orders类的odExpNum的类型是long，因此要进行转换
		if(ExpNum == "") {
			ExpNum = "0";
		}
		long odExpNum = Long.parseLong(ExpNum);
		order.setOdExpNum(odExpNum);
		// 由于提交的日期后台在进行日期转换时会减一天，因此这里的操作时将日期加一天，转到正常日期
		Calendar cal = Calendar.getInstance();
		cal.setTime(order.getOdTime());
		cal.add(Calendar.DATE, 1);
		order.setOdTime(cal.getTime());
		// 对提交表单存储为String类型的修改的售出商品数量 提取出数字，并转换为整型赋值给newCount进行操作(正则表达式)
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(order.getOdCount());
		newCount = Integer.parseInt(matcher.replaceAll(""));
		// 对库存数据进行处理
		if(newCount > oldCount) {
			newCount = newCount - oldCount;
			Goods gd = goodsInfoMapper.getGoodByName(order.getOdGood());
			newCount = gd.getGdCount() - newCount;  // 减去库存里的数量
			if(newCount >= 0) {
				goodsInfoMapper.updateGoodForCount(gd.getGdID(), newCount);
			}else {
				return "redirect:/order/" + Integer.toString(forEditId);
			}
		}else if(newCount < oldCount) {
			newCount = oldCount - newCount;
			Goods gd = goodsInfoMapper.getGoodByName(order.getOdGood());
			newCount = gd.getGdCount() + newCount; // 加上库存的数量
			goodsInfoMapper.updateGoodForCount(gd.getGdID(), newCount);
		}
		orderInfoMapper.updateOrder(order);
		return "redirect:/order";
	}
	
	// 删除请求处理
	@Transactional
	@GetMapping(value="/delOrder/{id}")
	public String delOrder(@PathVariable("id")int id) {
		Orders order = orderInfoMapper.getOrderById(id);
		// 对提交表单存储为String类型的修改的售出商品数量 提取出数字，并转换为整型赋值给newCount进行操作(正则表达式)
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(order.getOdCount());
		newCount = Integer.parseInt(matcher.replaceAll(""));
		// 对库存数据进行处理
		Goods gd = goodsInfoMapper.getGoodByName(order.getOdGood());
		newCount = gd.getGdCount() + newCount;
		goodsInfoMapper.updateGoodForCount(gd.getGdID(), newCount);
		orderInfoMapper.delOrder(id);
		return "redirect:/order";
	}
}
