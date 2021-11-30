package com.piedpiper.tests;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;
import reactor.util.context.Context;

public class Sec12TestWithContextPropogationStepVerifierDemo {
	
	public Mono<String> getWelcomeMessage(){
		return Mono.deferContextual(ctx -> {
			if(ctx.hasKey("user")) {
				return Mono.just("Welcome "+ctx.get("user"));
			}else {
				return Mono.error(new RuntimeException("error"));
			}
			
		});
	}
	
	
	@Test
	public void testContext() {
		StepVerifier.create(getWelcomeMessage())
		.verifyError(RuntimeException.class);
		
	}
	
	@Test
	public void testWithContext() {
		//creating and passing context to publisher 
		StepVerifierOptions options = StepVerifierOptions.
				create().withInitialContext(Context.of("user", "sam"));
		StepVerifier.create(getWelcomeMessage(),options)
		.expectNext("Welcome sam")
		.verifyComplete();
		
	}
	
	
	

}
