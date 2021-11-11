package com.piedpiper.sec07.backpressure;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Sec07BackPressureObBackPressureLatest {
	
	public static void main(String[] args) {
		System.setProperty("reactor.bufferSize.small", "20");
		Flux.create(sink -> {
			for(int i = 1; i < 500; i++) {
				System.out.println("publishing: "+i);
				sink.next(i);
				Util.sleepMilliSeconds(1);
			}
			sink.complete();
		})
		.onBackpressureLatest()
		.publishOn(Schedulers.boundedElastic())
		.doOnNext(item ->{
			System.out.println("don on next: "+item);
			Util.sleepMilliSeconds(2);
		})
		.subscribe(Util.getDefaultSubscriber());
		//onBackpressureLatest is similar to onBackpressureDrop.
		//The key difference is that when the buffer is freed 75%
		//the item which gets published just previous to that event is the
		//the one that gets buffered. 
		
		//And also it should be noted that the last item which gets published 
		//will always be consumed----lets see why?
		//
		Util.sleepSeconds(7);
	}

}
