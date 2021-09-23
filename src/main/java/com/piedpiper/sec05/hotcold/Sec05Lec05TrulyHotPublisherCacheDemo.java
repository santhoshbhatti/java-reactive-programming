package com.piedpiper.sec05.hotcold;

import java.time.Duration;
import java.util.stream.Stream;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec05Lec05TrulyHotPublisherCacheDemo {
	public static void main(String[] args) {

		var flux = Flux.<String>fromStream(() -> getMovieScenes())
				.delayElements(Duration.ofSeconds(1))
				.log()
				.cache(3);
				//.cache();

		
		//Here the cache() call is explained as follows
		//The first subscriber receives items at 1 sec delay
		//Once all items are emitted and consumed by the subscriber, the pipeline caches all items
		//and plays them back from the cache to the second subscriber
		//So we see that the second subscribers gets all items passed to it at once and not at 1 second delay
		
		//just share was alias to publish().refCount(1)
		//the cahce is an alias for publish().relay() from history
		
		//By default cache() caches all items going through the pipeline ---max cache size is Integer.MAX_VALUE
		//but we can customize it to cache any number of items like cache(2) ---only last 2 items are cached
		
		flux.subscribe(Util.getDefaultSubscriber("El Camino"));
		Util.sleepSeconds(10);
		flux.log().subscribe(Util.getDefaultSubscriber("El Chapo"));
		Util.sleepSeconds(10);

	}

	public static Stream<String> getMovieScenes() {
		return Stream.of("scene 1", "scene 2", "scene 3", "scene 4", "scene 5", "scene 6", "scene 7");

	}
}
