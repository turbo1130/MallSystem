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

import com.mallsystem.entities.Goods;
import com.mallsystem.entities.GoodsName;
import com.mallsystem.entities.Orders;
import com.mallsystem.mapper.GoodsInfoMapper;
import com.mallsystem.mapper.OrderInfoMapper;

/**
 * Created with Eclipse
 * @author heroC
 * @see com.mallsystem.mapper.OrderInfoMapper
 * @see com.mallsystem.mapper.GoodsInfoMapper
 * @since JDK1.8
 * @version 1.0
 * Description: 该类处理客户订单模块的所有请求
 */
@Controller
public class OrderController {
	
	private int orderPageNum = 1; // 为了确定商品管理展示信息的当前是第几页
	private int oldCount; // 记录订单中已售出的商品数量
	private int newCount; // 记录订单中最新售出的商品数量
	private int forEditId; // 用于记录编辑商品的id信息(如果提交编辑失败，会通过存储的id信息，再次返回到编辑页面)

	@Autowired
	OrderInfoMapper orderInfoMapper;
	@Autowired
	GoodsInfoMapper goodsInfoMapper;
	
	/**
	 * Description: 通过侧边菜单栏客户订单发送的请求，获取所有订单信息，并
	 * 	跳转至订单页面，每一页显示15条信息。方法中pay和exp是判断通过付款发
	 * 	货状态提交的表单来查询选择后的订单信息，跳转页面之后选择查询的选择栏
	 * 	能够正确显示对应的信息，查看本类的searchStat()方法。
	 * 	pay表示支付状态，值为1则显示‘-’，值为2则显示‘已付款’，值为3则显示‘未付款’
	 * 	exp表示快递状态，值为1则显示‘-’，值为2则显示‘已发货’，值为3则显示‘未发货’
	 * 	pageSignalForOrder用于给定一个信号，来动态判断是否显示翻页按钮；值为1，则
	 * 	显示；值为0，则不显示。
	 * @param model
	 * @param map
	 * @return 返回String类型，值为order，可自动将order.html页面的代码传递
	 * 	到浏览器进行解析
	 */
	@GetMapping(value="/order")
	public String order(Model model, Map<String,Object> map) {
		
		/*
		 * 	这里调用orderInfoMapper.getOrdersByPage()方法，根据传递的参数可以查询
		 * 	第某页的所有商品信息，并返回一个Orders类型的List集合。
		 */
		Collection<Orders> od = orderInfoMapper.getOrdersByPage( (orderPageNum-1) * 15 );
		
		/*
		 * 	将查询到的所有订单信息，存入到model节点中，在order.html页面可以通
		 * 	过操作orders节点存储的所有商品信息来动态展示到页面中
		 */
		model.addAttribute("orders",od);
		
		map.put("pay", "1");
		map.put("exp", "1");
		map.put("pageSignalForOrder",1);
		return "order";
	}
	
	/**
	 * Description: 上一页按钮发送的get请求做处理。用于设定
	 * 	goodsPageNum的值，减1就是往前翻一页。（每页显示15条信息）
	 * @return 返回重定向的请求值
	 */
	@GetMapping(value="/ordersPre")
	public String orderPre() {
		/*
		 * 	在向上翻页时需要检查goodsPageNum是否大于1。如果等于1时，就一值定位到
		 * 	第一页。
		 */
		if(orderPageNum > 1) {
			orderPageNum -= 1;
		}else {
			orderPageNum = 1;
		}
		return "redirect:/order"; // 重定义，get请求 /order (定位到并执行本类第一个方法)
	}
	
	/**
	 * Description: 下一页按钮的请求做处理。用于设定
	 * 	goodsPageNum的值，加1就是往后翻一页（每页显示15条信息）
	 * @return 返回重定向的请求值
	 */
	@GetMapping(value="/ordersAppend")
	public String orderAppend() {
		/*
		 *	在向下翻页时，需要判断数据库中存储的商品能够显示多少页。 
		 */
		int orderRows = ( orderInfoMapper.getAllorders().size() ) / 15;
		
		if(( orderInfoMapper.getAllorders().size() ) % 15 != 0) {
			orderRows += 1; // 如果除以15有余数说明还要再加一页才能将剩下的商品信息显示完成
		}
		
		/*
		 * 	在向下翻页时需要检查goodsPageNum是否小于最大显示页数。如果等于或大于时，就一值定位到
		 * 	最大页数处。
		 */
		if(orderRows > orderPageNum) {
			orderPageNum += 1;
		}else {
			orderPageNum = orderRows;
		}
		return "redirect:/order";
	}
	
	/**
	 * Description: 对添加订单按钮发送的请求做处理，获取所有商品名，并跳转至添加订单页面
	 * @param model
	 * @return 返回String类型，值为orderAdd，可自动将orderAdd.html页面的代码传递
	 * 	到浏览器进行解析
	 */
	@GetMapping(value="/addOrder")
	public String addOrder(Model model) {
		Collection<GoodsName> gdName = goodsInfoMapper.getGoodsName();
		model.addAttribute("gdAllName", gdName); // 将所有订单信息存储在model节点中，前端对其进行遍历
		return "orderAdd";
	}
	
	/**
	 * Description: 对添加订单页面的提交按钮发动的表单请求做处理，对数据类型不同的变量进行类型转换处理。
	 * @param order
	 * @param ExpNum
	 * @return 返回String类型
	 */
	@Transactional  // 事务处理，必须所有数据在数据库操作完成之后才生效，否则数据不发生变化
	@PostMapping(value="/addOrder")
	public String orderAdd(Orders order,@RequestParam("ExpNum")String ExpNum) {
		// 判断请求中运单号是否为空，如果为空则赋值为字符串的0
		if(ExpNum == "") {
			ExpNum = "0";
		}
		long odExpNum = Long.parseLong(ExpNum); // 将字符串转换为long类型
		order.setOdExpNum(odExpNum); // 转换完成之后，将值赋值给order对象中存储运单号的属性中
		
		/*
		 * 	由于提交的日期后台在进行日期转换时会减一天，因此这里通过调用add()
		 * 	方法可将日期加一天，转到正常日期
		 */
		Calendar cal = Calendar.getInstance();
		cal.setTime(order.getOdTime());
		cal.add(Calendar.DATE, 1);
		order.setOdTime(cal.getTime());
		
		// 对提交表单存储为String类型的修改的售出商品数量 提取出数字，并转换为整型赋值给newCount进行操作(正则表达式)
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(order.getOdCount());
		newCount = Integer.parseInt(matcher.replaceAll(""));
		
		/*
		 *	对库存数据进行处理，添加新的订单，要出售商品，因此要在商品管理的表中
		 *	减少库存中的该商品的库存数量。
		 */
		Goods gd = goodsInfoMapper.getGoodByName(order.getOdGood());
		newCount = gd.getGdCount() - newCount;
		if(newCount >= 0) {
			goodsInfoMapper.updateGoodForCount(gd.getGdID(), newCount); // 更新商品管理该商品库存数量
			orderInfoMapper.addOrder(order); // 向数据库中添加订单
			return "redirect:/order";
		}else {
			return "orderAdd";
		}
	}
	
	/**
	 * Description: 对支付状态和发货状态的查询发送的请求进行处理，pay和exp
	 * 	是判断提交表单后，对应的选择栏显示正确的信息	 
	 * 	pay表示支付状态，值为1则显示‘-’，值为2则显示‘已付款’，值为3则显示‘未付款’
	 * 	exp表示快递状态，值为1则显示‘-’，值为2则显示‘已发货’，值为3则显示‘未发货’
	 * @param payStat
	 * @param expStat
	 * @param model
	 * @param map
	 * @return 返回String类型，值为order，可自动将order.html页面的代码传递
	 * 	到浏览器进行解析
	 */
	@GetMapping(value="/order/searchStat")
	public String searchStat(@RequestParam("payStat")String payStat,
							 @RequestParam("expStat")String expStat,
							 Model model,
							 Map<String,Object> map) {
		// 调用方法，获取符合查询要求的订单信息，并返回
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
		map.put("pageSignalForOrder",null); // 翻页按钮在页面中不显示
		return "order";
	}
	
	/**
	 * Description: 处理编辑按钮发送的请求，并通过id查询到订单信息，跳转到编辑页面，将查询的定单信息
	 * 	显示在表单中。
	 * @param id
	 * @param model
	 * @return 返回String类型，值为orderEdit，可自动将orderEdit.html页面的代码传递
	 * 	到浏览器进行解析
	 */
	@GetMapping(value="/order/{id}")
	public String eidtOrder(@PathVariable("id")int id, Model model) {
		forEditId = id; // 获取需要编辑的商品id
		Orders od = orderInfoMapper.getOrderById(id); // 通过id获取对应的订单信息
		model.addAttribute("order", od);

		// 对提交表单存储为String类型的修改的售出商品数量 提取出数字，并转换为整型赋值给newCount进行操作(正则表达式)
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(od.getOdCount());
		oldCount = Integer.parseInt(matcher.replaceAll(""));
		return "orderEdit";
	}
	
	/**
	 * Description: 对编辑完成的表单的提交按钮发送的请求做处理。
	 * @param order
	 * @param ExpNum
	 * @return 返回String类型
	 */
	@Transactional
	@PostMapping(value="/editOrder")
	public String orderEdit(Orders order,@RequestParam("ExpNum")String ExpNum) {
		// 如果前端没有填写快递单号，传过来的是空，orders类的odExpNum的类型是long，因此要进行转换
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
		
		/*
		 *	对库存数据进行处理，添加新的订单，要出售商品，因此要在商品管理的表中
		 *	减少库存中的该商品的库存数量。
		 */
		if(newCount > oldCount) {
			newCount = newCount - oldCount;
			Goods gd = goodsInfoMapper.getGoodByName(order.getOdGood());
			newCount = gd.getGdCount() - newCount;  // 需要减去库存里的数量
			if(newCount >= 0) {
				goodsInfoMapper.updateGoodForCount(gd.getGdID(), newCount);
			}else {
				return "redirect:/order/" + Integer.toString(forEditId);
			}
		}else if(newCount < oldCount) {
			newCount = oldCount - newCount;
			Goods gd = goodsInfoMapper.getGoodByName(order.getOdGood());
			newCount = gd.getGdCount() + newCount; // 需要增加库存的数量
			goodsInfoMapper.updateGoodForCount(gd.getGdID(), newCount);
		}
		orderInfoMapper.updateOrder(order);
		return "redirect:/order";
	}
	
	/**
	 * Description: 删除按钮发送的请求做处理
	 * @param id
	 * @return 返回String值，重定向
	 */
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
		newCount = gd.getGdCount() + newCount; // 需要增加库存的数量
		goodsInfoMapper.updateGoodForCount(gd.getGdID(), newCount);
		orderInfoMapper.delOrder(id);
		return "redirect:/order";
	}
}
