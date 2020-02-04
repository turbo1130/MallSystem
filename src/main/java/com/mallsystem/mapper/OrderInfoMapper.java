package com.mallsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mallsystem.entities.Orders;

@Mapper
public interface OrderInfoMapper {

	// 查询所有订单信息
	@Select("select * from orders")
	public List<Orders> getAllorders();
	
	// 查询指定页的订单信息
	@Select("select * from orders limit #{pageNum},15")
	public List<Orders> getOrdersByPage(int pageNum);
	
	// 查询付款状态和发货状态的订单信息
	@Select("select * from orders where odPayStat like '${payStat}' and odExpStat like '${expStat}'")
	public List<Orders> getOrderStat(String payStat, String expStat);
	
	// 查询指定ID的订单信息
	@Select("select * from orders where odID = #{id}")
	public Orders getOrderById(int id);
	
	// 通过指定odGood查询是否有该商品的订单
	@Select("select count(*) from orders where odGood like '${odGood}' ")
	public int getOrderCountByGood(String odGood);
	
	// 通过ID号更新订单信息
	@Update("update orders set odGood = #{odGood}, odCount = #{odCount}, odPayStat = #{odPayStat}, odExpStat = #{odExpStat}, odExpNum = #{odExpNum}, odTime = #{odTime} where odID = #{odID}")
	public int updateOrder(Orders order);
	
	// 添加订单信息
	@Insert("insert into orders(odCustomer,odGood,odCount,odPayStat,odExpStat,odExpNum,odTime) "
			+ "values( #{odCustomer},#{odGood},#{odCount},#{odPayStat},#{odExpStat},#{odExpNum},#{odTime})")
	public int addOrder(Orders order);
	
	// 通过ID删除信息
	@Delete("delete from orders where odID = #{odID}")
	public int delOrder(int odID);
}
