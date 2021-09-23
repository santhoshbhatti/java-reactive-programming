package com.piedpiper.sec05.assignment;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class OrderProcessor {
	
	public static void main(String[] args) {
		Flux<Order> orders = OrderService.getOrderService().getOrdersPublisher();
		
		orders
		.log()
		.subscribe(RevenueService.getRevenueService());
		
		
		
		orders
		.log()
		.subscribe(OrderService.getOrderService());
		
		Util.sleepSeconds(210);
		
		
		System.out.println(RevenueService.getRevenueService().totalRevenue());
		
		
	}
}
