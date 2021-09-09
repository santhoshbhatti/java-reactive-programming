package com.piedpiper.sec03;

import java.util.concurrent.CountDownLatch;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class SharingFluxSinkWithMultipleThreads {

	public static void main(String[] args) {
		var nameProducer = new NameProducer();

		System.out.println("current thread !!!! " + Thread.currentThread().getName());

		Flux.create(nameProducer).subscribe(Util.getDefaultSubscriber());

		Runnable producer = nameProducer::produce;
		
		for(int k= 0; k < 10; k++) {
			
			new Thread(producer).start();
		}

		System.out.println("ending main");
		Util.sleepSeconds(3);
	}

}
