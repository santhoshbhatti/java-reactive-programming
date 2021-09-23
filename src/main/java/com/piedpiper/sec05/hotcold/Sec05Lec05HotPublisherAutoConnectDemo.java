package com.piedpiper.sec05.hotcold;

import java.time.Duration;
import java.util.stream.Stream;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec05Lec05HotPublisherAutoConnectDemo {

	public static void main(String[] args) {

		var flux = Flux.<String>fromStream(() -> getMovieScenes())
				.delayElements(Duration.ofSeconds(1))
				.publish()
				.autoConnect(1);
				//.share();
		

		//The autoConnect displays true hot publisher behaviour
		//in that now when the first subscriber joins and 
		//executes the pipe line and exhausts it----ie.. completes the flow and exits
		//and then if the second subscriber joins ----the second subscriber does not
		//get any elemets sent to it
		
		//----A one more degree hot----but as we can see to execute the flow
		//atleast one subscriber is needed
		
		//But as defined before the truely hot publisher does not even need a subscriber to
		//execute
		
		//Look at the next example for truly hot publisher

		flux.subscribe(Util.getDefaultSubscriber("El Camino"));
		Util.sleepSeconds(10);
		flux.log().subscribe(Util.getDefaultSubscriber("El Chapo"));
		Util.sleepSeconds(10);

	}

	public static Stream<String> getMovieScenes() {
		return Stream.of("scene 1", "scene 2", "scene 3", "scene 4", "scene 5", "scene 6", "scene 7");

	}

}
