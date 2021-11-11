package com.piedpiper.sec06.threadandscheduling;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Sec06PublishOnDemo {
	
	public static void main(String[] args) {
		Flux<Integer> flux = Flux.<Integer>create(fluxSync -> {
			printThreadName("pipeline creation");
			for (int k = 0; k < 10; k++) {
				fluxSync.next(k);
				Util.sleepSeconds(1);
			}
			fluxSync.complete();
		})
				.doFirst(() -> printThreadName("first1"))
				.doOnNext(i -> printThreadName("nextElement1: "+i));

		System.out.println("---------------- running pipeline on another thread -----");

		//Here we are using publishOn
		//Now the publisher is running on the main thread 
		//as well as the first donOnNext as well as the 2 doFirst calls
		//but the moment the call to publishOn is encountered
		//the doOnNext and the subscriber are running on the bounded elastic
		//publishOn has no effect on doFirst calls as it happens before
		//publisher starts publishing
		flux.doFirst(() -> printThreadName("first2"))
				.publishOn(Schedulers.boundedElastic())
				.doOnNext(i -> printThreadName("nextElement2: "+i))
				.doFirst(() -> printThreadName("first3"))
				.subscribe(i -> printThreadName("subscriber 1: "+i));
	}
	
	public static void printThreadName(String prefix) {
		System.out.println(prefix + " : " + Thread.currentThread().getName());
	}

}
