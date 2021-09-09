package com.piedpiper.sec02;

import java.util.List;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec2Flux2 {
	
	public static void main(String[] args) {
		var flux = Flux.fromArray(new String[] {"hello","world","flux","array"});
		
		flux.subscribe(Util.onNext());
		
		
		var fluxFromList = Flux.fromIterable(List.of("hello", "world", "on", "iterable", "how cool", "is", "that"))
				.filter(s -> s.length() > 3)
				.map(String::toUpperCase);
		fluxFromList.subscribe(Util.onNext());
	}

}
