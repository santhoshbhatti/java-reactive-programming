package com.piedpiper.sec07.backpressure;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Sec07DropStrategyForOverFlowSmallBufferSize {
	public static void main(String[] args) {
		System.setProperty("reactor.bufferSize.small", "20");
		Flux.create(fluxSink ->{
			for(int k=1;k<=500;k++) {
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
		
		
		
		//In this overflow strategy As we can see -----the buffer size maintained is
		//256 . even though the publisher keeps pushing more items when the buffer 
		//size reaches 256, it will stop buffering more items ----the buffer size can 
		//be increased or decreased upto 16 by setting an environment variable
		//reactor.bufferSize.small
		//I have set System.setProperty("reactor.bufferSize.small", "300");
		//the default max buffer size is 256 and min buffer size is 16----we cannot
		//set buffer size less than 16
		
		//Now we have set the buffer size as 16.....now the publisher keeps items and 
		//at some point of time the buffer size reaches 16. At this point even though
		//the publisher pushes items  and the consumer is too slow to consume any new items
		//the buffer will no longer accept anymore items, so the new items will get dropped.
		//Now when the consumer starts consuming new slots will open up 
		//for new items to be added.
		//But new items will not be added to the buffer until the buffer is 75 percent
		//empty. So the first item which gets published after the buffer is emptied 75 percent
		//will be the next item buffered and it will be the item the consumer will consume after
		//emptying the buffer!!!!!!!!!!!!
		Util.sleepSeconds(10);
	}
}
