package com.piedpiper.sec06.threadandscheduling;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Sec06MultipleSubscribeOnDemo {

	public static void main(String[] args) {
		
		//here we are calling subscribeOn twice
		//nearest to the publisher is .subscribeOn(Schedulers.newParallel("vinsguru"))
		//further below the pipeline is the  .subscribeOn(Schedulers.boundedElastic())
		
		//the schedular closed to the producer takes presidence here
		//the .doFirst(() -> printThreadName("first1")) will execute on
		//the current thread. And then the first 2 runs on bounded elastic
		//then the vinsguru schedular takes over---as it is closest to producer
		
		//Notes on subscribeOn
		//as we have seen that the subscribeOn closest to the producer
		//takes precidence ----So this is for a reason
		
		//the person who creates a flux---based on the computation
		//that is carried out in the flux needs to decide on which
		//schedular or thread pool the pipeline needs to execute
		//so he has to use subscribeOn with appropriate schedular
		//before passing it to any users of his/her API
		Flux<Integer> flux = Flux.<Integer>create(fluxSync -> {
			printThreadName("pipeline creation");
			fluxSync.next(1);
		})
				.subscribeOn(Schedulers.newParallel("vinsguru"))
				.doOnNext(i -> printThreadName("nextElement"));

		System.out.println("---------------- running pipeline on another thread -----");

		Runnable runnable = 
				() -> 
		       flux.doFirst(() -> printThreadName("first2"))
		       .subscribeOn(Schedulers.boundedElastic())
			   .doFirst(() -> printThreadName("first1"))
			   .subscribe(i -> printThreadName("subscriber 1"));

		for (int k = 0; k < 2; k++) {
			new Thread(runnable).start();
		}

		Util.sleepSeconds(10);
	}
	
	public static void printThreadName(String prefix) {
		System.out.println(prefix +" : "+Thread.currentThread().getName());
	}


}
