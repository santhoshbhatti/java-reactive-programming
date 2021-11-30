package com.piedpiper.sec11.sink;

import com.piedpiper.util.Util;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class Sec11Lec02TryEmitError {
	
	public static void main(String[] args) {
		Sinks.One<String> sink = Sinks.one();
		
		Mono<String> mono = sink.asMono();
		
		mono.subscribe(Util.getDefaultSubscriber());
		
		sink.tryEmitError(new RuntimeException("error"));
	}

}
