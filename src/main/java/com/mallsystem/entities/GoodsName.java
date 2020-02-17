package com.mallsystem.entities;

/**
 * Created with Eclipse
 * @author heroC
 * @since JDK1.8
 * @version 1.0
 * Description: 该类是用于存储商品名字信息的普通类，其中有getter/setter/toString方法
 */
public class GoodsName {
	private String gdName;

	public String getGdName() {
		return gdName;
	}

	public void setGdName(String gdName) {
		this.gdName = gdName;
	}

	@Override
	public String toString() {
		return "GoodsName [gdName=" + gdName + "]";
	}
}
