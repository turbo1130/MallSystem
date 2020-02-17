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

/**
 * Created with Eclipse
 * @author 刘志远
 * @see com.mallsystem.config.Classification
 * @see com.mallsystem.mapper.ClassificationInfoMapper
 * @see com.mallsystem.mapper.GoodsInfoMapper
 * @since JDK1.8
 * @version 1.0
 * Description: 该类是用于处理接收分类管理模块的Get/Post请求
 */
@Controller
public class ClassificationController {
	
	// 私有属性classContent，用于记录左侧菜单栏类别分类的高亮类别的id信息
	private int classContent;

	@Autowired
	ClassificationInfoMapper classificationInfoMapper;
	@Autowired
	GoodsInfoMapper goodsInfoMapper;
	
	/**
	 * Description: 获取到由bar.html文件中分类管理a标签发送的get请求。
	 *	主要的操作是，获取到请求中的id数据，可以记录当前高亮的类别的id信息；
	 *	在跳转到下一个页面时，需要查询到属于该类的所有商品信息，并返回到下一
	 *	个页面之中。其中通过查询商品类别，将获取的商品类别通过Classification
	 *	类进行筛选出大类别，在将重复的大类别剔除，并对大类别设定一个id，返回
	 *	一个分类的列表，然后经过for循环遍历出与id相同的类，就可以定位到当前
	 *	显示的商品是哪一个类别了。还向前端传输了对应的面包屑显示的内容。
	 * @see com.mallsystem.config.Classification
	 * @param id
	 * @param map
	 * @param model
	 * @return 返回一个classification字符串，可自动定位到classification.html页面
	 */
	@GetMapping(value="/classes/{id}")
	public String classificationSpecifiedPage(@PathVariable("id")int id,Map<String,Object> map, Model model) {
		Classification classification = new Classification();
		List<GoodsClass> collClass = 
				classification.classificatonMethod(classificationInfoMapper.getGoodsClass()); // 接受到整理过的商品类别List集合
		// 通过for循环找到id相同的商品类别
		for(int i = 0; i < collClass.size(); i++) {
			if(collClass.get(i).getNumber() == id) {
				String classifi = collClass.get(i).getGdClass(); // 获取商品类别
				Collection<Goods> gds = classificationInfoMapper.getGoodsByClass(classifi); // 获取该类别的所有商品信息
				map.put("classMsg", classifi); // 用于显示对应的面包屑导航
				map.put("activeCla",id); // 用于激活当前分类管理的子类，如果该页面显示的泡菜类商品信息，可以将泡菜类a标签激活为黑色	
				model.addAttribute("classeS", gds); // 将商品信息存储到model中名为classeS的节点，前端classification.html可根据此进行遍历获取商品信息
				classContent = id;
			}
		}
		return "classification";
	}
	
	/**
	 * Description: 对编辑按钮的请求做处理，读取需要编辑的商品信息，并跳转到编辑页面。
	 *	通过get请求路径，获取到前端点击编辑按钮的商品id，通过调用goodsInfoMapper的
	 *	getGood方法，获取到该商品的所有信息，由gd接收信息，将信息存放到model的good
	 *	节点中，由前端代码进行识别对应的具体信息，由此可以展示该商品的所有信息。
	 * @param id
	 * @param model
	 * @return 返回一个classificationEdit字符串，可自动定位到classificationEdit.html页面
	 */
	@GetMapping(value="/class/{id}")
	public String classById(@PathVariable("id")int id,Model model) {
		Goods gd = goodsInfoMapper.getGood(id); // 获取正确的商品信息
		model.addAttribute("good",gd); // 将获取到的数据信息，存放到model名为good的节点中
		return "classificationEdit";
	}
	
	/**
	 * Description: 处理请求，修改指定商品的类别信息，并返回修改前的类别页面。
	 * 	将前端提交的post请求中的数据信息自动封装到Good对象中，然后通过调用
	 * 	classificationInfoMapper的updateGoodByClass方法，可以将数据进行更新。
	 * 	在更新商品数据时，用户可能会更改类别，此时需要重新获取数据库的类别信息，
	 * 	以此来更新左侧栏类别菜单。然后从编辑页面，返回到该类别的主页面。
	 * @see com.mallsystem.config.Classification
	 * @param good
	 * @param session
	 * @return 返回一个重定向的get请求路径，String类型请求路径。会自动识别到
	 * 	/classes/{id}的get请求方法中去实现功能(本类的第一个方法)
	 */
	@PostMapping(value="/editClass")
	public String classEdit(Goods good,HttpSession session) {
		classificationInfoMapper.updateGoodByClass(good);
		
		Classification classification = new Classification();
		Collection<GoodsClass> collClass = classification.classificatonMethod(classificationInfoMapper.getGoodsClass());
		session.setAttribute("sClasses",collClass);
		
		return "redirect:/classes/"+classContent;
	}
}
