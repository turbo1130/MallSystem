package com.mallsystem.bean;

import java.util.Date;

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
