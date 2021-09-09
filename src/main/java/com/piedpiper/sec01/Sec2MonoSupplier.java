package com.piedpiper.sec01;

import com.piedpiper.util.Util;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class Sec2MonoSupplier {
	public static void main(String[] args) {
		//here the get mono is called eagrly --- just is eager
		var mono = Mono.just(getName());
		
		//the pipeline is not executed as there are no sunscribers
		var lazyMono = getMonoLazy("first");
		
		//now when we subscribe to this lazyMono we see the pipeline getting executed
		
		//Here the logic inside mono consumer is not executing in aynch mode as can be seen in the output
		//the last print stmt in main is printing after the consumer in mono waits for 3 sec and then runs
		//But in case of Asynch execution the main thread would have printed the last stmt and exited
		//more on this later
		
		//this is a blocking pipeline-------it runs on the same thread as the main thread and so it runs to
		//completion
		lazyMono.subscribe(Util.onNext());
		
		
		//here the main method exits and the business logic within the supplier of mono
		//is not even executed. More on this later
		var asynchMono = getMonoLazy("second");
		//this is a non blocking pipeline.....it runs on a seperate thread
		//a demon thread......so unless main thread is blocked it will fail to complete----it wont even be scheduled
		//as the demon thread will stop before main thread completes
		asynchMono.subscribeOn(Schedulers.boundedElastic())
		.subscribe(Util.onNext());
		
		//here we are blocking the main thread untill the Asynch task above executes
		//this not the correct way for server applications to block the thread....more on this later
		
		//if this is commented out the second pipeline or flow will not run to completion
		//as the second flow will is running on the demon thread which will stop before just main thread ends
		
		//the second flow or pipeline is scheduled to run a seperate thread on account of subscribeOn call on the mono
		//Util.sleepSeconds(4);
		System.out.println("Hello exiting!!!!!!");
	}
	
	public static String getName() {
		System.out.println("inside eager get name");
		return Util.faker().name().fullName().toUpperCase()+"_eager";
	}
	
	public static Mono<String> getMonoLazy(final String pipName) {
		System.out.println("inside lazy get name");
		var mono = Mono
				.fromSupplier(() -> {
					System.out.println("generating name !!!!!");
					Util.sleepSeconds(3);
					return Util.faker().funnyName().name()+"_lazy_"+pipName;
				})
				.map(String::toUpperCase);
		return mono;
	}
}
