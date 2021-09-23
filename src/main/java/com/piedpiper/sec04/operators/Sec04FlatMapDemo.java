package com.piedpiper.sec04.operators;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.piedpiper.util.PurchaseOrder;
import com.piedpiper.util.User;
import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec04FlatMapDemo {
	
	static final List<User> users = List.of(new User("1"), 
			new User("2"), 
			new User("3"), 
			new User("4"), 
			new User("5")); 
	
	static final Map<String,List<PurchaseOrder>> purchaseOrders;
	static {
		purchaseOrders = new HashMap<>() {
			{
				put("1", List.of(new PurchaseOrder("10", "1"),
						new PurchaseOrder("11", "1"),
						new PurchaseOrder("12", "1"),
						new PurchaseOrder("13", "1")));
				
				put("2", List.of(new PurchaseOrder("10", "2"),
						new PurchaseOrder("11", "2"),
						new PurchaseOrder("15", "2"),
						new PurchaseOrder("14", "2")));
				
				put("3", List.of(new PurchaseOrder("16", "3"),
						new PurchaseOrder("17", "3"),
						new PurchaseOrder("15", "3"),
						new PurchaseOrder("14", "3"),
						new PurchaseOrder("20", "3")));
				
				put("4", List.of(new PurchaseOrder("32", "4"),
						new PurchaseOrder("33", "4"),
						new PurchaseOrder("19", "4"),
						new PurchaseOrder("14", "4"),
						new PurchaseOrder("21", "4")));
				
				put("5", List.of(new PurchaseOrder("35", "5"),
						new PurchaseOrder("36", "5"),
						new PurchaseOrder("23", "5"),
						new PurchaseOrder("18", "5"),
						new PurchaseOrder("26", "5")));
				
				
			}
		};
	}
	public static Flux<User> getAllUsers(){
		var ui = users.iterator();
		Flux<User>  flux = Flux.generate(() -> ui,(iterator,sink)->{
			if (iterator.hasNext()) {
				sink.next(iterator.next());
			}else {
				sink.complete();
			}
			return iterator;
		});
		
		return flux;
	}
	
	public static Flux<PurchaseOrder> getUserPO(String userid){
		return Flux.<PurchaseOrder>create(sink -> {
			if(purchaseOrders.containsKey(userid)) {
				var orders = purchaseOrders.get(userid);
				orders.forEach(sink::next);
				sink.complete();
			}else {
				sink.error(new RuntimeException("No PO for the user"));
			}
			
		}).delayElements(Duration.ofSeconds(1));
	}
	
	public static void main(String[] args) {
		getAllUsers()
		.map(user -> getUserPO(user.getId()))
		.subscribe(Util.getDefaultSubscriber());
		
		//When we run this below each PO flux returned
		//will not run serially ....since we delay elements in the PO fluxes
		//So We get the output of all PO fluxes are mixed up
		
		getAllUsers()
		.flatMap(user -> getUserPO(user.getId()))
		.subscribe(Util.getDefaultSubscriber());
		
		System.out.println("concatMap for serilized execution of individual fluxes>>>>>>>>>>>>");
		getAllUsers()
		.concatMap(user -> getUserPO(user.getId()))
		.subscribe(Util.getDefaultSubscriber());
		Util.sleepSeconds(60);
	}

}
