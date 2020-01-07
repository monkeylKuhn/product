package com.simple.productInfo.DTO;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 
 * @author mjl
 * @date 2020/01/07
 */
@Data
public class OrderDTO {

    private Long id;

    private String orderNo;

    private LocalDateTime submitTime;

    private String supplierName;

    private String sku;

    private String artNo;

    private String brand;

    private String color;

    private String size;

    private String season;

    private String price;

    private String retailPrice;

    private Integer orderStatus;
    
    private Integer supplierDeliveryStatus;
    
    private String supplierDeliveryNo;
    
    private String supplierBarCode;
    
    private LocalDateTime supplierTime;
    
    private String warehouseBarCode;
    
    private String quality;
    
    private Integer warehouseStatus;
    
    private String warehouseDeliveryNo;
    
    private String warehouseTime;
}
