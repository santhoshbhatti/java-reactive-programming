package com.piedpiper.sec06.threadandscheduling;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Sec06ParallelExecutionOfFluxDemo {
	
	public static void main(String[] args) {
		
		//this is how we can achieve parallel execution of flux
		//Here we call parallel to inform the flux to divide the
		//the data into specified number of rails---in a round robin manner
		//Further we invoke runOn to provide the schedular on which
		//these rails are processed ----the thread pool on which the data in these 
		//raisls are processed
		Flux.range(1, 20)
		.parallel(4) //returns parallelflux
		.runOn(Schedulers.parallel()) //returns parallel flux
		.doOnNext(i -> printThreadName("next--"+i))
		.subscribe(i -> printThreadName("subscriber--"+i));
		
		Util.sleepSeconds(5);
		
	}
	
	public static void printThreadName(String prefix) {
		System.out.println(prefix+" : "+Thread.currentThread().getName());
	}

}
