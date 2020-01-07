 package com.simple.productInfo.controller.param;

import lombok.Data;

@Data
 public class FindSupplierOrderListParam {

     private Integer pageNum;
     
     private Integer pageSize;
     
     private String supplierName;
}
