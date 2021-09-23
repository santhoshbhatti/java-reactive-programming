package com.piedpiper.sec05.assignment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryService {
	
	private final OrderService orderService;
	
	private final Map<String,Item> items;
	
	List<String> itemSSN;
	private InventoryService() {
		this.orderService = OrderService.getOrderService();
		this.items = new HashMap<>();
		for(int k = 0;k < 100; k++) {
			Item item = new Item();
			
		}
		
	}
	
	
	
	
	

}
