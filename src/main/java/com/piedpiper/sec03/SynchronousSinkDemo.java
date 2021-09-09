package com.piedpiper.sec03;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class SynchronousSinkDemo {
	
	public static void main(String[] args) {
		//this will never stop. A SyncronousSink will be passe
		Flux.generate(
				synSink -> {
					synSink.next(Util.faker().funnyName().name());
					//This below line will be cause IllegalStateException as a on given SynchronousSink
					//next can be called only once
					
					//this also tells some thing about how the generate works. A new SynchronousSink object is supplied every
					//time the accept on this consumer is called
					//synSink.next(Util.faker().funnyName().name());
					
					//This will stop the generator
					//synSink.complete();
				}
		).log()
		.take(5)//this will send cancel signal to upstream producer and also cancels the subscription of the 
		//downstream subscribers. Hence stopping the generator. The generator need check for the cancel
		//signal to stop , it will be automatically handled by generator
		.subscribe(Util.getDefaultSubscriber());
	}

}
