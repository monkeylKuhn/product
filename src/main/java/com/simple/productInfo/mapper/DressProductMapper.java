 package com.simple.productInfo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
       "SELECT COUNT(1) FROM DressProduct WHERE productID = #{productId}"
     })
    Integer count(String productId);
     
     
     @Update({
       "UPDATE  DressProduct SET sku = '123' WHERE productID = #{productID}"
     })
     Integer updateByProductID(DressProduct dressProduct);     
     
     @Insert({
         "<script>",
         "INSERT INTO DressProduct(productID,clientProductID,spu,sku,brand,name,description,genre,type,",
         "category,season,isCarryOver,color,retailPrice,price,pricesIncludeVat,productLastUpdated,photos)  ",
         "values(#{productID},#{clientProductID},#{spu},#{sku},#{brand},#{name},#{description},#{genre},#{type},",
         "#{category},#{season},#{isCarryOver},#{color},#{retailPrice},#{price},#{pricesIncludeVat},#{productLastUpdated},#{photos})",
         "</script>"
     })
     @Options(useGeneratedKeys = true, keyProperty = "id")
     int insert(DressProduct dressProduct);
}
