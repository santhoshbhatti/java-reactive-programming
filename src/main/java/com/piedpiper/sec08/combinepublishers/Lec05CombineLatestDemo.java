package com.piedpiper.sec08.combinepublishers;

import java.time.Duration;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Lec05CombineLatestDemo {
	
	public static void main(String[] args) {
		Flux.combineLatest(getStringFlux(), getIntegerFlux(),
				(a,b) -> a+" : "+b)
		.subscribe(Util.getDefaultSubscriber());
		
		Util.sleepSeconds(10);
	}
	
	public static Flux<String> getStringFlux(){
		return Flux.just("Hello","world","reactive","programming")
		.delayElements(Duration.ofSeconds(1));
	}
	
	public static Flux<Integer> getIntegerFlux(){
		return Flux.just(1, 2, 3, 4, 5, 6, 7, 8)
		.delayElements(Duration.ofSeconds(3));
	}

}
