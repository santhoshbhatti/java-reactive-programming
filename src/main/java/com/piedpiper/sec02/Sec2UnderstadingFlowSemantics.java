package com.piedpiper.sec02;

import java.util.concurrent.atomic.AtomicReference;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec2UnderstadingFlowSemantics {
	
	public static void main(String[] args) {
		AtomicReference<Subscription> subscription = new AtomicReference<>();
		Flux.range(1, 20).log()
		.subscribeWith(
				new Subscriber<Integer>() {

					@Override
					public void onSubscribe(Subscription s) {
						System.out.println("got subscription " + s);
						subscription.set(s);
						
					}

					@Override
					public void onNext(Integer t) {
						System.out.println("next item : "+t);
						
					}

					@Override
					public void onError(Throwable t) {
						System.out.println("exception thrown: "+t.getMessage());
						
					}

					@Override
					public void onComplete() {
						System.out.println("flow completed successfully");
						
					}
					
				}
				
				);
		
		//notice that if we comment the below code this flow is not realised at all
		//the onSubscribe method will be called since request method is not invoked 
		//the onNext method will not be invoked by passing the elements in the stream
		
		//It should also be noted here that when once the subscription.cancel() is called
		//the stream processing stops....even thought we request for more items after the 
		//Cancellation.
		
		
		
		Util.sleepSeconds(3);
		subscription.get().request(3);
		Util.sleepSeconds(3);
		subscription.get().request(3);
		System.out.println("cancelling subscription");
		subscription.get().cancel();
		Util.sleepSeconds(3);
		subscription.get().request(3);
		Util.sleepSeconds(3);
		
	}

}
