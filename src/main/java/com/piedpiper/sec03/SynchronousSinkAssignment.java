package com.piedpiper.sec03;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class SynchronousSinkAssignment {
	public static void main(String[] args) {
		
		Flux.generate(syncSink -> {
			String country = Util.faker().country().name();
			System.out.println("Emit : "+country);
			syncSink.next(country);
			if (country.equalsIgnoreCase("canada")) {
				syncSink.complete();
			}
		})
		.take(10)
		.log()
		.subscribe(Util.getDefaultSubscriber());
	}

}
