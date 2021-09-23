package com.piedpiper.sec05.hotcold;

import java.time.Duration;
import java.util.stream.Stream;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec05Lec02HotPublisherDemo {
	
	public static void main(String[] args) {
		
		Flux<String> scenes = Flux
				.fromStream(getMovieScenes())
				.delayElements(Duration.ofSeconds(1))
				.share();
		
		//nothe how to make a cold publisher a hot publisher
		//by calling share() on it.
		//What the share does is that it will start streaming or broadcasting the elements
		//when the subscriber subscribes to it. But any subsequent subscriber which subscribes to the flux
		//wont get the elements whicha re already emitted....like a movie theater ...if we are late
		//we will get to see the movie from that scene that is is being broadcasted
		//as can be seen with these below two subscribers
		
		scenes.subscribe(Util.getDefaultSubscriber("sam"));
		
		Util.sleepSeconds(4);
		
		scenes.subscribe(Util.getDefaultSubscriber("curran"));
				
		Util.sleepSeconds(20);
		
		//as can be seen from the output the subscriber 1 has already subscribes and has received some scenes
		//after 4 minutes another subscriber subscribes and gets all the other scenes which have not already 
		//emitted. He gets every subsequent scenes along with the first subscriber.
		
		
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
