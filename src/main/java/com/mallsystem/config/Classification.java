package com.mallsystem.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mallsystem.entities.GoodsClass;
import com.mallsystem.mapper.ClassificationInfoMapper;

/**
 * Created with Eclipse
 * @author heroC
 * @see com.mallsystem.mapper.ClassificationInfoMapper
 * @since JDK1.8
 * @version 1.0
 * Description: 该类是用于处理左侧菜单栏成列的类别
 */
public class Classification {
	@Autowired
	ClassificationInfoMapper classificationInfoMapper;
	
	/**
	 * Description: 参数的类型为GoodsClass类型的List集合，其中存储了很多个GoodsClass
	 * 	的实列，对每个实例的数据进行重复剔除，规范处理，然后将处理完成了的类别返回出去。
	 * @param list
	 * @return
	 */
	public List<GoodsClass> classificatonMethod(List<GoodsClass> list) {
		List<GoodsClass> showClass = list; // 将传入的集合赋值给showClass新集合
		String gdClass; // 用于存放大类别
		
		/*
		 * 	该循环用于将如‘酱油类-生抽’这样的类别，抽取出大类别，将‘-’以及后面的内容去除掉，
		 * 	只存储酱油类到新集合中。
		 */
		for(int i = 0; i < showClass.size(); i++) {
			String gdC = showClass.get(i).getGdClass();
			if(gdC.contains("-")) {
				gdClass = gdC.substring(0, gdC.indexOf("-"));
				showClass.get(i).setGdClass(gdClass);
				showClass.get(i).setGdClass(gdClass);
			}
		}
		
		/*
		 *	获取到了的大类别集合，将其中的重复类别去除掉，只保留唯一的类别
		 */
		for(int i = 0; i < showClass.size()-1; i++) {
			for(int j = showClass.size()-1; j > i; j--) {
				if(showClass.get(j).getGdClass().equals(showClass.get(i).getGdClass())) {
					showClass.remove(j);
				}
			}
		}
		
		/*
		 * 	现在新集合中存放的都是不重复不同的类别值，因此将每个类别值编号，
		 * 	以此前端可用过唯一编号id，获取到对应类别的全部商品信息。
		 */
		for(int i = 0; i < showClass.size(); i++) {
			showClass.get(i).setNumber(i+1);
		}
		
		return showClass; // 返回整理完的类别集合
	}
}
