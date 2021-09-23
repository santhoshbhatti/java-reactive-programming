package com.piedpiper.sec05.assignment;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class RevenueService implements Subscriber<Order>{
	
	private static RevenueService revenueService;
	private RevenueStore revenueStore;
	private Subscription subscription;
	private  RevenueService() {
		this.revenueStore = RevenueStore.getRevenueStore();
				
	}
	public static RevenueService getRevenueService() {
		if(revenueService == null) {
			revenueService = new RevenueService();
		}
		return revenueService;
	}
	@Override
	public void onSubscribe(Subscription s) {
		System.out.println("revenue subscription obtained !!!! "+s);
		this.subscription = s;
		subscription.request(Long.MAX_VALUE);
	}

	@Override
	public void onNext(Order order) {
		System.out.println("processing order in revenueService >>>>>> : "+order);
		revenueStore.saveRevenueDetails(order);
	}

	@Override
	public void onError(Throwable e) {
		System.out.println("revenue Exception thrown : "+ e.getMessage());
	}

	@Override
	public void onComplete() {
		System.out.println("done processing revenue pipeline");
		
	}
	
	public Double totalRevenue() {
		var revenueOpt = revenueStore.getRevenueList().stream().reduce((a,b) -> a+b);
		return revenueOpt.isPresent()? revenueOpt.get():0.0;
	}

}
