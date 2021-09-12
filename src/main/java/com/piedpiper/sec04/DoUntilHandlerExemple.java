package com.piedpiper.sec04;

import com.github.javafaker.Country;
import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class DoUntilHandlerExemple {
	
	public static void main(String[] args) {
		Flux.<String>generate((sink) ->{
			String country = Util.faker().country().name();
			sink.next(country);
		}).handle((country,sink) ->{
			sink.next(country);
			if (country.equalsIgnoreCase("india")) {
				
				sink.complete();
			}
		}).subscribe(Util.getDefaultSubscriber()	);
	}

}
