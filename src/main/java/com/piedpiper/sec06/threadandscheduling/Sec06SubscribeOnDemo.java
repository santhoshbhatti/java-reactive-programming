package com.piedpiper.sec06.threadandscheduling;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class Sec06SubscribeOnDemo {
	
	public static void main(String[] args) {
		
		//printing thread name on which the main is running
		
				printThreadName("main method");
				
				//Here we are subscribing on a different thread
				//as has been declared in subscribeOn-----Schedulers.boundedElastic()
				//Here we are subscribing on a different thread pool
				//called boundedElastic
				
				//doFirst(() -> printThreadName("first1")) will run on main thread
				//as do first nearest to subscriber executes first on main thread
				//and then we use subscribeOn on a schedular whit its own thread pool
				//then on wards another doFirst executes on a different thread 
				//as well as the entire pipeline
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
				
				Util.sleepSeconds(3);
				
				//setting up one more experiment by running the pipeline on another thread
				//Here we run the above flux on a user defined thread to see when the 
				//subscribeOn is called the pipeline is exeucuted in the boundedElastic
				//context and not the user defined pool
				
				System.out.println("---------------- running pipeline on another thread -----");
				
				Runnable runnable = () -> flux
						.doFirst(() -> printThreadName("first2"))
						.subscribeOn(Schedulers.boundedElastic())
						.doFirst(() -> printThreadName("first1"))
						.subscribe(i -> printThreadName("subscriber 1"));
				
				for(int k=0; k < 2; k++) {
					new Thread(runnable).start();
				}
				
				Util.sleepSeconds(10);
				
				
			}

			
			public static void printThreadName(String prefix) {
				System.out.println(prefix +" : "+Thread.currentThread().getName());
			}


}
