package com.piedpiper.sec11.sink;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class Sec11Lec05SinkManyMulticastDemo {
	
	public static void main(String[] args) {
		Sinks.Many<String> sinkMany = Sinks.many()
				.multicast()
				.onBackpressureBuffer();
		
		Flux<String> flux = sinkMany.asFlux();
		
		flux.subscribe(Util.getDefaultSubscriber("sam"));
		flux.subscribe(Util.getDefaultSubscriber("mike"));
		
		for(int k = 0; k < 10; k++) {
			sinkMany.tryEmitNext(Util.faker().funnyName().name());
		}
		
	}

}
