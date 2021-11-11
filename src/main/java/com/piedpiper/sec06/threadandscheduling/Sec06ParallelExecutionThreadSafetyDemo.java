package com.piedpiper.sec06.threadandscheduling;

import java.util.ArrayList;
import java.util.List;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Sec06ParallelExecutionThreadSafetyDemo {
	
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>(); 
		Flux.range(0, 1000)
		.parallel()//Here the default number of rails will be number of cpus.
		//Parallelism can be adjusted by passing a int value to parallel()
		.runOn(Schedulers.parallel())
		.doOnNext(next -> System.out.println(next))
		.subscribe(next -> list.add(next));
		//in the above flux we are adding elements produced in 
		//into a ArrayList. Since these elements are processed in a parallel manner
		//and the ArrayList is not thread safe. 
		//We may see the size of the array is not always 1000
		//It is sometimes less
		//This is a case of race condition
		//Be careful to use thread-safe data structures if you are consuming
		Util.sleepSeconds(2);
		System.out.println("list size is :"+list.size());
	}

}
