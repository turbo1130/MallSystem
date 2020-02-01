package com.mallsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.mallsystem.bean.LeaveAMsg;

@Mapper
public interface LeaveAMsgInfoMapper {

	// 查询所有的客户留言信息
	@Select("select * from leaveamsg")
	public List<LeaveAMsg> getAllMsg();
	
	// 通过客户名删除留言
	@Delete("delete from leaveamsg where msgCustomer = #{customer}")
	public int delMsg(String customer);
}
