package com.piedpiper.sec08.combinepublishers;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Lec04ZipFluxDemo {
	
	public static void main(String[] args) {
		Flux.zip(getBody(), getEngine(),getTire())
		.doOnNext(item -> 
		System.out.println(item.get(0) +" : "+item.get(1)+" : "+item.get(2)))
		.subscribe(Util.getDefaultSubscriber());
	}
	
	public static Flux<String> getBody(){
		return Flux.range(1, 5)
				.map(i -> "body"+i);			
	}
	public static Flux<String> getEngine(){
		return Flux.range(1, 2)
				.map(i -> "engine"+i);			
	}
	public static Flux<String> getTire(){
		return Flux.range(1, 5)
				.map(i -> "tire"+i);			
	}

}
