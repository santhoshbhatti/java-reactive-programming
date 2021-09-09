package com.piedpiper.sec01;

import com.piedpiper.util.Util;

import reactor.core.publisher.Mono;

public class Sec1MonoJust {
	public static void main(String[] args) {
		//mono.just is used when the num elemets are fixed
		//other ways of creating a mono is to use a supplier or a callable
		//just is eager in that the elemets in the pipeline are  realized at that point of time
		//where as mono by callable and supplier are lazy. Only when some on subscribes to the mono(pipeline)
		//the actual code in the supplier or callable will execute
		var mono = Mono.just("some string")
				.subscribe(Util.onNext(), 
						Util.onError(),
						Util.onComplete());
		
		
	}

}
