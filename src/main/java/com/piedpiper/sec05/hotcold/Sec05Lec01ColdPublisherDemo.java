package com.piedpiper.sec05.hotcold;

import java.time.Duration;
import java.util.stream.Stream;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec05Lec01ColdPublisherDemo {
	
	public static void main(String[] args) {
		Flux<String> scenes=Flux
				.fromStream(() -> getMovieScenes())
				.delayElements(Duration.ofSeconds(1));
		
		scenes.subscribe(Util.getDefaultSubscriber("sam"));
		Util.sleepSeconds(5);
		scenes.subscribe(Util.getDefaultSubscriber("curran"));
		Util.sleepSeconds(30);
		
		/*
		 * This is the example of a cold publisher.
		 * The publisher emits data to the subscriber as and when the subscriber scubscribes to it
		 * Until the subscriber subscribes to the publisher nothing happens
		 * Both subscribers subscribe to the publisher and the publisher starts emitting to the subscriber
		 * from the begining. as we can see in this eaxample all scenes are streamed to both 
		 * subscribers.
		 * */
		
	}
	
	public static Stream<String> getMovieScenes(){
		return Stream.of(
					"scene 1",
					"scene 2",
					"scene 3",
					"scene 4",
					"scene 5",
					"scene 6",
					"scene 7"
				);
		
	}

}
