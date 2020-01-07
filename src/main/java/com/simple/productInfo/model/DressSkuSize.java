package com.simple.productInfo.model;

import java.util.Date;

import lombok.Data;

@Data
public class DressSkuSize {
	
	private String productID;
	
	private String size;
	
	private String stock;
	
	private String retailPrice;
	
	private String price;
	
	private Date createTime;
}