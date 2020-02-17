package com.mallsystem.entities;

import java.util.Date;

/**
 * Created with Eclipse
 * @author heroC
 * @since JDK1.8
 * @version 1.0
 * Description: 该类是用于存储客户反馈信息的普通类，其中有getter/setter/toString方法
 */
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
