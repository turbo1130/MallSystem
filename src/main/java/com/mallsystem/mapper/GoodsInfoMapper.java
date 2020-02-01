package com.mallsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mallsystem.bean.Goods;
import com.mallsystem.bean.GoodsName;

@Mapper
public interface GoodsInfoMapper {
	
	// 查询商品所有信息
	@Select("select * from goods")
	public List<Goods> getAllGoods();
	
	// 每页展示15条商品信息，如果第1页就是查询从行号0到15的商品信息,第2页就是查询从行号16到30的商品信息
	@Select("select * from goods limit #{goodsPageNum},15")
	public List<Goods> pageGoods(int goodsPageNum);
	
	// 查询指定id的商品信息
	@Select("select * from goods where gdID = #{id}")
	public Goods getGood(int id);
	
	// 通过商品名称查询商品信息
	@Select("select * from goods where gdName = #{name}")
	public Goods getGoodByName(String name);
	
	// 查询包含指定字符的商品信息
	@Select("select * from goods where ${col} like '%${content}%'")
	public List<Goods> getPassGoods(String col, String content);
	
	// 查询商品名称
	@Select("select distinct gdName from goods")
	public List<GoodsName> getGoodsName();
	
	// 删除指定id的商品信息
	@Delete("delete from goods where gdID = #{id}")
	public int DelGoodById(int id);
	
	// 添加商品信息
	@Insert("insert into goods(gdName, gdCount, gdPrice, gdClass, gdTime) values(#{gdName},#{gdCount},#{gdPrice},#{gdClass},#{gdTime})")
	public int AddGood(Goods good);
	
	// 更新指定商品信息
	@Update("update goods set gdName = #{gdName}, gdCount = #{gdCount}, gdPrice = #{gdPrice}, gdClass = #{gdClass}, gdTime = #{gdTime} where gdID = #{gdID}")
	public int UpdateGood(Goods good);
	
	// 更新库存数量
	@Update("update goods set gdCount = #{gdCount} where gdID = #{gdID}")
	public int updateGoodForCount(int gdID, int gdCount);
	
}
