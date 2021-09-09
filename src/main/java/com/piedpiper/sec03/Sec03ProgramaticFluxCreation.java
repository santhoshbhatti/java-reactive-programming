package com.piedpiper.sec03;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec03ProgramaticFluxCreation {
	
	public static void main(String[] args) {
		//this is how flux can be constructed using Flux.create
		
		Flux.create(fluxSink ->{
			for(;;) {
				String country = Util.faker().country().name();
				fluxSink.next(country);
				if (country.equalsIgnoreCase("canada")) {
					break;
				}
			}
			
			fluxSink.complete();
		}).subscribe(Util.getDefaultSubscriber());
	}

}
