 package com.simple.productInfo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;

import com.simple.productInfo.model.DressSkuSize;

public interface DressSkuMapper {

     @Insert({
         "<script>",
         "INSERT INTO DressSku(spuId,size,stock,retailPrice,price,createTime)  ",
         "<foreach collection='list' item='item' open='VALUES' close='' separator=','>", 
         "(#{item.spuId},#{item.size},#{item.stock},#{item.retailPrice},#{item.price},#{item.createTime})",
         "</foreach>", 
         "</script>"
     })
     int batchInsert(List<DressSkuSize> list);

}