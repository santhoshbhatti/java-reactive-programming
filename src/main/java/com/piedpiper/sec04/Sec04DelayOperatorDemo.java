package com.piedpiper.sec04;

import java.time.Duration;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.util.concurrent.Queues;

public class Sec04DelayOperatorDemo {
	
	public static void main(String[] args) {
		
		//Here this producer produces 100 items 
		//subscriber makes a unbounded request
		//but the delayElements sits in the middle and emits elements with delay in 1 second
		
		//But the subscriber now is running aon a different thread and it exits even before it is scheduled
		//Hence we need to block the main thread which allows the scheduler to run the subscriber
		System.setProperty("reactor.bufferSize.x", "10");
		Flux.range(1, 100)
		.log()
		.delayElements(Duration.ofSeconds(1))
		.subscribe(Util.getDefaultSubscriber());
		
		//If we do not have the belo sleep, the delay operator requests for 32 items and emits them each every second
		//But since the subscriber is not scheduled at all it wont execute
		
		//we see request(32) and onNext on the operator 32 times---log()
		
		//So what are these 32 items ....it is the size of the recators buffer Queues reactor.bufferSize.x system property
		//so by setting this to different value we can change how many items get requested
		//by delay operator from producer
		
		//the property reactor.bufferSize.x has to be set to value above 8...elese it will default to 32
		
		//reactor.util.concurrent.Queues
		
		Util.sleepSeconds(200);
	}

}
