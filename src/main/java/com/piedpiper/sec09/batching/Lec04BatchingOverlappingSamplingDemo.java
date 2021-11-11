package com.piedpiper.sec09.batching;

import java.time.Duration;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Lec04BatchingOverlappingSamplingDemo {

	public static void main(String[] args) {
		generateClickEvent()
		.buffer(3, 5)
		.subscribe(Util.getDefaultSubscriber());
		
		Util.sleepSeconds(60);
		
		/*
		 * [event: 0, event: 1, event: 2]
           [event: 5, event: 6, event: 7]
           [event: 10, event: 11, event: 12]
           [event: 15, event: 16, event: 17]
           [event: 20, event: 21, event: 22]
           [event: 25, event: 26, event: 27]
           [event: 30, event: 31, event: 32] 
		 * 
		 * As can be seen from the output above
		 * the events are selected by skipping elements from middle.
		 * like from the first row we have events 1, 2 and 3
		 * the second batch skips 5 elements and picks events
		 * from 5, 6 and 7 and so on
		 * So this is sampling the elements in pipeline enabled
		 * by buffer(maxSize,skip) here we have given skip count
		 * greater than the maxSize. This is typical sampling behavior.
		 * 
		 * */
	}

	public static Flux<String> generateClickEvent() {

		return Flux.interval(Duration.ofMillis(300))
				.map(i -> "event: " + i);

	}

}
