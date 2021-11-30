package com.piedpiper.sec11.sink;

import com.piedpiper.util.Util;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class Sec11Lec02MultiSubscribers {

	public static void main(String[] args) {
		Sinks.One<Object> sink = Sinks.one();

		Mono<Object> mono = sink.asMono();

		Mono<Object> mono1 = sink.asMono();
		
		mono.subscribe(Util.getDefaultSubscriber("sam"));
		
		mono.subscribe(Util.getDefaultSubscriber("mike"));
		
		sink.tryEmitValue("multiple subscribers hello!!!!");
		
		
	}

}
