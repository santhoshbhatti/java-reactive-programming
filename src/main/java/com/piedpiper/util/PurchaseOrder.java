package com.piedpiper.util;

import lombok.Data;

@Data
public class PurchaseOrder {
	
	private String id;
	private String userId;
	private String item;
	private String price;
	public PurchaseOrder(String id, String userId) {
		super();
		this.id = id;
		this.userId = userId;
		this.item = Util.faker().commerce().productName();
		this.price = Util.faker().commerce().price(12.0, 55.0);
	}
	
	

}
