package com.piedpiper.sec02;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec02StockPrizeAssignment {
	
	private static final int BASE_PRICE = 100;
	
	private static final int MIN_PRICE = 90;
	
	private static final int MAX_PRICE = 110;
	
	public static void main(String[] args) {
		AtomicInteger currentPrice = new AtomicInteger(BASE_PRICE);
		getInfiniteQuotes().subscribe(
				new Subscriber<Integer>() {
					AtomicReference<Subscription> subscription = new AtomicReference<>();
					
					@Override
					public void onSubscribe(Subscription s) {
						System.out.println("got subscription"+s);
						this.subscription.set(s);
						subscription.get().request(1);
						
					}

					@Override
					public void onNext(Integer quote) {
						System.out.println("got next quote "+quote);
						if (currentPrice.get()+quote > MIN_PRICE 
								&& currentPrice.get()+quote < MAX_PRICE) {
							currentPrice.addAndGet(quote);
							
						} else {
							currentPrice.addAndGet(quote);
							subscription.get().cancel();
						}
						System.out.println("stock price is "+currentPrice.get());
						subscription.get().request(1);
						
					}

					@Override
					public void onError(Throwable t) {
						System.out.println("error "+t.getMessage());
						
					}

					@Override
					public void onComplete() {
						System.out.println("done!!!!!!");
						
					}
					
				}
				
				);
		
		System.out.println("Done as stock price is "+ currentPrice.get());
		
	}
	
	private static Flux<Integer> getInfiniteQuotes(){
		return Flux
		.range(0, Integer.MAX_VALUE)
		.map(index -> getStockQuote());
	}
	
	private static Integer getStockQuote() {
		int stockPrice = Util.faker().random().nextInt(-5, 5);
		Util.sleepSeconds(1);
		return stockPrice;
	}

}
