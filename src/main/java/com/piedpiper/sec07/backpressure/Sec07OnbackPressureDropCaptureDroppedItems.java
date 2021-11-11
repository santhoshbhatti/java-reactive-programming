package com.piedpiper.sec07.backpressure;

import java.util.ArrayList;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

public class Sec07OnbackPressureDropCaptureDroppedItems {
	
	public static void main(String[] args) {
		System.setProperty("reactor.bufferSize.small", "20");
		var list = new ArrayList<Object>();
		Flux.create(fluxSink -> {
			for(int k = 0;k < 200; k++) {
				System.out.println("publishing: "+k);
				fluxSink.next(k);
				Util.sleepMilliSeconds(5);
			}
			fluxSink.complete();
		})
		.onBackpressureDrop(list::add)//capturing items which were 
		//dropped for processing later...or for some other purposes.
		//Items dropped can be written to DB or a file 
		//for processing later
		.publishOn(Schedulers.boundedElastic())
		.doOnNext(item -> {
			System.out.println("do on next: "+item);
			Util.sleepMilliSeconds(10);
		})
		.subscribe(Util.getDefaultSubscriber());
		
		Util.sleepSeconds(10);
		System.out.println(list);
	}

}
