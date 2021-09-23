package com.piedpiper.sec04.operators;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Sec04ErrorOperator {

	public static void main(String[] args) {
		Flux.range(1, 10).log().map(num -> 10 / (5 - num)).subscribe(Util.getDefaultSubscriber());

		// this above flux causes error and the onError of the subscriber gets executed,
		// the cancel gets called
		// and the flow stops

		// If we want to push a default value on error we can do the below

		System.out
				.println("!!!!!!!!!!!!!!!!!!!!!!!!---------------------------------------------!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println();
		System.out.println();
		Flux.range(1, 10).log().map(num -> 10 / (5 - num)).onErrorReturn(-1).subscribe(Util.getDefaultSubscriber());

		// If we want to get value on error from a fall back publisher

		System.out
				.println("!!!!!!!!!!!!!!!!!!!!!!!!---------------------------------------------!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println();
		System.out.println();
		
		Flux.range(1, 10)
		.log()
		.map(num -> 10 / (5 - num))
		.onErrorResume(e -> Mono.just(Util.faker().random().nextInt(10, 50)))
		.subscribe(Util.getDefaultSubscriber());

		
		//All the above techniques to handle error will cancel the subscription 
		//once the error occurs. But if we want to continue the flow even after the error happens
		//we do the below
		
		//Here we log the erroed object "5" and continue the flow!!!!!!!
		
		System.out
		.println("!!!!!!!!!!!!!!!!!!!!!!!!---------------------------------------------!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println();
		System.out.println();
		
		Flux.range(1, 10)
		.log()
		.map(num -> 10 / (5 - num))
		.onErrorContinue((err,obj) -> System.out.println("errored object"+obj))
		.subscribe(Util.getDefaultSubscriber());
	}

}
