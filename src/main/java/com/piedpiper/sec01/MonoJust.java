package com.piedpiper.sec01;

import java.util.function.Consumer;

import reactor.core.publisher.Mono;

public class MonoJust {
	
	public static void main(String[] args) {
		var mono = Mono.just(10);
		mono.subscribe(System.out::println);
				
	}

}
