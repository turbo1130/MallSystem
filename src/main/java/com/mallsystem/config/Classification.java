package com.mallsystem.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mallsystem.entities.GoodsClass;
import com.mallsystem.mapper.ClassificationInfoMapper;

public class Classification {
	@Autowired
	ClassificationInfoMapper classificationInfoMapper;
	
	public List<GoodsClass> classificatonMethod(List<GoodsClass> list) {
		List<GoodsClass> showClass = list;
		String gdClass;
		for(int i = 0; i < showClass.size(); i++) {
			String gdC = showClass.get(i).getGdClass();
			if(gdC.contains("-")) {
				gdClass = gdC.substring(0, gdC.indexOf("-"));
				showClass.get(i).setGdClass(gdClass);
				showClass.get(i).setGdClass(gdClass);
			}
		}
		for(int i = 0; i < showClass.size()-1; i++) {
			for(int j = showClass.size()-1; j > i; j--) {
				if(showClass.get(j).getGdClass().equals(showClass.get(i).getGdClass())) {
					showClass.remove(j);
				}
			}
		}
		for(int i = 0; i < showClass.size(); i++) {
			showClass.get(i).setNumber(i+1);
		}
		return showClass;
	}
}
