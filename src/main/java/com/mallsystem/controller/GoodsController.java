package com.mallsystem.controller;

import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mallsystem.config.Classification;
import com.mallsystem.entities.Goods;
import com.mallsystem.entities.GoodsClass;
import com.mallsystem.mapper.ClassificationInfoMapper;
import com.mallsystem.mapper.GoodsInfoMapper;
import com.mallsystem.mapper.OrderInfoMapper;

/**
 * Created with Eclipse
 * @author 刘志远
 * @see com.mallsystem.mapper.ClassificationInfoMapper
 * @see com.mallsystem.mapper.GoodsInfoMapper
 * @see com.mallsystem.mapper.OrderInfoMapper
 * @since JDK1.8
 * @version 1.0
 * Description: 该类处理商品管理模块的所有请求
 */
@Controller
public class GoodsController {
	
	// 给定全局变量，为了确定商品管理展示信息的当前是第几页
	int goodsPageNum = 1;
	
	@Autowired
	GoodsInfoMapper goodsInfoMapper;
	@Autowired
	OrderInfoMapper orderInfoMapper;
	@Autowired
	ClassificationInfoMapper classificationInfoMapper;
	
	
	/**
	 * Description: 通过侧边菜单分类管理发起get请求/goods路径，获取数据库
	 * 	所有商品信息并展示到分类管理主页。并根据goodsPageNum的值，获取第几
	 * 	页的所有商品信息。（每页显示15条信息）
	 * @param model
	 * @param map
	 * @return 返回String类型，值为goods，可自动将goods.html页面的代码传递
	 * 	到浏览器进行解析
	 */
	@GetMapping(value="/goods")
	public String goods(Model model, Map<String, Object> map) {
		
		/*
		 * 	这里调用goodsInfoMapper.pageGoods()方法，根据传递的参数可以查询
		 * 	第某页的所有商品信息，并返回一个Goods类型的List集合。
		 */
		Collection<Goods> gds = goodsInfoMapper.pageGoods( (goodsPageNum-1)*15 );
		
		/*
		 * 	将查询到的所有商品信息，存入到model节点中，在goods.html页面可以通
		 * 	过操作goods节点存储的所有商品信息来动态展示到页面中
		 */
		model.addAttribute("goods", gds);
		
		/*
		 *	用于给定一个信号，来动态判断是否显示翻页按钮，在前端通过搜索栏搜索
		 *	的商品信息，不显示翻页按钮，会一次性将商品信息全部展现出来
		 */
		map.put("pageSignalForGood", 1);
		return "goods";
	}
	
	
	/**
	 * Description: 对goods.html页面的上一页按钮发送的get请求做处理。用于设定
	 * 	goodsPageNum的值，减1就是往前翻一页（每页显示15条信息）
	 * @return 返回重定向的请求值
	 */
	@GetMapping(value="/goodsPre")
	public String goodsPre() {
		/*
		 * 	在向上翻页时需要检查goodsPageNum是否大于1。如果等于1时，就一值定位到
		 * 	第一页。
		 */
		if(goodsPageNum > 1) {
			goodsPageNum-=1;
		}else {
			goodsPageNum = 1;
		}
		return "redirect:/goods"; // 重定义，get请求 /goods(定位到并执行本类第一个方法)
	}
	
	
	/**
	 * Description: 对goods.html页面的下一页按钮的请求做处理。用于设定
	 * 	goodsPageNum的值，加1就是往后翻一页（每页显示15条信息）
	 * @return 返回重定向的请求值
	 */
	@GetMapping(value="/goodsAppend")
	public String goodsAppend() {
		/*
		 *	在向下翻页时，需要判断数据库中存储的商品能够显示多少页。 
		 */
		Collection<Goods> gds = goodsInfoMapper.getAllGoods();
		int goodsRows = gds.size()/15;
		if(gds.size()%15 != 0) {
			goodsRows+=1;  // 如果除以15有余数说明还要再加一页才能将剩下的商品信息显示完成
		}
		
		/*
		 * 	在向下翻页时需要检查goodsPageNum是否小于最大显示页数。如果等于或大于时，就一值定位到
		 * 	最大页数处。
		 */
		if(goodsPageNum < goodsRows) {
			goodsPageNum+=1;
		}else {
			goodsPageNum = goodsRows;
		}
		return "redirect:/goods";
	}
	
	
	/**
	 * Description: 对添加商品按钮的请求做处理，点击添加商品按钮跳转到添加商品页面
	 * @return 返回String类型，值为goods，可自动将goodsAdd.html页面的代码传递
	 * 	到浏览器进行解析
	 */
	@GetMapping(value="/addGood")
	public String addGood() {
		return "goodsAdd";
	}
	
	
	/**
	 * Description: 对商品管理的搜索栏发送的get请求做处理。sType是前端通过请求
	 * 	路径传递过来的查询名称还是类别。sContent是需要获取的内容。
	 * @param sType
	 * @param sContent
	 * @param model
	 * @param map
	 * @return 如果查询内容为空，那么重定向到分类管理主页；如果有内容，则重新将goods.html
	 * 	代码传到浏览器解析，并将新的商品信息显示到页面中。
	 */
	@GetMapping(value="/goodSearch")
	public String goodSearch(@RequestParam("sType")String sType, 
			                 @RequestParam("sContent")String sContent, 
							 Model model,
							 Map<String, Object> map) {
		// 判断查询的是名称还是类别
		if(sType.equals("名称")) {
			sType="gdName";
		}else {
			sType="gdClass";
		}
		
		// 通过调用goodsInfoMapper.getPassGoods()方法获取符合查询要求的商品信息
		Collection<Goods> gds = goodsInfoMapper.getPassGoods(sType, sContent);
		model.addAttribute("goods", gds); // 将新的商品信息重新存储到goods中
		
		// 判断内容是否为空
		if(sContent.equals("")) {
			goodsPageNum = 1;
			return "redirect:/goods";
		}else {
			map.put("pageSignalForGood", null);
			return "goods";
		}
		
	}
	
	
	/**
	 * Description: 对编辑按钮发送的请求做处理，读取需要编辑的商品信息。
	 * 	id为前端鼠标单击编辑按钮时，获取到对应的商品id。
	 * @param id
	 * @param model
	 * @return 返回String类型，值为goodsEdit，可自动将goodsEdit.html页面的代码传递
	 * 	到浏览器进行解析，
	 */
	@GetMapping(value="/good/{id}")
	public String goodIdEdit(@PathVariable("id")int id, Model model) {
		Goods gd = goodsInfoMapper.getGood(id);
		
		/*
		 * 向model中名good的节点，存储从数据库获取的记录，前端遍历存储的
		 * 	内容，展示到编辑页的输入框中
		 */
		model.addAttribute("good", gd);
		return "goodsEdit";
	}
	
	
	/**
	 * Description: 对删除按钮发送的请求做处理。当删除商品之后，需要再次检查
	 * 	数据库中的类别，动态更新菜单栏的分类菜单。
	 * @param id
	 * @param session
	 * @return 返回重定向
	 */
	@PostMapping(value="/good/{id}")
	public String goodIdDel(@PathVariable("id")int id,HttpSession session) {
		
		/*
		 * 	在删除商品之前，需要获取该id对应的商品名，然后查看订单中是否有该商品的订单，
		 * 	如果有就不能对商品进行删除，如果没有该商品的订单，则执行删除操作。
		 */
		Goods gd = goodsInfoMapper.getGood(id);
		int count  = orderInfoMapper.getOrderCountByGood(gd.getGdName());
		if(count == 0) {
			goodsInfoMapper.DelGoodById(id);
		}
		
		/*
		 * 	其中通过查询商品类别，将获取的商品类别通过Classification类进行筛选出
		 * 	大类别，在将重复的大类别剔除，并对大类别设定一个id，返回一个分类的列表，
		 * 	存储到session中，可更新侧边栏的分类菜单列表。
		 */
		Classification classification = new Classification();
		Collection<GoodsClass> collClass = classification.classificatonMethod(classificationInfoMapper.getGoodsClass());
		session.setAttribute("sClasses",collClass);
		
		return "redirect:/goods";
	}
	
	
	/**
	 * Description: 对添加商品页面提交按钮发送的请求进行处理。
	 * @param good
	 * @param session
	 * @return 返回重定向
	 */
	@PostMapping(value="/addGood")
	public String goodAdd(Goods good,HttpSession session) {
		// 由于提交的日期后台在进行日期转换时会减一天，因此这里通过调用add()方法可将日期加一天，转到正常日期
		Calendar cal = Calendar.getInstance();
		cal.setTime(good.getGdTime());
		cal.add(Calendar.DATE, 1);
		good.setGdTime(cal.getTime());
		// 日期转换完成，通过调用方法，向数据过写入新的数据
		goodsInfoMapper.AddGood(good);

		// 由于添加新的商品，可能会涉及到会增加新的类别，所以要重新获取类别。
		Classification classification = new Classification();
		Collection<GoodsClass> collClass = classification.classificatonMethod(classificationInfoMapper.getGoodsClass());
		session.setAttribute("sClasses",collClass);
		
		return "redirect:/goods";
	}
	
	
	/**
	 * Description: 对编辑商品页面提交按钮发送的请求进行处理。
	 * @param good
	 * @param session
	 * @return 返回重定向
	 */
	@PostMapping(value="/editGood")
	public String goodEidt(Goods good,HttpSession session) {
		// 由于提交的日期后台在进行日期转换时会减一天，因此这里通过调用add()方法可将日期加一天，转到正常日期
		Calendar cal = Calendar.getInstance();
		cal.setTime(good.getGdTime());
		cal.add(Calendar.DATE, 1);
		good.setGdTime(cal.getTime());
		
		/*
		 * 	在更新商品之前，需要获取该id对应的商品名，然后查看订单中是否有该商品的订单，
		 * 	如果有就不能对商品进行更新，如果没有该商品的订单，则执行更新操作。
		 */ 
		Goods god = goodsInfoMapper.getGood(good.getGdID());
		int count  = orderInfoMapper.getOrderCountByGood(god.getGdName());
		if(count == 0) {
			goodsInfoMapper.UpdateGood(good);
		}
		
		// 由于更新商品，可能会涉及到会增加新的类别，所以要重新获取类别。
		Classification classification = new Classification();
		Collection<GoodsClass> collClass = classification.classificatonMethod(classificationInfoMapper.getGoodsClass());
		session.setAttribute("sClasses",collClass);
		
		return "redirect:/goods";
	}
}
