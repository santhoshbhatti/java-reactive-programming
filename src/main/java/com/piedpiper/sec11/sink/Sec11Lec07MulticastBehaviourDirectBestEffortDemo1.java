package com.piedpiper.sec11.sink;

import java.time.Duration;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class Sec11Lec07MulticastBehaviourDirectBestEffortDemo1 {
	
	public static void main(String[] args) {
		
		System.setProperty("reactor.bufferSize.small", "16");
		Sinks.Many<Integer> sink = Sinks.many()
				.multicast()
				.directAllOrNothing();
		
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
		//Untill all items are 75 percent items are drained
		//no new items will be added to the pipeline
		//So Any new item emitted by the pipeline will be discarded
		//Because of the slow subscriber we see that 
		//other subscribers which are fast will also be affected.
		//So we see that all items will not be pushed to
		//the subscribers.
		//directAllOrNothing is telling the pipeline
		//that either all subscribers are getting the values emitted
		//or none of the pubslishers will get the values
	}

}
