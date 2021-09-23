package com.piedpiper.sec05.assignment.vinsguru;

import com.piedpiper.util.Util;

import lombok.Data;

@Data
public class ItemPurchaseOrder {
	
	
	private String item;
	private double price;
	private String catagory;
	private int quantity;
	public ItemPurchaseOrder() {
		super();
		this.item = Util.faker().commerce().productName();
		this.price =Double.valueOf( Util.faker().commerce().price(12.0, 55.0));
		this.catagory = Util.faker().commerce().department();
		this.quantity = Util.faker().random().nextInt(1,10);
	}
	
	

}
