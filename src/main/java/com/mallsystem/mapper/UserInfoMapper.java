package com.mallsystem.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mallsystem.entities.SysUser;

/**
 * Created with Eclipse
 * @author 刘志远
 * @since JDK1.8
 * @version 1.0
 * Description: 该类实现了对用户账号信息的增删改查的相关处理
 */
@Mapper
public interface UserInfoMapper {
	
	/**
	 * Description: 获取登录用户名等信息
	 * @param sysName
	 * @return 返回SysUser类型
	 */
	@Select("select * from sysuser where sysName = #{sysName}")
	public SysUser login(String sysName);
	
	/**
	 * Description: 获取指定登录用户名和密码等信息
	 * @param sysName
	 * @return 返回SysUser类型
	 */
	@Select("select * from sysuser where sysName = #{sysName}")
	public SysUser findCode(String sysName);
	
	/**
	 * Description: 通过登录名插入一条新的用户信息
	 * @param sysName
	 * @param sysAnswer
	 * @return  返回int类型，表示记录受影响的条数
	 */
	@Insert("insert into sysuser(sysName,sysPwd,sysEncrypted,sysAnswer) values(#{sysName},'123456','您的名字是什么？',#{sysAnswer})")
	public int addSysUser(String sysName, String sysAnswer);
	
	/**
	 * Description: 更新密码
	 * @param sysName
	 * @param sysPwd
	 * @return 返回int类型，表示记录受影响的条数
	 */
	@Update("update sysuser set sysPwd = #{sysPwd} where sysName = #{sysName}")
	public int updateCode(String sysName, String sysPwd);
	
	/**
	 * Description: 更改密保
	 * @param sysEnc
	 * @param sysAnswer
	 * @param sysName
	 * @return 返回int类型，表示记录受影响的条数
	 */
	@Update("update sysuser set sysEncrypted = #{sysEnc}, sysAnswer = #{sysAnswer} where sysName = #{sysName}")
	public int updateEnc(String sysEnc, String sysAnswer, String sysName);
	
	@Delete("delete from sysuser where sysName = #{sysName}")
	public int delUser(String sysName);
	
}
