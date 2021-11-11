package com.piedpiper.sec09.batching;

import com.piedpiper.util.Util;

import lombok.Data;

@Data
public class PurchaseOrder {
	
	private String name;
	private double price;
	private String category;
	
	public PurchaseOrder() {
		super();
		
		this.name = Util.faker().commerce().productName();
		this.price = 
				Double.parseDouble(Util.faker().commerce().price(20.0, 120.0));
		this.category = Util.faker().commerce().department();
	}
	
	
	

}
