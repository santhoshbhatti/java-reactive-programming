package com.piedpiper.sec09.batching;

import java.time.Duration;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Lec02BatchingByBufferingWithCompleteSignal {
	public static void main(String[] args) {
		generateEvents()
		.buffer(5)
		.subscribe(Util.getDefaultSubscriber());
		//as can be seen in the output
		//if the publisher only sends fewer items than the size of
		//the buffer and sends complete signal, those remaining items are
		//batched together and send to subscriber.
		
		//But if the complete signal is not sent the pipeline will wait till
		//all the items of the buffer are not emitted by the publisher.
		Util.sleepSeconds(30);
	}
	
	public static Flux<String> generateEvents(){
		return Flux
				.interval(Duration.ofMillis(300))
				.map(i -> "event: "+i)
				.take(8);
	}
}
