package com.mallsystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mallsystem.bean.SysUser;

@Mapper
public interface UserInfoMapper {
	
	// 查询登录用户名和密码等信息
	@Select("select * from sysuser")
	public SysUser login();
	
	// 更新密码
	@Update("update sysuser set sysPwd = #{sysPwd} where sysName = #{sysName}")
	public int updateCode(String sysName, String sysPwd);
	
	// 更改密保
	@Update("update sysuser set sysEncrypted = #{sysEnc}, sysAnswer = #{sysAnswer} where sysName = #{sysName}")
	public int updateEnc(String sysEnc, String sysAnswer, String sysName);
}
