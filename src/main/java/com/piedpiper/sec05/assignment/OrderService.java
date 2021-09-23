package com.piedpiper.sec05.assignment;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class OrderService implements Subscriber<Order>{
	private static OrderService orderService = null;
	private OrderStore orderStore;
	private ItemMaster itemMaster;
	private Subscription subscription;
	private OrderService() {
		itemMaster = ItemMaster.getItemMaster();
		orderStore = OrderStore.getOrderStore();
	}
	
	public static OrderService getOrderService() {
		if(orderService == null) {
			orderService = new OrderService();
		}
		
		return orderService;
	}
	
	public Flux<Order> getOrdersPublisher(){
		Integer numOrders =0;
		var itemSSNList = itemMaster.getValidItemSSN();
		Flux<Order> orders = Flux.generate(() -> numOrders,(orderCount,sink) -> {
			if(orderCount >= 100) {
				sink.complete();
			}
			Order order = getNextOrder(itemSSNList);
			sink.next(order);
			orderCount++;
			return orderCount;
		});
		orders = orders.delayElements(Duration.ofSeconds(1));
		return orders;
	}

	private Order getNextOrder(List<String> itemSSNList) {
		var count = Util.faker().random().nextInt(2, 10);
		Order order = new Order();
		List<Item> items = new ArrayList<>();
		for(int k =0; k< count; k++) {
			int random = Util.faker().random().nextInt(0, 99);
			items.add(itemMaster.getItemDB().get(itemSSNList.get(random)));
		}
		order.setItems(items);
		return order;
	}
	
	
	
	@Override
	public void onSubscribe(Subscription s) {
		System.out.println("subscription obtained !!!! "+s);
		this.subscription = s;
		subscription.request(Long.MAX_VALUE);
	}

	@Override
	public void onNext(Order order) {
		System.out.println("processing order in orderService >>>>>> : "+order);
		orderStore.saveOrder(order);
	}

	@Override
	public void onError(Throwable e) {
		System.out.println("Exception thrown : "+ e.getMessage());
	}

	@Override
	public void onComplete() {
		System.out.println("done processing pipeline");
		
	}

}
