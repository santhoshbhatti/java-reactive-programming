package com.piedpiper.sec11.sink;

import java.time.Duration;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class Sec11Lec07MulticastBehaviourDirectBestEffortDemo2 {
public static void main(String[] args) {
		
		System.setProperty("reactor.bufferSize.small", "16");
		Sinks.Many<Integer> sink = Sinks.many()
				.multicast()
				.directBestEffort();
		
		Flux<Integer> flux = sink.asFlux();
		flux.subscribe(Util.getDefaultSubscriber("sam"));
		
		flux.delayElements(Duration.ofMillis(200))
		.subscribe(Util.getDefaultSubscriber("mike"));
		
		for(int k = 0; k < 100; k++) {
			sink.tryEmitNext(k);
		}
		
		Util.sleepSeconds(20);
		
		//Here there are 2 subscribers to the pipeline and
		//One is slow and the other is fast.
		//Now since the buffer size is set to 16
		//directBestEffort() as opposed to directAllOrNothing
		//will push the elements to the fast subscriber and only
		//the elements that can be consumed by the slow subscriber
		//Read the docs of directBestEffort() for clarity
	}
}
