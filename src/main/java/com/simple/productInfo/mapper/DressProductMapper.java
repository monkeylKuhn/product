 package com.simple.productInfo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.simple.productInfo.model.DressProduct;

public interface DressProductMapper {


     @Select({
       "SELECT COUNT(1) FROM DressProduct WHERE productID = #{productId}"
     })
    Integer count(String productId);
     
     
     @Update({
       "UPDATE  DressProduct SET ",
       "    spu = #{spu},",
       "    sku = #{sku}, ",
       "    brand = #{brand}, ",
       "    name = #{name}, ",
       "    description = #{description}, ",
       "    season = #{season}, ",
       "    isCarryOver = #{isCarryOver}, ",
       "    retailPrice = #{retailPrice}, ",
       "    price = #{price}, ",
       "    pricesIncludeVat = #{pricesIncludeVat}, ",
       "    productLastUpdated = #{productLastUpdated}, ",
       "    photos = #{photos} ",
       "WHERE productID = #{productID}"
     })
     Integer updateByProductID(DressProduct dressProduct);     
     
     @Insert({
         "<script>",
         "INSERT INTO DressProduct(productID,clientProductID,spu,sku,brand,name,description,genre,type,",
         "category,season,isCarryOver,color,retailPrice,price,pricesIncludeVat,productLastUpdated,photos,madeIn,composition)  ",
         "values(#{productID},#{clientProductID},#{spu},#{sku},#{brand},#{name},#{description},#{genre},#{type},",
         "#{category},#{season},#{isCarryOver},#{color},#{retailPrice},#{price},#{pricesIncludeVat},#{productLastUpdated},#{photos},#{madeIn},#{composition})",
         "</script>"
     })
     @Options(useGeneratedKeys = true, keyProperty = "id")
     int insert(DressProduct dressProduct);
}
