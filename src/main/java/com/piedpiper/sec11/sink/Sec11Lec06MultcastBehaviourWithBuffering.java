package com.piedpiper.sec11.sink;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class Sec11Lec06MultcastBehaviourWithBuffering {
	
	public static void main(String[] args) {
		Sinks.Many<String> sink = Sinks.many()
				.multicast()
				.onBackpressureBuffer();
		
		//This is hot publisher
		Flux<String> flux = sink.asFlux();
		
		
		sink.tryEmitNext("hi!!!");
		sink.tryEmitNext("hello");
		flux.subscribe(Util.getDefaultSubscriber("sam"));
		//Sam recieves all four items from the pipline
		//We see this is still true as the pipeline buffers the items 
		//emitted before any subscriber subscribes to the pipleine.
		//
		flux.subscribe(Util.getDefaultSubscriber("mike"));
		//Mike subscribers after some messages are already emitted
		//So he recieves messges which were sent after it subscribes to
		//pipeline....this is what you say nothing 
		//happens untill a subscriber subscribes to the pipeline
		sink.tryEmitNext("How are you?");
		sink.tryEmitNext("?");
	}

}
