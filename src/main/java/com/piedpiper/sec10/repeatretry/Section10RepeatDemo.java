package com.piedpiper.sec10.repeatretry;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Section10RepeatDemo {
	
	public static void main(String[] args) {
		Flux.range(1, 4)
		.doOnSubscribe(sub -> System.out.println("--subscribed"))
		.doOnComplete(() -> System.out.println("completed--"))
		.repeat(2)
		.subscribe(Util.getDefaultSubscriber());
		
	}

}
