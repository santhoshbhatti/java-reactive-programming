package com.piedpiper.sec05.assignment.vinsguru;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import reactor.core.publisher.Flux;

public class ItemsRevenueService {
	
	private Map<String, Double> db;

	public ItemsRevenueService() {
		super();
		this.db = new HashMap<>();
		this.db.put("Kids", 0.0);
		this.db.put("Automotive", 0.0);
	}
	
	public Consumer<ItemPurchaseOrder> subscribeOrderStream(){
		return (itemPo) -> {
			db.computeIfPresent(itemPo.getCatagory(), (key,val) -> val+itemPo.getPrice());
		};
	}
	
	public Flux<String> revenueStream(){
		return Flux.interval(Duration.ofSeconds(2))
		.map(s -> db.toString());
	}
	
	

}
