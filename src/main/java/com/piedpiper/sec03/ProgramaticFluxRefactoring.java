package com.piedpiper.sec03;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class ProgramaticFluxRefactoring {
	
	public static void main(String[] args) {
		var nameProducer = new NameProducer();
		
		System.out.println("current thread !!!! "+Thread.currentThread().getName());
		
		//This is event dispatching in the single thread or same thread
		//we build a pipeline here by providing a Consumer<FluxSink> instance
		//to Flux.create method. and we also establish a subscriber by calling subscribe on flux
		// and passing DefaultSubscriber
		//but this pipeline is inactive until the consumer pushes an item into its fluxsink.
		//So this done by calling the nameProducer.produce method which will send the item to the sink
		//by calling the sink.next and passing the element to it.
		//Once an item is passed the onNext signal is passed to the subsciber which will execute.
		
		//So the magic here is the pipeline is built before hand but the subscriber is invoked
		//when the fluxsink has data to execute the pipeline. All this is happening on the same thread.
		//This is the cornerstone of non blocking single threaded model.
		
		Flux.create(nameProducer)
		.subscribe(Util.getDefaultSubscriber());
		
		nameProducer.produce();
		
		System.out.println("ending main");
		
	}

}
