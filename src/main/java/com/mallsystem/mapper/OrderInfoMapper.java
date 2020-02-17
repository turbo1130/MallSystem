package com.mallsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mallsystem.entities.Orders;

/**
 * Created with Eclipse
 * @author heroC
 * @since JDK1.8
 * @version 1.0
 * Description: 该类实现了对订单信息的增删改查的相关处理
 */
@Mapper
public interface OrderInfoMapper {

	/**
	 * Description: 获取所有订单信息
	 * @return 返回Orders类型的List集合
	 */
	@Select("select * from orders")
	public List<Orders> getAllorders();
	
	/**
	 * Description: 获取指定页的订单信息
	 * @param pageNum
	 * @return 返回Orders类型的List集合
	 */
	@Select("select * from orders limit #{pageNum},15")
	public List<Orders> getOrdersByPage(int pageNum);
	
	/**
	 * 获取付款状态和发货状态的订单信息
	 * @param payStat
	 * @param expStat
	 * @return 返回Orders类型的List集合
	 */
	@Select("select * from orders where odPayStat like '${payStat}' and odExpStat like '${expStat}'")
	public List<Orders> getOrderStat(String payStat, String expStat);
	
	/**
	 * Description: 通过指定ID信息获取到订单信息
	 * @param id
	 * @return 返回Orders类型，获取的记录自动封装到Orders类中
	 */
	@Select("select * from orders where odID = #{id}")
	public Orders getOrderById(int id);
	
	/**
	 * Description: 通过指定odGood查询是否有该商品的订单
	 * @param odGood
	 * @return 返回int类型，表示受影响的记录条数
	 */
	@Select("select count(*) from orders where odGood like '${odGood}' ")
	public int getOrderCountByGood(String odGood);
	
	/**
	 * Description: 通过ID号更新订单信息
	 * @param order
	 * @return 返回int类型，表示受影响的记录条数
	 */
	@Update("update orders set odGood = #{odGood}, odCount = #{odCount}, odPayStat = #{odPayStat}, odExpStat = #{odExpStat}, odExpNum = #{odExpNum}, odTime = #{odTime} where odID = #{odID}")
	public int updateOrder(Orders order);
	
	/**
	 * Description: 添加订单信息
	 * @param order
	 * @return 返回int类型，表示受影响的记录条数
	 */
	@Insert("insert into orders(odCustomer,odGood,odCount,odPayStat,odExpStat,odExpNum,odTime) "
			+ "values( #{odCustomer},#{odGood},#{odCount},#{odPayStat},#{odExpStat},#{odExpNum},#{odTime})")
	public int addOrder(Orders order);
	
	/**
	 * Description: 通过ID删除信息
	 * @param odID
	 * @return 返回int类型，表示受影响的记录条数
	 */
	@Delete("delete from orders where odID = #{odID}")
	public int delOrder(int odID);
}
