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
       "    productID = #{productID},",
       "    clientProductID = #{clientProductID},",
       "    spu = #{spu},",
       "    sku = #{sku}, ",
       "    brand = #{sku}, ",
       "    name = #{name}, ",
       "    description = #{description}, ",
       "    genre = #{genre}, ",
       "    type = #{type}, ",
       "    category = #{category}, ",
       "    season = #{season}, ",
       "    isCarryOver = #{isCarryOver}, ",
       "    color = #{color}, ",
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
         "category,season,isCarryOver,color,retailPrice,price,pricesIncludeVat,productLastUpdated,photos)  ",
         "values(#{productID},#{clientProductID},#{spu},#{sku},#{brand},#{name},#{description},#{genre},#{type},",
         "#{category},#{season},#{isCarryOver},#{color},#{retailPrice},#{price},#{pricesIncludeVat},#{productLastUpdated},#{photos})",
         "</script>"
     })
     @Options(useGeneratedKeys = true, keyProperty = "id")
     int insert(DressProduct dressProduct);
}
