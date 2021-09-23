package com.piedpiper.sec05.assignment;

import java.util.HashMap;
import java.util.Map;

public class OrderStore {
	
	private static  OrderStore orderStore;
	private final Map<String,Order> orderDB;
	private OrderStore() {
		orderDB = new HashMap<>();
	}
	
	public static OrderStore getOrderStore() {
		if(orderStore == null) {
			orderStore = new OrderStore();
		}
		
		return orderStore;
	}
	
	public double saveOrder(Order order) {
		this.orderDB.put(order.getOrderId(), order);
		return order.getItems().stream().map(Item::getPrice).reduce(0.0, (a,b) -> a+b);
	}
}
