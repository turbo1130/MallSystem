package com.mallsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.mallsystem.entities.LeaveAMsg;

/**
 * Created with Eclipse
 * @author heroC
 * @since JDK1.8
 * @version 1.0
 * Description: 该类实现了对用户信息反馈的增删改查的相关处理
 */
@Mapper
public interface LeaveAMsgInfoMapper {

	/**
	 * Description: 获取所有的客户留言信息
	 * @return 返回结果为LeaveAMsg类型的List集合，将获取的记录自动封装到LeaveAMsg类中
	 */
	@Select("select * from leaveamsg")
	public List<LeaveAMsg> getAllMsg();
	
	/**
	 * Description: 通过客户名删除留言
	 * @param customer
	 * @return 返回结果为int类型，表示数据受影响条数
	 */
	@Delete("delete from leaveamsg where msgCustomer = #{customer}")
	public int delMsg(String customer);
}
