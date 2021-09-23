package com.piedpiper.sec05.assignment;

import com.piedpiper.util.Util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Item {
	
	private String itemId;
	private String productName;
	private double price;
	
	public Item(String itemId) {
		this.itemId = itemId;
		this.productName = Util.faker().commerce().productName();
		this.price = Double.parseDouble(Util.faker().commerce().price(10.0, 200.0));
	}
	
	public Item() {
		this.itemId = Util.faker().idNumber().ssnValid();
		this.productName = Util.faker().commerce().productName();
		this.price = Double.parseDouble(Util.faker().commerce().price(10.0, 200.0));
	}
	
	

}
