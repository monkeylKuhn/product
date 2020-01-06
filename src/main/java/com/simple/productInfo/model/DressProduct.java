package com.simple.productInfo.model;

import java.util.List;

import lombok.Data;

@Data
public class DressProduct {
	
	private String productID;
	
	private String clientProductID;
	
	private String spu;
	
	private String sku;
	
	private String brand;
	
	private String name;
	
	private String description;
	
	private String genre;
	
	private String type;
	
	private String category;
	
	private String season;
	
	private boolean isCarryOver;
	
	private String color;
	
	private String retailPrice;
	
	private String price;
	
	private String pricesIncludeVat;
	
	private String productLastUpdated;
	
	private List<DressSkuSize> sizes;
	
	private List<String> photos;
		
	private Long id;
}