package com.piedpiper.sec05.assignment.vinsguru;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import reactor.core.publisher.Flux;

public class ItemInventoryService {
	
	private Map<String, Integer> db;

	public ItemInventoryService() {
		super();
		this.db = new HashMap<>();
		this.db.put("Kids", 100);
		this.db.put("Automotive", 100);
	}
	
	public Consumer<ItemPurchaseOrder> subscribeOrderStream(){
		return (itemPo) -> {
			db.computeIfPresent(itemPo.getCatagory(), (key,val) -> val-itemPo.getQuantity());
		};
	}
	
	public Flux<String> inventoryStream(){
		return Flux.interval(Duration.ofSeconds(2))
		.map(s -> db.toString());
	}

}
