package com.piedpiper.sec01;

import java.util.concurrent.CompletableFuture;

import com.piedpiper.util.Util;

import reactor.core.publisher.Mono;

public class Sec1MonoFromCompletableFuture {
	
	public static void main(String[] args) {
		
		var mono = Mono.fromFuture(getName());
		mono.subscribe(Util.onNext());
		
		//or we need to block it as below
		
		var mono2 = Mono.fromFuture(getName());
		var str = mono2.block();
		System.out.println("blocked execution   "+str);
		//this above will not execute the logic within a completable future
		
		//we need to block the main thread to male the completable future to work
		Util.sleepSeconds(1);
	}
	
	public static CompletableFuture<String> getName(){
		return CompletableFuture.supplyAsync(() -> Util.faker().funnyName().name());
	}

}
