 package com.simple.productInfo.controller.param;

import lombok.Data;

@Data
 public class WarehouseUpdateParam {

    private Long id;
    
    private String quality;
    
    private Integer status;
    
    private String deliveryNo;
}
