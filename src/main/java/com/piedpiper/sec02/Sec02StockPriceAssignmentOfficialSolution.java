package com.piedpiper.sec02;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec02StockPriceAssignmentOfficialSolution {
	
	private static final int BASE_PRICE = 100;
	
	private static final int MIN_PRICE = 90;
	
	private static final int MAX_PRICE = 110;
	
	public static void main(String[] args) throws InterruptedException {
		
		CountDownLatch latch = new CountDownLatch(1);
		getNextQuote().subscribeWith(new Subscriber<Integer>() {
			Subscription subscription;
			@Override
			public void onSubscribe(Subscription s) {
				this.subscription = s;
				System.out.println("got subscription >>>>>> "+subscription);
				subscription.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(Integer price) {
				System.out.println("stock price is "+LocalDateTime.now() +" : "+price);
				if (price < MIN_PRICE || price >MAX_PRICE) {
					System.out.println("ending flow as stock price is "+LocalDateTime.now() +" : "+price);
					this.subscription.cancel();
					latch.countDown();
				}
				
				
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onComplete() {
				System.out.println("flow completed!!!!!!");
				latch.countDown();
				
			}
		});
		latch.await();
	}
	
	private static Flux<Integer> getNextQuote(){
		AtomicInteger stockPrice = new AtomicInteger(BASE_PRICE);
		return Flux
				.interval(Duration.ofSeconds(1))
				.map(i -> stockPrice.getAndAccumulate(getStockQuote(), Integer::sum));
	}
	
	private static Integer getStockQuote() {
		int stockPrice = Util.faker().random().nextInt(-5, 5);
		return stockPrice;
	}

}
