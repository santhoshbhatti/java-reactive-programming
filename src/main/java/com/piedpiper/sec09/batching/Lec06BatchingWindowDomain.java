package com.piedpiper.sec09.batching;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Lec06BatchingWindowDomain {
	
	static AtomicInteger batch = new AtomicInteger(0);
	public static void main(String[] args) {
		generateEvents()
		.window(5)
		.flatMap(item -> processBatch(item))
		.subscribe(Util.getDefaultSubscriber());
		
		Util.sleepSeconds(100);
	}
	
	
	private static Mono<Integer> processBatch(Flux<String> flux) {
		
		return flux
				.doOnNext(string -> System.out.println("saving: "+string+" : "+Thread.currentThread().getName()))
				.doOnComplete(() -> System.out.println("completed processing"))
				.then(Mono.just(batch.incrementAndGet()));
	}


	public static Flux<String> generateEvents(){
		return Flux.interval(Duration.ofMillis(100))
				.map(i -> "event: "+i);
				
	}

}
