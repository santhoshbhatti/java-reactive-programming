package com.piedpiper.sec11.sink;

import com.piedpiper.util.Util;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class Sec11Lec02TryEmitValueWithErrorHandlerCallback {
	
	public static void main(String[] args) {
		Sinks.One<String> sink = Sinks.one();
		
		Mono<String> mono = sink.asMono();
		
		mono.subscribe(Util.getDefaultSubscriber());
		
		//Here the error handler call back provided
		//as EmitFailureHandler provided as lambda
		//expression to emitValue wont even execute
		//as the claue being emitted is a literal value.
		
		sink.emitValue("Hi", (signalType,emitResult) ->{
			System.out.println("signal type: "+signalType);
			System.out.println("emit result: "+emitResult);
			return false;
		});
		
		//Here the EmitFailureHandler executes as 
		//we try to push a second element to the mono
		//as we know Mono can only handle single element.
		sink.emitValue("Hi", (signalType,emitResult) ->{
			System.out.println("signal type: "+signalType);
			System.out.println("emit result: "+emitResult);
			//if this returns true we will retry perpetually
			//return true;
			return false;
		});
		
		
	}

}
