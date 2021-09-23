package com.piedpiper.sec06.threadandscheduling;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class Sec06SubscribeOnDemo {
	
	public static void main(String[] args) {
		
		//printing thread name on which the main is running
		
				printThreadName("main method");
				
				//this entire pipeline executes on the main thread
				//that is the thread on which the pipeline is created and subscribed to
				Flux<Integer> flux = Flux.<Integer>create(fluxSync ->{
					printThreadName("pipeline creation");
					fluxSync.next(1);
				})
			  .doOnNext(i -> printThreadName("nextElement"));
				
				flux
				.doFirst(() -> printThreadName("first2"))
				.subscribeOn(Schedulers.boundedElastic())
				.doFirst(() -> printThreadName("first1"))
				.subscribe(i -> printThreadName("subscriber 1"));
				
				Util.sleepSeconds(10);
				
				
				
				
			}

			
			public static void printThreadName(String prefix) {
				System.out.println(prefix +" : "+Thread.currentThread().getName());
			}


}
