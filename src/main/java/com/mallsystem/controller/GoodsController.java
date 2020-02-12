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

@Controller
public class GoodsController {
	
	// 给定全局变量，为了确定商品管理展示信息的当前是第几页面
	int goodsPageNum = 1;
	
	@Autowired
	GoodsInfoMapper goodsInfoMapper;
	@Autowired
	OrderInfoMapper orderInfoMapper;
	@Autowired
	ClassificationInfoMapper classificationInfoMapper;
	
	
	// goods.html
	// 通过get请求/goods路径，响应数据库所有商品信息
	@GetMapping(value="/goods")
	public String goods(Model model, Map<String, Object> map) {
		Collection<Goods> gds = goodsInfoMapper.pageGoods( (goodsPageNum-1)*15 );
		model.addAttribute("goods", gds);  // 将查询到的所有商品信息，存入到model节点中，在goods.html页面可以通过操作goods节点存储的所有商品信息来动态展示到页面中
		map.put("pageSignalForGood", 1); // 用于给定一个信号，来动态判断是否显示翻页按钮（88行）
		return "goods";
	}
	
	// 对goods.html页面的上一页按钮的请求做处理
	@GetMapping(value="/goodsPre")
	public String goodsPre() {
		if(goodsPageNum > 1) {
			goodsPageNum-=1;
		}else {
			goodsPageNum = 1;
		}
		return "redirect:/goods"; // 重定义，请求 /goods
	}
	
	// 对goods.html页面的下一页按钮的请求做处理
	@GetMapping(value="/goodsAppend")
	public String goodsAppend() {
		Collection<Goods> gds = goodsInfoMapper.getAllGoods();
		int goodsRows = gds.size()/15;
		if(gds.size()%15 != 0) {
			goodsRows+=1;  // 如果除以15有余数说明还要多一页才能将所有的商品信息进行显示
		}
		if(goodsPageNum < goodsRows) {
			goodsPageNum+=1;
		}else {
			goodsPageNum = goodsRows;
		}
		return "redirect:/goods";
	}
	
	// 对添加商品按钮的请求做处理，点击添加商品按钮跳转到添加商品页面
	@GetMapping(value="/addGood")
	public String addGood() {
		return "goodsAdd";
	}
	
	// 对搜索栏的请求做处理
	@GetMapping(value="/goodSearch")
	public String goodSearch(@RequestParam("sType")String sType, 
			                 @RequestParam("sContent")String sContent, 
							 Model model,
							 Map<String, Object> map) {
		if(sType.equals("名称")) {
			sType="gdName";
		}else {
			sType="gdClass";
		}
		Collection<Goods> gds = goodsInfoMapper.getPassGoods(sType, sContent);
		model.addAttribute("goods", gds);
		if(sContent.equals("")) {
			goodsPageNum = 1;
			return "redirect:/goods";
		}else {
			map.put("pageSignalForGood", null);
			return "goods";
		}
		
	}
	
	// 对编辑按钮的请求做处理，读取需要编辑的商品信息，并跳转到编辑页面
	@GetMapping(value="/good/{id}")
	public String goodIdEdit(@PathVariable("id")int id, Model model) {
		Goods gd = goodsInfoMapper.getGood(id);
		model.addAttribute("good", gd);
		return "goodsEdit";
	}
	
	// 对删除按钮的请求做处理
	@PostMapping(value="/good/{id}")
	public String goodIdDel(@PathVariable("id")int id,HttpSession session) {
		Goods gd = goodsInfoMapper.getGood(id);
		int count  = orderInfoMapper.getOrderCountByGood(gd.getGdName());
		if(count == 0) {
			goodsInfoMapper.DelGoodById(id);
		}
		
		Classification classification = new Classification();
		Collection<GoodsClass> collClass = classification.classificatonMethod(classificationInfoMapper.getGoodsClass());
		session.setAttribute("sClasses",collClass);
		
		return "redirect:/goods";
	}
	
	// goodsAdd.html
	// 对添加商品提交信息的请求进行处理
	@PostMapping(value="/addGood")
	public String goodAdd(Goods good,HttpSession session) {
		// 由于提交的日期后台在进行日期转换时会减一天，因此这里的操作时将日期加一天，转到正常日期
		Calendar cal = Calendar.getInstance();
		cal.setTime(good.getGdTime());
		cal.add(Calendar.DATE, 1);
		good.setGdTime(cal.getTime());
		goodsInfoMapper.AddGood(good);
		
		Classification classification = new Classification();
		Collection<GoodsClass> collClass = classification.classificatonMethod(classificationInfoMapper.getGoodsClass());
		session.setAttribute("sClasses",collClass);
		
		return "redirect:/goods";
	}
	
	//goodsEdit.html
	// 对编辑商品的信息提交的请求进行处理
	@PostMapping(value="/editGood")
	public String goodEidt(Goods good,HttpSession session) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(good.getGdTime());
		cal.add(Calendar.DATE, 1);
		good.setGdTime(cal.getTime());
		goodsInfoMapper.UpdateGood(good);
		
		Classification classification = new Classification();
		Collection<GoodsClass> collClass = classification.classificatonMethod(classificationInfoMapper.getGoodsClass());
		session.setAttribute("sClasses",collClass);
		
		return "redirect:/goods";
	}
}
