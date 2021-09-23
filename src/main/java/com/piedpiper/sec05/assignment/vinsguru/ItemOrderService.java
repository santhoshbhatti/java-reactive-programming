package com.piedpiper.sec05.assignment.vinsguru;

import java.time.Duration;
import java.util.Objects;

import reactor.core.publisher.Flux;

public class ItemOrderService {
	
	private Flux<ItemPurchaseOrder> flux;
	
	public Flux<ItemPurchaseOrder> orderStream(){
		if(Objects.isNull(flux)) {
			flux = getOrderStream();
		}
		return flux;
	}
	
	private Flux<ItemPurchaseOrder> getOrderStream(){
		
		return Flux.interval(Duration.ofMillis(100))
		.map(s -> new ItemPurchaseOrder())
		.publish()
		.refCount(2);
		
	}

}
