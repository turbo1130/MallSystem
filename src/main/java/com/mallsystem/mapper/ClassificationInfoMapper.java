package com.mallsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mallsystem.entities.Goods;
import com.mallsystem.entities.GoodsClass;

/**
 * Created with Eclipse
 * @author heroC
 * @since JDK1.8
 * @version 1.0
 * Description: 该类实现了对类别增删改查的相关处理
 */

@Mapper
public interface ClassificationInfoMapper {
	
	/**
	 * Description: 获取指定类别的商品信息
	 * @param content
	 * @return 返回的是一个Goods类型的List集合，用于存储查询到的所有符合要求的商品信息
	 */
	@Select("select * from goods where gdClass like '%${content}%'")
	public List<Goods> getGoodsByClass(String content);
	
	/**
	 * Description: 获取所有类
	 * @return 返回的是不带重复的商品类别数据信息，存储在GoodsClass类型的列表集合中
	 */
	@Select("select distinct gdClass from goods")
	public List<GoodsClass> getGoodsClass();
	
	/**
	 * Description: 修改类别
	 * @param good
	 * @return 返回的结果为int类型，用于反应数据库是否成功更新数据
	 */
	@Update("update goods set gdClass = #{gdClass} where gdID = #{gdID}")
	public int updateGoodByClass(Goods good);
}
