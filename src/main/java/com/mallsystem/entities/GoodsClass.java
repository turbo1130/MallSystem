package com.mallsystem.entities;

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
