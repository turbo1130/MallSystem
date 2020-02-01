package com.mallsystem.bean;

import java.util.Date;

public class LeaveAMsg {
	private String msgCustomer;
	private String msgContent;
	private Date msgTime;
	
	public String getMsgCustomer() {
		return msgCustomer;
	}
	public void setMsgCustomer(String msgCustomer) {
		this.msgCustomer = msgCustomer;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public Date getMsgTime() {
		return msgTime;
	}
	public void setMsgTime(Date msgTime) {
		this.msgTime = msgTime;
	}
	@Override
	public String toString() {
		return "LeaveAMsg [msgCustomer=" + msgCustomer + ", msgContent=" + msgContent + ", msgTime=" + msgTime + "]";
	}
	
	
}
