package com.mallsystem.entities;

/**
 * Created with Eclipse
 * @author 刘志远
 * @since JDK1.8
 * @version 1.0
 * Description: 该类是用于存储登录用户的登录密码密保信息的普通类，其中有getter/setter/toString方法
 */
public class SysUser {
	private String sysName;
	private String sysPwd;
	private String sysEncrypted;
	private String sysAnswer;
	
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public String getSysPwd() {
		return sysPwd;
	}
	public void setSysPwd(String sysPwd) {
		this.sysPwd = sysPwd;
	}
	public String getSysEncrypted() {
		return sysEncrypted;
	}
	public void setSysEncrypted(String sysEncrypted) {
		this.sysEncrypted = sysEncrypted;
	}
	public String getSysAnswer() {
		return sysAnswer;
	}
	public void setSysAnswer(String sysAnswer) {
		this.sysAnswer = sysAnswer;
	}
	@Override
	public String toString() {
		return "SysUser [sysName=" + sysName + ", sysPwd=" + sysPwd + ", sysEncrypted=" + sysEncrypted + ", sysAnswer="
				+ sysAnswer + "]";
	}
	
	
}
