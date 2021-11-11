package com.piedpiper.util;

import java.time.Duration;

import reactor.core.publisher.Flux;

public class Flights {
	
	public Flux<String> getFlights(String airline){
		String airlinePrefix = airline.substring(0, 1)
				.toUpperCase()+"A";
		int count=Util.faker().random().nextInt(700, 800);
		Flux<String> flights = Flux.create(
				sink -> {
					for(int k = 700; k <= count; k++) {
						sink.next(airlinePrefix+":"+k);
						
					}
					sink.complete();
				}
				).delayElements(Duration.ofMillis(50))
				.filter(str -> Util.faker().random().nextBoolean())
				.cast(String.class);
		return flights;
	}

}
