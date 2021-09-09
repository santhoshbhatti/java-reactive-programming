package com.piedpiper.sec02;

import java.time.Duration;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec02Interval {
	
	public static void main(String[] args) {
		Flux.interval(Duration.ofSeconds(1))
		.subscribe(Util.onNext(),Util.onError(),Util.onComplete());
		
		
		//the above pipeline does not execute as previous as ---interval
		//this pipeline runs on different thread from another thread pool
		//So we have to block main therad so that the the pipeline 
		//could be scheduled on----since the pipeline executes on a demon
		//thread it will not get scheduled if the main thread dies or completes
		
		//Hence blocking main thread
		Util.sleepSeconds(10);
		System.out.print("Done processing !!!!");
	}

}
