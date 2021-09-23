package com.piedpiper.sec05.assignment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RevenueStore {
	
	private Map<String,Double> revenueDB;
	private static RevenueStore revenueStore = null;
	private RevenueStore() {
		revenueDB = new HashMap<>();
	}
	
	public static RevenueStore getRevenueStore() {
		if(revenueStore == null) {
			revenueStore = new RevenueStore();
		}
		return revenueStore;
	}
	
	public void saveRevenueDetails(Order order) {
		order.getItems().forEach(item -> {
			if(revenueDB.containsKey(item.getItemId())) {
				var totalPrice = revenueDB.get(item.getItemId()) + item.getPrice();
				revenueDB.put(item.getItemId(), totalPrice);
			}else {
				revenueDB.put(item.getItemId(), item.getPrice());
			}
		});
	}
	
	public Collection<Double> getRevenueList() {
		return revenueDB.values();
	}

}
