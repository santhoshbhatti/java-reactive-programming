package com.piedpiper.sec11.sink;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class Sec11Lec06MulticastBehaviour {
	
	public static void main(String[] args) {
		Sinks.Many<String> sink = Sinks.many()
				.multicast()
				.onBackpressureBuffer();
		
		//This is hot publisher
		Flux<String> flux = sink.asFlux();
		
		flux.subscribe(Util.getDefaultSubscriber("sam"));
		//Sam recieves all four items from the pipline
		sink.tryEmitNext("hi!!!");
		sink.tryEmitNext("hello");
		flux.subscribe(Util.getDefaultSubscriber("mike"));
		//Mike subscribers after some messages are already emitted
		//So he recieves messges which were sent after it subscribes to
		//pipeline
		sink.tryEmitNext("How are you?");
		sink.tryEmitNext("?");
		
		
	}

}
