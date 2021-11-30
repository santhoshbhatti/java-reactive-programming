package com.piedpiper.sec10.repeatretry;

import com.piedpiper.util.Util;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

public class Section10RetrySpecDemo {
	
	public static void main(String[] args) {
		/*retry for certain exceptions and stop retrying
		 * when certain other exception happens-----very useful
		 * */
		orderService(Util.faker().business().creditCardNumber())
		.doOnError(err -> System.out.println(err.getMessage()))
		.retryWhen(Retry.from(
				flux -> flux.doOnNext(
						//flux is Flux<RetrySignal>
							rs ->{
								System.out.println("retries: "+rs.totalRetries());
								System.out.println("failure: "+rs.failure());
							}
						).handle((rs,synchronousSink) ->{
							if(rs.failure().getMessage().equals("500")) {
								synchronousSink.next(1);
							}else {
								synchronousSink.error(rs.failure());
							}
							
						})
		))
		.subscribe(Util.getDefaultSubscriber());
	}
	
	public static Mono<String> orderService(String ccNumber){
		return Mono.fromSupplier(() -> {
			processPayment(ccNumber);
			return Util.faker().idNumber().valid();
		});
	}
	
	public static void processPayment(String creditCardNumber) {
		int random = Util.faker().random().nextInt(1, 10);
		if(random < 8) {
			throw new RuntimeException("500");
		}else if(random < 10) {
			throw new RuntimeException("400");
		}
	}

}
