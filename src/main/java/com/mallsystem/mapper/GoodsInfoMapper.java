package com.mallsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mallsystem.entities.Goods;
import com.mallsystem.entities.GoodsName;

/**
 * Created with Eclipse
 * @author heroC
 * @since JDK1.8
 * @version 1.0
 * Description: 该类实现了对商品增删改查的相关处理
 */
@Mapper
public interface GoodsInfoMapper {
	
	/**
	 * Description: 获取商品所有信息
	 * @return 返回为Goods类型的List集合，用于存储从数据库获取的每一条记录。
	 */
	@Select("select * from goods")
	public List<Goods> getAllGoods();
	
	/**
	 * Description: 每页展示15条商品信息，如果第1页就是查询从行号0到15的商品信息，
	 * 	第2页就是查询从行号16到30的商品信息
	 * @param goodsPageNum
	 * @return 返回的结果为Goods类型的List集合，用于存储通过分页查询获取的数据记录
	 */
	@Select("select * from goods limit #{goodsPageNum},15")
	public List<Goods> pageGoods(int goodsPageNum);
	
	/**
	 * Description: 获取指定id的商品信息
	 * @param id
	 * @return 返回的结果为Goods类型，自动将数据封装到Goods类型中
	 */
	@Select("select * from goods where gdID = #{id}")
	public Goods getGood(int id);
	
	/**
	 * Description: 通过商品名称查询商品信息
	 * @param name
	 * @return 返回的结果为Goods类型，自动将数据封装到Goods类型中
	 */
	@Select("select * from goods where gdName = #{name}")
	public Goods getGoodByName(String name);
	
	/**
	 * Description: 获取包含指定字符的商品信息
	 * @param col
	 * @param content
	 * @return 返回结果为Goods类型的List集合
	 */
	@Select("select * from goods where ${col} like '%${content}%'")
	public List<Goods> getPassGoods(String col, String content);
	
	/**
	 * Description: 获取不重复的商品名称
	 * @return 返回结果为GoodsName类型的List集合，将获取到的记录自动封装到GoodsName类中，然后一条一条存入List表中
	 */
	@Select("select distinct gdName from goods")
	public List<GoodsName> getGoodsName();
	
	/**
	 * Description: 删除指定id的商品信息
	 * @param id
	 * @return 返回结果为int类型，表示数据受影响条数
	 */
	@Delete("delete from goods where gdID = #{id}")
	public int DelGoodById(int id);
	
	/**
	 * Description: 添加商品信息
	 * @param good
	 * @return 返回结果为int类型，表示数据受影响条数
	 */
	@Insert("insert into goods(gdName, gdCount, gdPrice, gdClass, gdTime) values(#{gdName},#{gdCount},#{gdPrice},#{gdClass},#{gdTime})")
	public int AddGood(Goods good);
	
	/**
	 * Description: 获取指定商品信息
	 * @param good
	 * @return 返回结果为int类型，表示数据受影响条数
	 */
	@Update("update goods set gdName = #{gdName}, gdCount = #{gdCount}, gdPrice = #{gdPrice}, gdClass = #{gdClass}, gdTime = #{gdTime} where gdID = #{gdID}")
	public int UpdateGood(Goods good);
	
	/**
	 * Description: 获取库存数量
	 * @param gdID
	 * @param gdCount
	 * @return 返回结果为int类型，表示数据受影响条数
	 */
	@Update("update goods set gdCount = #{gdCount} where gdID = #{gdID}")
	public int updateGoodForCount(int gdID, int gdCount);
	
}
