package com.piedpiper.sec11.sink;

import com.piedpiper.util.Util;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class Sec11Lec03EmitFailureHandlerDemo {
	
	public static void main(String[] args) {
		Sinks.One<Object> sink = Sinks.one();
		
		Mono<Object> mono = sink.asMono();
		
		mono.subscribe(Util.getDefaultSubscriber());
		
		//Here the error handler call back provided
		//as EmitFailureHandler provided as lambda
		//expression to emitValue wont even execute
		//as the claue being emitted is a literal value.
		
		sink.emitValue(generateValue(), (signalType,emitResult) ->{
			System.out.println("signal type: "+signalType);
			System.out.println("emit result: "+emitResult);
			return false;
		});
	}

	private static Object generateValue() {
		var intVal = Util.faker().random().nextInt(1, 10);
		if((intVal % 2) == 0) {
			throw new RuntimeException("failure");
		}
		return "Hi: "+intVal;
	}

}
