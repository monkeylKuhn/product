 package com.simple.productInfo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.simple.productInfo.DTO.ExportDTO;
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


     
     @Select({
         "SELECT ",
         " dp.productID , ",
         " dp.sku , ",
         " dp.brand , ",
         " dp.`name` , ",
         " dp.description , ",
         " dp.genre , ",
         " CONCAT(dp.productID,'_',ds.size) AS \"skuId\", ",
         " CONCAT(dp.genre,'+',dp.type) AS \"fristCategory\", ",
         " CONCAT(dp.genre,'+',dp.type,'+',dp.category) AS \"skuCategory\", ",
         " dp.madeIn , ",
         " dp.composition , ",
         " dp.season , ",
         " IF(dp.isCarryOver,\"是\",\"否\") AS \"isCarryOver\", ",
         " dp.color , ",
         " dp.retailPrice , ",
         " dp.price , ",
         " IF(dp.pricesIncludeVat,\"是\",\"否\") AS \"是否含税\", ",
         " dp.productLastUpdated , ",
         " ds.size , ",
         " ds.stock , ",
         " dp.photos  ",
         "FROM ",
         " DressProduct AS dp ",
         "RIGHT JOIN dressskusize AS ds ON dp.productID = ds.productID"
     })
     List<ExportDTO> export();
}
