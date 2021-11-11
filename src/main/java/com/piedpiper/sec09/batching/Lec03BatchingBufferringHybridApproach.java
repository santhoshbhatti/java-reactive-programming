package com.piedpiper.sec09.batching;

import java.time.Duration;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Lec03BatchingBufferringHybridApproach {
	
	public static void main(String[] args) {
		generateEvents()
		.bufferTimeout(6, Duration.ofSeconds(1))
		.subscribe(Util.getDefaultSubscriber());
		//this approach is hybrid approach
		//Here we have max buffer size and timeout intrerval
		//provided. If any of these exceed the batch is pushed to 
		//subscriber. When the publisher is fast we will
		//push a batch aof 6 items, if the publisher is slow 
		//we push the whatever number of items emitted by pubslisher
		//in the window of 2 seconds (lesser than or equal to
		//buffer size).
		Util.sleepSeconds(50);
		
	}
	
	public static Flux<String> generateEvents(){
		return Flux
				.interval(Duration.ofMillis(500))
				.map(i -> "event: "+i);
	}

}
