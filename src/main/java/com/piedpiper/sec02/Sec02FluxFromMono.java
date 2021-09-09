package com.piedpiper.sec02;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Sec02FluxFromMono {
	public static void main(String[] args) {
		//suppose we want to pass a mono which accepts
		//a flux------we can create a flux from a mono
		
		Mono<String> mono = Mono.just("MonoToFlux");
		
		Flux<String> flux = Flux.from(mono);
		
		flux.subscribe(Util.onNext());
	}
}
