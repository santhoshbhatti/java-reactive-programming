package com.piedpiper.sec04;

import java.time.Duration;

import org.reactivestreams.Publisher;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec04TimeoutOperatorDemo {
	
	public static void main(String[] args) {
		
		
		//in this below flux we are subscribing to a producer which is
		//taking lot of time to emit data. So we can provide a timeOut 
		//duration and when the producer exceeds the timeOut, the timeout operator
		//cancels the subscription and timeout operator sends a onError 
		//signal to the subscriber.
		
		//To mitigate this scenario of slow producer we can provide a 
		//alternate producer which can produce some default items as second param 
		//to time out
		
		
		getRangeFlux()
		.log()
		.timeout(Duration.ofSeconds(2),getDefaultPublisher())
		.log()
		.subscribe(Util.getDefaultSubscriber());
		
		Util.sleepSeconds(1000);
		
	}
	
	private static Flux<Integer> getDefaultPublisher() {
		return Flux.<Integer>generate(synchronousSink ->{
			synchronousSink.next(Util.faker().random().nextInt(1000, 1700));
		}).limitRate(100, 80);
	}

	public static Flux<Integer> getRangeFlux(){
		return Flux.range(1, 30)
				.delayElements(Duration.ofSeconds(5));
	}

}
