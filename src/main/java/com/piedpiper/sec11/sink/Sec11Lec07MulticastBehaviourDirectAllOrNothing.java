package com.piedpiper.sec11.sink;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class Sec11Lec07MulticastBehaviourDirectAllOrNothing {
	
	
	public static void main(String[] args) {
		Sinks.Many<String> sink = Sinks.many()
				.multicast()
				.directAllOrNothing();
		
		//Because of the above call to directAllOrNothing
		//Now anything emitted in the pipeline
		//before any subscription is not buffered
		//until the first subscriber arrives. They are thrown out.
		//So we see that sam and mike get "How are you ?" and "?" and
		//"new message"
		
		//and since bob subscribes later he only gets the "new message"
		Flux<String> flux = sink.asFlux();
		
		
		sink.tryEmitNext("hi!!!");
		sink.tryEmitNext("hello");
		flux.subscribe(Util.getDefaultSubscriber("sam"));
		
		flux.subscribe(Util.getDefaultSubscriber("mike"));
		
		sink.tryEmitNext("How are you?");
		sink.tryEmitNext("?");
		flux.subscribe(Util.getDefaultSubscriber("bob"));
		sink.tryEmitNext("new message");
	}

}
