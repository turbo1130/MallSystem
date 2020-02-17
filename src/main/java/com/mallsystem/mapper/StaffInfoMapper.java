package com.mallsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mallsystem.entities.Staff;

/**
 * Created with Eclipse
 * @author heroC
 * @since JDK1.8
 * @version 1.0
 * Description: 该类实现了对员工信息的增删改查的相关处理
 */
@Mapper
public interface StaffInfoMapper {

	/**
	 * Description: 获取员工权限信息
	 * @return 返回Staff类型的List集合
	 */
	@Select("select * from staff")
	public List<Staff> getAllStaff();
	
	/**
	 * Description: 获取指定员工信息通过id
	 * @param id
	 * @return 返回Staff类型，获取的数据将自动封装到Staff类中
	 */
	@Select("select * from staff where staffID = #{id}")
	public Staff getStaffByID(int id);
	
	/**
	 * Description: 获取指定员工信息通过登录名
	 * @param loginName
	 * @return 返回Staff类型，获取的数据将自动封装到Staff类中
	 */
	@Select("select * from staff where loginName = #{loginName}")
	public Staff getStaffByLoginName(String loginName);
	
	/**
	 * Description: 修改员工权限信息
	 * @param staff
	 * @return 返回int类型，表示记录受影响的条数
	 */
	@Update("update staff set staffName = #{staffName}, loginName = #{loginName}, addOp = #{addOp}, delOp = #{delOp}, editOp = #{editOp} where staffID = #{staffID}")
	public int updateStaff(Staff staff);
	
	/**
	 * Description: 新增员工
	 * @param staff
	 * @return 返回int类型，表示记录受影响的条数
	 */
	@Insert("insert into staff(staffName, loginName, addOp, delOp, editOp) values(#{staffName},#{loginName},#{addOp},#{delOp},#{editOp})")
	public int addStaff(Staff staff);
	
	/**
	 * Description: 删除员工
	 * @param id
	 * @return 返回int类型，表示记录受影响的条数
	 */
	@Delete("delete from staff where staffID = #{id}")
	public int delStaff(int id);
}
