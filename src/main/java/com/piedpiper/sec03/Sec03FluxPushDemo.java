package com.piedpiper.sec03;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec03FluxPushDemo {
	
	public static void main(String[] args) {
		var nameProducer = new NameProducer();

		System.out.println("current thread !!!! " + Thread.currentThread().getName());
		//push is a non thread safe version of create.
		//When there is a single threaded producer use the push
		Flux.push(nameProducer).subscribe(Util.getDefaultSubscriber());

		Runnable producer = nameProducer::produce;
		
		for(int k= 0; k < 10; k++) {
			
			new Thread(producer).start();
		}

		System.out.println("ending main");
		Util.sleepSeconds(3);
	}

}
