package com.piedpiper.sec06.threadandscheduling;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Sec06MultiPublishOnDemo {
	
	public static void main(String[] args) {
		Flux<Integer> flux = Flux.<Integer>create(fluxSync -> {
			
			for (int k = 0; k < 10; k++) {
				printThreadName("pipeline creation: "+k);
				fluxSync.next(k);
				Util.sleepSeconds(1);
			}
			fluxSync.complete();
		})
				.doFirst(() -> printThreadName("first1"))
				.doOnNext(i -> printThreadName("nextElement1: "+i));


		//Here we are using publishOn
		//Now the publisher is running on the main thread 
		//as well as the first donOnNext as well as the 2 doFirst calls
		//but the moment the call to publishOn is encountered
		//the doOnNext and the subscriber are running on the bounded elastic
		//publishOn has no effect on doFirst calls as it happens before
		//publisher starts publishing
		
		//in this below we have second publishOn call
		//now since this is the nearest to the subscriber, the subscriber
		//will run on this schedulers thread pool
		
		//Remember this the publishOn is for the downstream
		//in the sense the operators downstream will be affected after the call
		//to publishOn in the sense, the operators downstream of produceOn
		//will be scheduled on the schedulaer's threadpool specified
		//by the publishOn call ---- the publishOn nearest to the operator
		//or the subscriber will be taken into account for scheduling that
		//operator or the subscriber
		
		//The subscribeOn is for the upstream.
		//In the sense when the subscribeOn call is encountered
		//All the operators and publishers upstream of that call are
		//executed on that subscribeOn's schedulars thread pool.
		//The subscribeOn nearest to the operator or the publishers is 
		//taken into account got schedulaing that operator ot the
		//publisher
		flux.doFirst(() -> printThreadName("first2"))
				.publishOn(Schedulers.boundedElastic())
				.doOnNext(i -> printThreadName("nextElement2: "+i))
				.publishOn(Schedulers.newParallel("pubon2",2,true))
				.doFirst(() -> printThreadName("first3"))
				.subscribe(i -> printThreadName("subscriber 1: "+i));
	}
	
	public static void printThreadName(String prefix) {
		System.out.println(prefix + " : " + Thread.currentThread().getName());
	}

}
