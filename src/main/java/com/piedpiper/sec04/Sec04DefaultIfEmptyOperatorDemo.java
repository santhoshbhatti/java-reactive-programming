package com.piedpiper.sec04;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec04DefaultIfEmptyOperatorDemo {
	
	public static void main(String[] args) {
		
		//If we run this below pipeline
		//nothing comes out of it
		//As we are filtering out all the items
		Flux.range(1, 10)
		.filter(i -> i > 11)
		.subscribe(Util.getDefaultSubscriber());
		
		//Now we can provide default value to the subscriber if the flow is 
		// not emitting any items as follows
		
		Flux.range(1, 10)
		.filter(i -> i > 11)
		.defaultIfEmpty(-100)//since the flow does not produce any value the default value is
		//is emitted
		.subscribe(Util.getDefaultSubscriber());
	}

}
