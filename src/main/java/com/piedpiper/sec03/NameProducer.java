package com.piedpiper.sec03;

import java.util.function.Consumer;

import com.piedpiper.util.Util;

import reactor.core.publisher.FluxSink;

public class NameProducer implements Consumer<FluxSink<String>>{

	private FluxSink<String> fluxSink;

	@Override
	public void accept(FluxSink<String> sink) {
		
		this.fluxSink = sink;
		
	}
	
	public void produce() {
			var funnyName = Util.faker().funnyName().name();
			System.out.println(Thread.currentThread().getName() + " : "+ funnyName +" : "+Thread.currentThread().isDaemon());
			fluxSink.next(funnyName);
	}

}
