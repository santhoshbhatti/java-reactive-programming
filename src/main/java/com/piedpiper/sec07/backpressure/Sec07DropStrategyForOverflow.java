package com.piedpiper.sec07.backpressure;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Sec07DropStrategyForOverflow {
	public static void main(String[] args) {
		System.setProperty("reactor.bufferSize.small", "300");
		Flux.create(fluxSink ->{
			for(int k=0;k<500;k++) {
				System.out.println("pushing "+k+" : "+Thread.currentThread().getName());
				fluxSink.next(k);
				Util.sleepMilliSeconds(1);
			}
			fluxSink.complete();
		})
		.onBackpressureDrop()
		.publishOn(Schedulers.boundedElastic())
		.doOnNext(item -> {
			System.out.println("doOnNext: "+item+" : "+Thread.currentThread().getName());
			Util.sleepMilliSeconds(10);
		})
		.subscribe(Util.getDefaultSubscriber());
		
		//in the above code we see that the publisher is pushing elements
		//and the subscriber is is blocked for 10 milliseconds every time the 
		//item is pushed
		
		//Now the publisher after publishing a single element. The downstream 
		//thread is put to sleep for 10 milliseconds. The downstream consumer is 
		//scheduled to run a different thread-pool ----call to publishOn ---is for downstream
		//So since doOnNext call will send the thread to sleep----the publisher will have 
		//keep on publishing and since the consumer is not able to consume, 
		//the publisher will have to buffer the items in memory. This can cause the 
		//publisher to crash as more and more items are buffered due to slow consumer.
		
		//buffering is the default startegy for overflowing.
		//we are overriding the buffering overflow behaviour
		//by calling .onBackpressureDrop()
		
		//In this overflow strategy As we can see -----the buffer size maintained is
		//256 . even though the publisher keeps pushing more items when the buffer 
		//size reaches 256, it will stop buffering more items ----the buffer size can 
		//be increased or decreased upto 16 by setting an environment variable
		//reactor.bufferSize.small
		//I have set System.setProperty("reactor.bufferSize.small", "300");
		//the default max buffer size is 256 and min buffer size is 16----we cannot
		//set buffer size less than 16
		Util.sleepSeconds(10);
	}
}
