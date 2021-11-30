package com.piedpiper.sec11.sink;

import com.piedpiper.util.Util;

import reactor.core.publisher.Sinks;

public class Sec11Lec03UnicastMultipleSubscribersDemo {
	
	public static void main(String[] args) {
		//Handle through we can programatically push items into
				//Pipeline
				Sinks.Many<Object> sink = Sinks
						.many()
						.unicast()
						.onBackpressureBuffer();
				//Handle through which we can subscribe to the pipeline
				//Remeber nothing happens nothing happens untill 
				//someone subscribes to the pipeline
				var flux = sink.asFlux();
				
				flux.subscribe(Util.getDefaultSubscriber("sam"));
				
				//Error signal is sent to this second subsriber
				//as this is a unicast sink---one subscriber 
				//allowed for a unicast sink
				flux.subscribe(Util.getDefaultSubscriber("mike"));
				
				sink.tryEmitNext("Hello");
				sink.tryEmitNext("Hi");
				sink.tryEmitNext("How are you?");
				
				Runnable runnable = () -> {
					sink.tryEmitNext("from other thread");
				};
				
				Thread t = new Thread(runnable);
				
				t.start();
	}

}
