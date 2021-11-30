package com.piedpiper.sec11.sink;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class Sec11Lec07MulticastSinkReply {
	
	public static void main(String[] args) {
		Sinks.Many<String> sink = Sinks
				.many()
				.replay()
				.all();
				
		
		//This is hot publisher
		Flux<String> flux = sink.asFlux();
		
		
		sink.tryEmitNext("hi!!!");
		sink.tryEmitNext("hello");
		flux.subscribe(Util.getDefaultSubscriber("sam"));
		
		flux.subscribe(Util.getDefaultSubscriber("mike"));
		
		sink.tryEmitNext("How are you?");
		sink.tryEmitNext("?");
		
		flux.subscribe(Util.getDefaultSubscriber("bob"));
		
		sink.tryEmitNext("New message");
	}

}
