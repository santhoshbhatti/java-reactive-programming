package com.piedpiper.sec11.sink;

import com.piedpiper.util.Util;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class Sec11Lec01SinksOneDemo {
	
	public static void main(String[] args) {
		Sinks.One<String> sink = Sinks.one();
		
		Mono<String> mono = sink.asMono();
		
		//subscriber side
		mono.subscribe(Util.getDefaultSubscriber("sam"));
		
		//this below line starts the pipeline.
		//Sink as we see is a publisher as well as the subscriber.
		//Sinks are constructs through which Reactive Streams signals 
		//can be programmatically pushed.
		
		//publisher side
		sink.tryEmitValue("Hello world in sink!!!!!");
		
		//this will end the pipeline without the subscriber getting any value
		//as the complete signal is sent
		//sink.tryEmitEmpty();
		
		
		
	}

}

