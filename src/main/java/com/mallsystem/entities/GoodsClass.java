package com.mallsystem.entities;

/**
 * Created with Eclipse
 * @author heroC
 * @since JDK1.8
 * @version 1.0
 * Description: 该类是用于存储商品类别信息的普通类，其中有getter/setter/toString方法
 */
public class GoodsClass {
	private String gdClass;

	private int number;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getGdClass() {
		return gdClass;
	}

	public void setGdClass(String gdClass) {
		this.gdClass = gdClass;
	}
	
	@Override
	public String toString() {
		return "GoodsClass [gdClass=" + gdClass + ", number=" + number + "]";
	}
}
