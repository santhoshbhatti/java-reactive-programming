package com.piedpiper.sec09.batching;

import java.time.Duration;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Lec04BatchingOverlappingPatternDemo {
	
	public static void main(String[] args) {
		generateClickEvent()
		.buffer(3, 1)
		.subscribe(Util.getDefaultSubscriber());
		/*
		 * [event: 0, event: 1, event: 2] 
		   [event: 1, event: 2, event: 3] 
           [event: 2, event: 3, event: 4] 
           [event: 3, event: 4, event: 5] 
           [event: 4, event: 5, event: 6] 
           [event: 5, event: 6, event: 7]
		 * 
		 * As can be seen from output above , we skip one 
		 * oldest item in the previous batch and take 2 latest
		 * items along with new item from the batch.
		 * This can be used for detecting patterns in a stream.
		 * This enabled by buffer(maxSize,skip) method.
		 * 
		 * */
		Util.sleepSeconds(60);
	}
	
	public static Flux<String> generateClickEvent() {
		
		return Flux.interval(Duration.ofMillis(300))
		.map(i -> "event: "+i);
		
	}

}
