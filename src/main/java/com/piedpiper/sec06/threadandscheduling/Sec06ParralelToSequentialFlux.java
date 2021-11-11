package com.piedpiper.sec06.threadandscheduling;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Sec06ParralelToSequentialFlux {
	
	public static void main(String[] args) {
		Flux.range(0, 100)
		.parallel() //the flux returned from here is ParallelFlux
		//So this wont have any publishOn ScheduleOn methods on it
		.runOn(Schedulers.parallel())
		//So now if we want to convert the ParallelFlux to sequentialflux call sequential
		.sequential() //from this point its is sequential flux
		.subscribe(next -> System.out.println(next));
		Util.sleepSeconds(5);
	}

}
