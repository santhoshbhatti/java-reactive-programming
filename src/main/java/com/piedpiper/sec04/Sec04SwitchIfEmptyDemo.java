package com.piedpiper.sec04;

import org.reactivestreams.Publisher;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec04SwitchIfEmptyDemo {
	
	public static void main(String[] args) {
		Flux.range(1, 10)
		.filter(i -> i > 11)
		.switchIfEmpty(getDefaultPublisher())
		.subscribe(Util.getDefaultSubscriber());
	}

	private static Flux<? extends Integer> getDefaultPublisher() {
		
		return Flux.range(20, 10);
	}

}
