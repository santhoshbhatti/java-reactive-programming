package com.piedpiper.sec06.threadandscheduling;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Sec06SubscribeOnThreadSwitchingDemo {

	public static void main(String[] args) {
		Flux<Integer> flux = Flux.<Integer>create(fluxSync -> {
			printThreadName("pipeline creation");
			for (int k = 0; k < 100; k++) {
				fluxSync.next(k);
				Util.sleepSeconds(1);
			}
			fluxSync.complete();
		}).doOnNext(i -> printThreadName("nextElement"));

		System.out.println("---------------- running pipeline on another thread -----");

		Runnable runnable =
				() -> flux.doFirst(() -> printThreadName("first2"))
				.subscribeOn(Schedulers.boundedElastic())
				.doFirst(() -> printThreadName("first1"))
				.subscribe(i -> printThreadName("subscriber 1: "+i));

		for(int k = 0;k < 5 ;k++)
			new Thread(runnable).start();
		

		//here we have 5 subscribers to a cold publisher
		//The publishers will publish and the subscriber has subscribed on a 
		//Scheduler Schedulers.boundedElastic()----each of 5 subscribers are
		//subscribed to cold publisher on boundedElastic scheduler
		//So what this means is each of these 5 schedulers is serviced by 5 
		//individual threads in the boundedElastic's thread pool.
		//As can be seen in the output ---if we track a single pipeline, 
		//it is always executed by the same thread in the thread pool---there is 
		//no switching of threads happening. 
		//A single pipeline is executed by a single thread----Since this is a cold
		//publisher each time a subscriber is subscribed to it a new pipeline
		//is created
		Util.sleepSeconds(210);
	}

	public static void printThreadName(String prefix) {
		System.out.println(prefix + " : " + Thread.currentThread().getName());
	}
}
