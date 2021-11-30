package com.piedpiper.sec10.repeatretry;

import java.util.concurrent.atomic.AtomicInteger;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Section10RepeatOnCondition {
	private static final AtomicInteger count = new AtomicInteger(0);
	public static void main(String[] args) {
		//this will not stop it continues indefinetly
				//Note -> repeat is done only when the 
				//pipeline sends complete signal
				//It wont work for error signal
				Flux.range(1, 4)
				.doOnSubscribe(sub -> System.out.println("--subscribed"))
				.doOnComplete(() -> System.out.println("completed--"))
				//.map(i -> i/0) to show repeat wont work in case of error 
				//signal
				.map(i -> count.incrementAndGet())
				.repeat(() -> count.get() < 14)
				.subscribe(Util.getDefaultSubscriber());
				
				/*
				 * now from the out put we see
				 * even though we have set the condition as count < 14.
				 * This is because onece complete signal is sent,
				 * the subscriber subscribes again. And in the last run
				 * we have incremented the vlaue of count to 12 so
				 * in the next run it goes till 16 and then when
				 * subscriber subscribes again the repeat wont allow
				 * as the count has exceeded 14
				 * */
	}

}
