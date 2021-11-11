package com.piedpiper.sec06.threadandscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Sec06ParallelExecutionThreadSafetyDSDemo {
	public static void main(String[] args) {
		List<Integer> list = Collections.synchronizedList(new ArrayList<>());
		Flux.range(0, 1000)
		.parallel()//5
		.runOn(Schedulers.parallel())
		.doOnNext(next -> printThreadName(next+" : "))
		.subscribe(next -> list.add(next));
		//in the above flux we are adding elements produced in 
		//into a ArrayList. Since these elements are processed in a parralel manner
		//and the ArrayList is not thread safe. 
		//We may see the size of the array is not always 1000
		//It is sometimes less
		//Be careful to use thread-safe datastructures if you are consuming
		
		//here we are using  a syncronized list implementation
		//So we will always see 1000 as the size of the list
		//
		Util.sleepSeconds(2);
		System.out.println("list size is :"+list.size());
	}

	public static void printThreadName(String prefix) {
		System.out.println(prefix+" : "+Thread.currentThread().getName());
	}
}
