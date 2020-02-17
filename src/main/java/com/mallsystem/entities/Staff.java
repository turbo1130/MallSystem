package com.mallsystem.entities;

/**
 * Created with Eclipse
 * @author heroC
 * @since JDK1.8
 * @version 1.0
 * Description: 该类是用于存员工信息的普通类，其中有getter/setter/toString方法
 */
public class Staff {
	private int staffID;
	private String staffName;
	private String loginName;
	private String addOp;
	private String delOp;
	private String editOp;
	
	public Staff() {
		super();
	}
	
	public Staff(int staffID, String staffName, String loginName, String addOp, String delOp, String editOp) {
		super();
		this.staffID = staffID;
		this.staffName = staffName;
		this.loginName = loginName;
		this.addOp = addOp;
		this.delOp = delOp;
		this.editOp = editOp;
	}
	
	public int getStaffID() {
		return staffID;
	}
	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getAddOp() {
		return addOp;
	}
	public void setAddOp(String addOp) {
		this.addOp = addOp;
	}
	public String getDelOp() {
		return delOp;
	}
	public void setDelOp(String delOp) {
		this.delOp = delOp;
	}
	public String getEditOp() {
		return editOp;
	}
	public void setEditOp(String editOp) {
		this.editOp = editOp;
	}
	
	@Override
	public String toString() {
		return "Staff [staffID=" + staffID + ", staffName=" + staffName + ", loginName=" + loginName + ", addOp="
				+ addOp + ", delOp=" + delOp + ", editOp=" + editOp + "]";
	}
	
}
