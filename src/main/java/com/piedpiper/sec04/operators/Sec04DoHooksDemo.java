package com.piedpiper.sec04.operators;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec04DoHooksDemo {
	public static void main(String[] args) {
		Flux.create((fluxSink) ->{
			System.out.println("begining of producer");
			for(int k=0;k<10;k++) {
				fluxSink.next(k);
			}
			//fluxSink.complete();
			//Instead of above if we send an error . doOnError is executed instead of doOnComplete
			fluxSink.error(new RuntimeException("OOPS!!!!"));
			System.out.println("exiting the producer");
		})
		.doOnComplete(() -> System.out.println("do On Complete"))
		.doFirst(() -> System.out.println("doFirst: 1"))//gets executed first in the pipeline
		.doOnNext((item) -> System.out.println("doOnNext: "+item))
		.doOnSubscribe(sub -> System.out.println("doOnSubscribe 1: "+sub))
		.doOnNext((item) -> System.out.println("doOnNext2: "+item))
		.doOnRequest(numRequested -> System.out.println("donOnRequest :"+numRequested))
		.doFirst(() -> System.out.println("doFirst: 2"))
		.doOnError(ex -> System.out.println("doOnError: "+ex.getMessage()))
		.doOnTerminate(() -> System.out.println("doOnTerminate"))
		.doOnCancel(() -> System.out.println("doOnComplete"))
		.doFinally(signal -> System.out.println("doFinally: "+signal))
		.doOnSubscribe(sub -> System.out.println("doOnSubscribe 2: "+sub))
		.doFirst(() -> System.out.println("doFirst: 3"))
		.doOnDiscard(Integer.class, s -> System.out.println("doOnDiscard: "+s))
		.take(2)
		.doFinally(signal -> System.out.println("doFinally 2: "+signal))
		.subscribe(Util.getDefaultSubscriber())
		;
		
		//in the above there are 3 doFirst operators
		//their execution order is bottom up
		//Why? this happens because the execution order of the pipeline is 
		//from bottom ie..the doFirst nearest to subscriber executes first as
		//only when the subscriber requests the data the pipeline starts and so do first 
		//nearest to subscriber executes first and in that order. first thing the subscriber does
		//is to subscribe to the producer (call to subscribe happens)---in that same flow all the
		//doFirst operators execute. 
		
		//Next to execute is the doOnSubscribe....when the onSubscribe is invoked back
		//on the subscriber by the producer ...doOnSubscribe operator is executed
		
		//so if we have many doOnSubscribe in this flow...they will be executed top to bottom
		//as the call to onSubscribe from happens from the producer to the subscriber the .
		
		//So the do calls need to be seen in this perspective....
		//if the the do hooks are part of subscriber flow call they happen bottom up
		//if the do hooks are part of publisher flow call they happen top down
		
		//doOnRequest() is called next once the subscription is got
		//So this is what the subscriber does ---request for items from the producer
		//So doOnRequest is called in this flow
		
		//Next thing that happens is the producer calls next on sink when the data is available and sends the 
		//data to subscriber(call to onNext on subscriber)---in this flow doOnNext is invoked ---if we have 
		//multiple they will be executed top to bottom
		
		//do ON terminate gets executed on error and on complete
		
		//when we do take(2) ...it sends a cancel signal to producer to cancel subscription
		//after the 2 elements are pushed. Here doOnTerminate is not executed.
		
		//but doFinally is executed. So doFinally always gets executed so this is the please to do all final 
		//clean up activities.
		
		//when we did take(2) all other elements pushed by producer are discarded as there is no active subscriber
		//hence doOnDiscard are getting executed for each elements pushed after the subscription ends
		//This is because we did not care to stop the producer when the cancel signal is raised by take.
		//we can check if the subscription is ended before emitting more elements in the producer
		
		//here when we use take the do finally gets executed before 
		//the subscriber exits. This is because the doFinally decorator is placed above take
		//So if you need to foFinally to get executed after the subscriber executes we need to place doFinally after take()
		//so now doFinally2 above will run after the subscriber completes
		
		
	}
}
