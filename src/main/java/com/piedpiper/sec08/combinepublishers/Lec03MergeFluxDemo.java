package com.piedpiper.sec08.combinepublishers;

import com.piedpiper.util.Flights;
import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Lec03MergeFluxDemo {
	
	public static void main(String[] args) {
		Flights flights = new Flights();
		
		Flux<String> flux= Flux
				.merge(flights.getFlights("Quatar"),
						flights.getFlights("American Airlines"),
						flights.getFlights("Indian Airlines"));
		
		flux.subscribe(Util.getDefaultSubscriber());
		
		Util.sleepSeconds(20);
	}

}
