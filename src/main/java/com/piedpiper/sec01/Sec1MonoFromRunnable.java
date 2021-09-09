package com.piedpiper.sec01;

import com.piedpiper.util.Util;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Sec1MonoFromRunnable {
	
	public static void main(String[] args) {
		//When we  want a time taking process to run and also
		//notify the pipeline we .....but not return any value
		//we use the mono from runnable
		
		//subscribeOn(Schedulers.boundedElastic()) is a call to make this pipeline asynchronous
		//if we remove this the pipeline executes in a blocking manner and first executes the pipeline and then
		//main method continues and prints the last print statement
		
		//after adding subscribeOn the pipeline wont even execute or scheduled . we might have to block
		//main thread till the pipeline executes
		Mono.fromRunnable(timeConsumingProcess()).subscribeOn(Schedulers.boundedElastic())
		.subscribe(Util.onNext(),Util.onError(),Util.onComplete());
		//used for blocking main thread for the pipeline to be scheduled
		Util.sleepSeconds(3);
		System.out.println("end of time taking process!!!!!!!");
	}
	
	public static Runnable timeConsumingProcess() {
		return () ->{
			Util.sleepSeconds(2);
			System.out.println("time taking process completed!!!!");
		};
	}

}
