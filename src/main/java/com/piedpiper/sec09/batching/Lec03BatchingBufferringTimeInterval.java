package com.piedpiper.sec09.batching;

import java.time.Duration;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Lec03BatchingBufferringTimeInterval {
	
	
	public static void main(String[] args) {
		
		generateEvents()
		.buffer(Duration.ofSeconds(2))
		.subscribe(Util.getDefaultSubscriber());
		//all events arising in a duration of 
		//2 seconds are pushed to subscriber
		Util.sleepSeconds(20);
		
	}

	public static Flux<String> generateEvents(){
		return Flux
				.interval(Duration.ofMillis(300))
				.map(i -> "event: "+i);
	}
}
