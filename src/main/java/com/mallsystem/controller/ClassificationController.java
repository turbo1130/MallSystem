package com.mallsystem.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mallsystem.config.Classification;
import com.mallsystem.entities.Goods;
import com.mallsystem.entities.GoodsClass;
import com.mallsystem.mapper.ClassificationInfoMapper;
import com.mallsystem.mapper.GoodsInfoMapper;

@Controller
public class ClassificationController {
	
	// 全局变量，为了判断显示指定的页面
	private int classContent;

	@Autowired
	ClassificationInfoMapper classificationInfoMapper;
	@Autowired
	GoodsInfoMapper goodsInfoMapper;
	
	// 显示贡盐泡菜的请求
	
	@GetMapping(value="/classes/{id}")
	public String gonguyan(@PathVariable("id")int id,Map<String,Object> map, Model model) {
		Classification classification = new Classification();
		List<GoodsClass> collClass = classification.classificatonMethod(classificationInfoMapper.getGoodsClass());
		for(int i = 0; i < collClass.size(); i++) {
			if(collClass.get(i).getNumber() == id) {
				String classifi = collClass.get(i).getGdClass();
				Collection<Goods> gds = classificationInfoMapper.getGoodsByClass(classifi);
				map.put("classMsg", classifi); // 用于显示对应的面包屑导航
				map.put("activeCla",id); // 用于激活当前分类管理的子类，如果该页面显示的泡菜类商品信息，可以将泡菜类a标签激活为黑色	
				model.addAttribute("classeS", gds);
				classContent = id;
			}
		}
		return "classification";
	}
	
	// 对编辑按钮的请求做处理，读取需要编辑的商品信息，并跳转到编辑页面
	@GetMapping(value="/class/{id}")
	public String classById(@PathVariable("id")int id,Model model) {
		Goods gd = goodsInfoMapper.getGood(id);
		model.addAttribute("good",gd);
		return "classificationEdit";
	}
	
	// 处理请求，修改指定商品的类别信息，并返回修改前的类别页面
	@PostMapping(value="/editClass")
	public String classEdit(Goods good,HttpSession session) {
		classificationInfoMapper.updateGoodByClass(good);
		
		Classification classification = new Classification();
		Collection<GoodsClass> collClass = classification.classificatonMethod(classificationInfoMapper.getGoodsClass());
		session.setAttribute("sClasses",collClass);
		
		return "redirect:/classes/"+classContent;
		
	}
}
