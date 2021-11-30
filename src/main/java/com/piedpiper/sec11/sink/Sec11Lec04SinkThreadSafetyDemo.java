package com.piedpiper.sec11.sink;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class Sec11Lec04SinkThreadSafetyDemo {
	
	public static void main(String[] args) {
		
		List<Integer> list = new ArrayList<>();
		Sinks.Many<Integer> sink = Sinks.many().unicast().onBackpressureBuffer();
		
		Flux<Integer> flux= sink.asFlux();
		
		flux.subscribe(list::add);
		
		for(int k = 0;k < 1000; k++) {
			final int next = k;
			CompletableFuture.runAsync(() -> sink.emitNext(next,
					(signalType,result) -> {
						System.out.println("signal type: "+signalType);
						System.out.println("result: "+result);
						return true;
					}));
		}
		
		
		
		Util.sleepSeconds(5);
		
		System.out.println(list.size());
		
	}

}
