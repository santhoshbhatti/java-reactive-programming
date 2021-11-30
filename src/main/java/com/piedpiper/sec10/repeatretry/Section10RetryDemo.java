package com.piedpiper.sec10.repeatretry;

import com.piedpiper.util.Util;
import com.sun.net.httpserver.Authenticator.Retry;

import reactor.core.publisher.Flux;

public class Section10RetryDemo {
	
	public static void main(String[] args) {
		Flux.range(1, 4)
		.doOnSubscribe(sub -> System.out.println("--subscribed"))
		.doOnComplete(() -> System.out.println("completed--"))
		.map(i -> i/(Util.faker().random().nextInt(1,5) > 3 ? 0:1))
		.doOnError(e -> System.out.println("---error!!!!!"))
		.retry(2)
		.subscribe(Util.getDefaultSubscriber());
	}

}
