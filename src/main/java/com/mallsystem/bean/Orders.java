package com.mallsystem.bean;

import java.util.Date;

public class Orders {
	private int odID;
	private String odCustomer;
	private String odGood;
	private String odCount;
	private String odPayStat;
	private String odExpStat;
	private long odExpNum;
	private Date odTime;
	
	public int getOdID() {
		return odID;
	}
	public void setOdID(int odID) {
		this.odID = odID;
	}
	public String getOdCustomer() {
		return odCustomer;
	}
	public void setOdCustomer(String odCustomer) {
		this.odCustomer = odCustomer;
	}
	public String getOdGood() {
		return odGood;
	}
	public void setOdGood(String odGood) {
		this.odGood = odGood;
	}
	public String getOdCount() {
		return odCount;
	}
	public void setOdCount(String odCount) {
		this.odCount = odCount;
	}
	public String getOdPayStat() {
		return odPayStat;
	}
	public void setOdPayStat(String odPayStat) {
		this.odPayStat = odPayStat;
	}
	public String getOdExpStat() {
		return odExpStat;
	}
	public void setOdExpStat(String odExpStat) {
		this.odExpStat = odExpStat;
	}
	public long getOdExpNum() {
		return odExpNum;
	}
	public void setOdExpNum(long odExpNum) {
		this.odExpNum = odExpNum;
	}
	public Date getOdTime() {
		return odTime;
	}
	public void setOdTime(Date odTime) {
		this.odTime = odTime;
	}
	@Override
	public String toString() {
		return "Orders [odID=" + odID + ", odCustomer=" + odCustomer + ", odGood=" + odGood + ", odCount=" + odCount
				+ ", odPayStat=" + odPayStat + ", odExpStat=" + odExpStat + ", odExpNum=" + odExpNum + ", odTime="
				+ odTime + "]";
	}
	
}
