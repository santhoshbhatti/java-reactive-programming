package com.piedpiper.sec03;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class FluxRangeTest {
	
	public static void main(String[] args) {
		Flux.range(1, 100)
		.log()
		.take(5)
		.log()
		.subscribe(Util.onNext());
	}

}
