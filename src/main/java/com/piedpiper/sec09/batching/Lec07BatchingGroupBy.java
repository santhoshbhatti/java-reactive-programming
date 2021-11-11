package com.piedpiper.sec09.batching;

import java.time.Duration;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;

public class Lec07BatchingGroupBy {
	
	public static void main(String[] args) {
		
		Flux.range(1, 30)
		.delayElements(Duration.ofSeconds(1))
		.groupBy(i -> i % 2)
		.subscribe(gf -> processGroup(gf,gf.key()));
		
		Util.sleepSeconds(60);
	}

	private static void processGroup(GroupedFlux<Integer, Integer> gf, Integer key) {
		System.out.println("called process group for key: "+key);
		gf.subscribe(item -> System.out.println("key: "+key+" | item: "+item));
	}
	
	

}
