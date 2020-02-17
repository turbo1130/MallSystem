package com.mallsystem.entities;

import java.util.Date;

/**
 * Created with Eclipse
 * @author 刘志远
 * @since JDK1.8
 * @version 1.0
 * Description: 该类是用于存储商品信息的普通类，其中有getter/setter/toString方法
 */
public class Goods {
	private int gdID;
	private String gdName;
	private int gdCount;
	private String gdPrice;
	private String gdClass;
	private Date gdTime;
	
	public int getGdID() {
		return gdID;
	}
	public void setGdID(int gdID) {
		this.gdID = gdID;
	}
	public String getGdName() {
		return gdName;
	}
	public void setGdName(String gdName) {
		this.gdName = gdName;
	}
	public int getGdCount() {
		return gdCount;
	}
	public void setGdCount(int gdCount) {
		this.gdCount = gdCount;
	}
	public String getGdPrice() {
		return gdPrice;
	}
	public void setGdPrice(String gdPrice) {
		this.gdPrice = gdPrice;
	}
	public String getGdClass() {
		return gdClass;
	}
	public void setGdClass(String gdClass) {
		this.gdClass = gdClass;
	}
	public Date getGdTime() {
		return gdTime;
	}
	public void setGdTime(Date gdTime) {
		this.gdTime = gdTime;
	}
	@Override
	public String toString() {
		return "Goods [gdID=" + gdID + ", gdName=" + gdName + ", gdCount=" + gdCount + ", gdPrice=" + gdPrice
				+ ", gdClass=" + gdClass + ", gdTime=" + gdTime + "]";
	}
	
}
