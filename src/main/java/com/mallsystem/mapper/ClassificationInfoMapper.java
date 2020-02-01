package com.mallsystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mallsystem.bean.Goods;

@Mapper
public interface ClassificationInfoMapper {
	
	// 查询指定类别的商品信息
	@Select("select * from goods where gdClass like '${content}'")
	public List<Goods> getGoodsByClass(String content);
	
	// 修改类别
	@Update("update goods set gdClass = #{gdClass} where gdID = #{gdID}")
	public int updateGoodByClass(Goods good);
}
