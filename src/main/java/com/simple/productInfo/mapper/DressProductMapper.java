 package com.simple.productInfo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.simple.productInfo.model.DressProduct;

public interface DressProductMapper {

     @Select({
         "<script>",
         
         "INSERT INTO DressProduct(productID,clientProductID,spu,sku)  ",
//         "<foreach collection='list' item='dressProduct' open='VALUES' close='' separator=','>", 
//         "values(#{dressProduct.productID},#{dressProduct.clientProductID},#{dressProduct.spu},#{dressProduct.sku})",

         "values(#{productID},#{clientProductID},#{spu},#{sku})",
//         "</foreach>", 
         
         "</script>"
     })
//     @Options(useGeneratedKeys = true, keyProperty = "id")
     int insertProduct(DressProduct list);

     @Select({
//         "select count(1) from DressProduct where  productID = '123'"
         
         "UPDATE  DressProduct SET sku = '123' where productID = '123'"
     })
    Integer select();
     
}
