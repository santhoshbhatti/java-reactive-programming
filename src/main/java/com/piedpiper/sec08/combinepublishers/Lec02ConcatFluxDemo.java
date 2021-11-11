package com.piedpiper.sec08.combinepublishers;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Lec02ConcatFluxDemo {
	
	public static void main(String[] args) {
		var flux1 = Flux.just("hello","world");
		var flux2 = Flux.just("in","reactive","programming");
		var flux3 = Flux.error(new RuntimeException("error !!!!"))
				.cast(String.class);
		
		//the stream ends with an error when the error
		//flux starts streaming
		var flux = flux1.concatWith(flux3).concatWith(flux2);
		flux.subscribe(Util.getDefaultSubscriber());
		
		//we can avoid this as below
		System.out.println("concat delay error !!!!!!!!>>>>>>>>");
		var fluxDelayError = Flux
				.concatDelayError(flux1,flux3,flux2);
				
		
		fluxDelayError.subscribe(Util.getDefaultSubscriber());
		
		
	}

}
