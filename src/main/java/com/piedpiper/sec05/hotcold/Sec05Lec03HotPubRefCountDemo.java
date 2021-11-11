package com.piedpiper.sec05.hotcold;

import java.time.Duration;
import java.util.stream.Stream;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec05Lec03HotPubRefCountDemo {
	
	public static void main(String[] args) {
		
		var flux = Flux.<String>fromStream(getMovieScenes())
		.delayElements(Duration.ofSeconds(1))
		//.share()
		.publish() //ConnectableFlux
		.refCount(2);
		
		
		//In the above share() ----making cold publisher hot
		//is replaced by publish() and refCount() -----this is what happens inside share
		//but if we want to customize the behaviour of hot flux we need to call publish and 
		//refCount seperately. 
		
		//Here we are trying to increase the refcount to a value other than one---higher than one like 2.
		//If we do that the hot publisher will wait for that many subscribers to join and only after that
		//it will start emitting values into the pipeline
		
		flux.subscribe(Util.getDefaultSubscriber("El Camino"));
		Util.sleepSeconds(5);
		flux.subscribe(Util.getDefaultSubscriber("El Chapo"));
		
		Util.sleepSeconds(40);
		
		
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
