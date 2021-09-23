package com.piedpiper.sec05.hotcold;

import java.time.Duration;
import java.util.stream.Stream;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec05Lec05TrulyHotPublisherAutoConnectDemo {
	public static void main(String[] args) {

		var flux = Flux.<String>fromStream(() -> getMovieScenes())
				.delayElements(Duration.ofSeconds(1))
				.log()
				.publish()
				.autoConnect(0);
				//.share();
		
		//As can be seen in the output
		//Now the flux starts executing even when no subscriber exists
		//So at after point of time when the subscriber joins it will start getting elements 
		//starting from somewhere in the middle.
		
		//When the final subscriber joins ....all the elements in the publiser are exhausted and it 
		//does not get any elements passed to it.
	
		Util.sleepSeconds(4);
		flux.subscribe(Util.getDefaultSubscriber("El Camino"));
		Util.sleepSeconds(10);
		flux.log().subscribe(Util.getDefaultSubscriber("El Chapo"));
		Util.sleepSeconds(10);

	}

	public static Stream<String> getMovieScenes() {
		return Stream.of("scene 1", "scene 2", "scene 3", "scene 4", "scene 5", "scene 6", "scene 7");

	}
}
