package com.piedpiper.sec07.backpressure;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Sec07OnBackpressureError {
	
	public static void main(String[] args) {
		Flux.create(sink ->{
			for(int k = 0;k < 500 && !sink.isCancelled(); k++) {
				System.out.println("item pushed: "+k);
				sink.next(k);
				Util.sleepMilliSeconds(2);
			}
			if(sink.isCancelled()) {
				sink.error(new RuntimeException("Producer overran subscriber"));
			}
			sink.complete();
		})
		.onBackpressureError()
		.publishOn(Schedulers.boundedElastic())
		.doOnNext(item -> {
			System.out.println("do on next: "+ item);
			Util.sleepMilliSeconds(3);
		})
		.subscribe(Util.getDefaultSubscriber());
	}

}
