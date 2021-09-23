package com.piedpiper.sec05.assignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.piedpiper.util.Util;


import lombok.Data;
@Data
public class Order {
	
	private String orderId;
	private List<Item> items;
	
	public Order() {
		this.orderId = Util.faker().idNumber().valid();
	}

}
