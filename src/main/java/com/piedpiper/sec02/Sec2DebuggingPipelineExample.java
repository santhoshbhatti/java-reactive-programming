package com.piedpiper.sec02;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec2DebuggingPipelineExample {
	
	public static void main(String[] args) {
		//debugging the flow through the log() method invoked between the 
		//components of the flow.
		
		//A flux is a stream of elements
		//and there are subscribers or the processors subscribed to the stream or the source
		//and how the elements flow through the subscribers/processors and end up in the sink can be debugged
		
		//below the folow of elements between the flux and the first map is as follows:
		
		//****** onSubscribe([Synchronous Fuseable] FluxRange.RangeSubscriptionConditional) is callback on the subscriber
		//subsciber here is the map
		//then the publisher establishes the subscription by calling back onSubscribe() and passing the subscription
		//object back to the subscriber as shown above
		
		//Next is the subscriber calling request ---requesting for the elements in the stream as below
		
		//request(unbounded) ---here the subscriber is asking for all the elements in the stream
		//this can be customized by asking for a specific number of elements
		
		//as the flux above is an integer flux each induvidual elements are passwd back to the subscriber 
		//by calling onNext() on the subscriber
		
		//finally when the stream is completes the onComplete on all the down stream subscribers are invoked
		
		
		Flux.range(1, 100)
		.log()
		.map(i -> Util.faker().chuckNorris())
		.filter(noris -> !noris.fact().startsWith("A"))
		.map(noris -> noris.fact())
		.log()
		.subscribe(System.out::println);
	}

}
