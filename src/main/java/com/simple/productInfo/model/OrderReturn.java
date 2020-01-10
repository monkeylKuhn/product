 package com.simple.productInfo.model;

import lombok.Data;

@Data
 public class OrderReturn {

     private OrderStatus data;
     
     private OrderError error;
     
}
