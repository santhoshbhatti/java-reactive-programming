package com.piedpiper.sec09.batching;

import java.time.Duration;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Lec01BatchingByBufferringDemo {
	
	public static void main(String[] args) {
		generateEvents()
		.buffer(5)
		.subscribe(Util.getDefaultSubscriber());
		
		Util.sleepSeconds(30);
	}
	
	public static Flux<String> generateEvents(){
		return Flux
				.interval(Duration.ofMillis(300))
				.map(i -> "event: "+i);
	}

}
