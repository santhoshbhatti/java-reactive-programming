package com.piedpiper.sec05.hotcold;

import java.time.Duration;
import java.util.stream.Stream;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec05Lec04HotPublisherRefCountReSubscribe {
	
	public static void main(String[] args) {
		
		var flux = Flux.<String>fromStream(() -> getMovieScenes())
				.delayElements(Duration.ofSeconds(1))
				.share();
				//.publish()
				//.refCount(1);
				
				
				//this is the demo for the resubscribe
				//here the first publisher subscribes and the hot publisher
				//starts emitting immidiately as the ref count is 1
				//It will complete and only then the second subscriber subscribes as there is a sleep IN between 
				//Now when the second subscriber subscribes , The producer sees there is a single subscriber
				//as the first subscriber has unscubscribed after executing the stream.
				//So the hot publisher starts emitting the data from the begining.
				
				flux.subscribe(Util.getDefaultSubscriber("El Camino"));
				Util.sleepSeconds(10);
				flux.log().subscribe(Util.getDefaultSubscriber("El Chapo"));
				Util.sleepSeconds(10);
				
		
		
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
