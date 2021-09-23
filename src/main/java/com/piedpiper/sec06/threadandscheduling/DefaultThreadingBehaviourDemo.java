package com.piedpiper.sec06.threadandscheduling;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class DefaultThreadingBehaviourDemo {
	
	
	
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
		
		flux.subscribe(i -> printThreadName("subscriber 1"));
		
		
		//Now lets execute the same pipeline on a different thread as follows
		//We will see that the the pipeline will execute on the same thread on
		//which it will be subscribed on ....lets see
		
		Runnable runnable = () -> flux.subscribe(i -> printThreadName("runnable"));
		
		for(int k = 0; k < 2; k++) {
			new Thread(runnable).start();
		}
		
	}

	
	public static void printThreadName(String prefix) {
		System.out.println(prefix +" : "+Thread.currentThread().getName());
	}
}
