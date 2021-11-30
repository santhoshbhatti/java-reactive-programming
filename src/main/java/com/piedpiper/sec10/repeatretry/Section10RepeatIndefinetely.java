package com.piedpiper.sec10.repeatretry;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Section10RepeatIndefinetely {
	public static void main(String[] args) {
		//this will not stop it continues indefinetly
		//Note -> repeat is done only when the 
		//pipeline sends complete signal
		//It wont work for error signal
		Flux.range(1, 4)
		.doOnSubscribe(sub -> System.out.println("--subscribed"))
		.doOnComplete(() -> System.out.println("completed--"))
		//.map(i -> i/0) to show repeat wont work in case of error 
		//signal
		.repeat()
		.subscribe(Util.getDefaultSubscriber());
		
	}
}
