package com.piedpiper.sec10.repeatretry;

import java.time.Duration;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

public class Section10RetryFixedDelayDemo {
	
	public static void main(String[] args) {
		Flux.range(1, 4)
		.doOnSubscribe(sub -> System.out.println("--subscribed"))
		.doOnComplete(() -> System.out.println("completed--"))
		.map(i -> i/(Util.faker().random().nextInt(1,5) > 3 ? 0:1))
		.doOnError(e -> System.out.println("---error!!!!!"))
		.retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(2)))
		.subscribe(Util.getDefaultSubscriber());
		
		Util.sleepSeconds(20);
	}

}
