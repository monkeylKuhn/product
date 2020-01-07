 package com.simple.productInfo.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.simple.productInfo.model.DressSkuSize;
import com.simple.productInfo.model.DressStock;

public interface DressSkuMapper {

     @Insert({
         "<script>",
         "INSERT INTO dressskusize(productID,size,stock,retailPrice,price,createTime)  ",
         "<foreach collection='list' item='item' open='VALUES' close='' separator=','>", 
         "(#{item.productID},#{item.size},#{item.stock},#{item.retailPrice},#{item.price},#{item.createTime})",
         "</foreach>", 
         "</script>"
     })
     int batchInsert(List<DressSkuSize> list);
     
     @Insert({
         "<script>",
         "INSERT INTO dressskusize(productID,size,stock,retailPrice,price,status)  values",
         "(#{productID},#{size},#{stock},#{retailPrice},#{price},6)",
         "</script>"
     })
     int insert(DressStock dressStock);
     
     // 将所有数据设置为更新中状态
     @Update({
         "<script>",
         "UPDATE dressskusize SET status = #{status}",
         "</script>"
     })
     int updateStatus(int status);
     
     // 将所有数据设置为更新中状态
     @Update({
         "<script>",
         "UPDATE dressskusize SET status = #{status} WHERE id = #{id}",
         "</script>"
     })
     int updateStatusById(int status,Long id);
     
     
     @Update({
         "<script>",
         " UPDATE dressskusize ",
         "<set>",
         " <if test=\" stock != null \">", " stock = #{stock},", "</if>",
         " <if test=\" retailPrice != null \">", " retailPrice = #{retailPrice},", "</if>",
         " <if test=\" price != null \">", " price = #{price},", "</if>",
         " <if test=\" status != null \">", " status = #{status},", "</if>",
         "</set>",
         " WHERE id = #{id}",
         "</script>",
     })
     Integer update(DressSkuSize dressSkuSize);     
     
     @Select({
         "SELECT id,productID,size,stock,retailPrice,price FROM dressskusize WHERE productID = #{productId} and size = #{size}"
       })
     DressSkuSize selectByProductIDandSize(String productId,String size);
     
     @Select({
             "<script>",
             " SELECT ",
             "productID,size,stock",
             " FROM",
             "   dressskusize",
             " WHERE",
             "   purchaseOrderId=#{purchaseOrderId}",
             " and deleted=0",
             "<if test=\"items!=null and items.size()>0 \">",
             " and profitAndLossStatus in",
             "<foreach item=\"item\" index=\"index\" collection=\"items\" open=\"(\" separator=\",\" close=\")\">",
             "   #{item}",
             "</foreach>",
             "</if>",
             "</script>"

     })
     List<DressSkuSize> findPurchaseOrderItemProfitAndLossList(@Param("stock") Long purchaseOrderId, @Param("sList") Set<Integer> statusList);


}