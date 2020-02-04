package com.mallsystem.controller;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mallsystem.entities.Goods;
import com.mallsystem.mapper.ClassificationInfoMapper;
import com.mallsystem.mapper.GoodsInfoMapper;

@Controller
public class ClassificationController {
	
	// 全局变量，为了判断显示指定的页面
	private String classContent;

	@Autowired
	ClassificationInfoMapper classificationInfoMapper;
	@Autowired
	GoodsInfoMapper goodsInfoMapper;
	
	// 显示贡盐泡菜的请求
	@GetMapping(value="/gongyan")
	public String gonguyan(Map<String,Object> map, Model model) {
		Collection<Goods> gds = classificationInfoMapper.getGoodsByClass("泡菜类-贡盐泡菜");
		map.put("classMsg", "泡菜类 / 贡盐泡菜"); // 用于显示对应的面包屑导航
		map.put("activeCla","1"); // 用于激活当前分类管理的子类，如果该页面显示的泡菜类商品信息，可以将泡菜类a标签激活为黑色	
		model.addAttribute("classes", gds);
		classContent = "泡菜类-贡盐泡菜";
		return "classification";
	}
	
	// 显示韩国泡菜的请求
	@GetMapping(value="/hanguo")
	public String hanguo(Map<String,Object> map, Model model) {
		Collection<Goods> gds = classificationInfoMapper.getGoodsByClass("泡菜类-韩国泡菜");
		map.put("classMsg", "泡菜类 / 韩国泡菜");
		map.put("activeCla","1");
		model.addAttribute("classes", gds);
		classContent = "泡菜类-韩国泡菜";
		return "classification";
	}
	
	// 显示红花椒的请求
	@GetMapping(value="/hong")
	public String hong(Map<String,Object> map, Model model) {
		Collection<Goods> gds = classificationInfoMapper.getGoodsByClass("花椒类-红花椒");
		map.put("classMsg", "花椒类 / 红花椒");
		map.put("activeCla","2");
		model.addAttribute("classes", gds);
		classContent = "花椒类-红花椒";
		return "classification";
	}

	// 显示青花椒的请求
	@GetMapping(value="/qing")
	public String qing(Map<String,Object> map, Model model) {
		Collection<Goods> gds = classificationInfoMapper.getGoodsByClass("花椒类-青花椒");
		map.put("classMsg", "花椒类 / 青花椒");
		map.put("activeCla","2");
		model.addAttribute("classes", gds);
		classContent = "花椒类-青花椒";
		return "classification";
	}
	
	// 显示酱油的请求
	@GetMapping(value="/jiangyou")
	public String jiangYou(Map<String,Object> map, Model model) {
		Collection<Goods> gds = classificationInfoMapper.getGoodsByClass("酱油类");
		map.put("classMsg", "酱油类");
		map.put("activeCla","3");
		model.addAttribute("classes", gds);
		classContent = "酱油类";
		return "classification";
	}
	
	// 显示食用油的请求
	@GetMapping(value="/shiyongyou")
	public String shiYongYou(Map<String,Object> map, Model model) {
		Collection<Goods> gds = classificationInfoMapper.getGoodsByClass("食用油类");
		map.put("classMsg", "食用油类");
		map.put("activeCla","4");
		model.addAttribute("classes", gds);
		classContent = "食用油类";
		return "classification";
	}
	
	// 显示其他的请求
	@GetMapping(value="/other")
	public String other(Map<String,Object> map, Model model) {
		Collection<Goods> gds = classificationInfoMapper.getGoodsByClass("其他类");
		map.put("classMsg", "其他类");
		map.put("activeCla","5");
		model.addAttribute("classes", gds);
		classContent = "其他类";
		return "classification";
	}
	
	// 对编辑按钮的请求做处理，读取需要编辑的商品信息，并跳转到编辑页面
	@GetMapping(value="/class/{id}")
	public String classById(@PathVariable("id")int id,Model model) {
		Goods gd = goodsInfoMapper.getGood(id);
		model.addAttribute("good",gd);
		return "classificationEdit";
	}
	
	// 处理请求，修改指定商品的类别信息
	@PostMapping(value="/editClass")
	public String classEdit(Goods good) {
		classificationInfoMapper.updateGoodByClass(good);
		if(classContent == "泡菜类-贡盐泡菜") {
			return "redirect:/gongyan";
		}else if(classContent == "泡菜类-韩国泡菜") {
			return "redirect:/hanguo";
		}else if(classContent == "花椒类-红花椒") {
			return "redirect:/hong";
		}else if(classContent == "花椒类-青花椒") {
			return "redirect:/qing";
		}else if(classContent == "酱油类") {
			return "redirect:/jiangyou";
		}else if(classContent == "食用油类") {
			return "redirect:/shiyongyou";
		}else if(classContent == "其他类") {
			return "redirect:/other";
		}else {
			return "404";
		}
	}
}
