package com.piedpiper.sec02;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec02Flux {
	public static void main(String[] args) {
		var flux = Flux.just(10,20,30,45,78,99,35);
		flux.subscribe(Util.onNext());
		flux.filter(i -> i%2==0).subscribe(Util.onNext());
		
		//multiple subscribers----possible with mono also
		flux.subscribe(i -> System.out.println(i*2));
	}

}
