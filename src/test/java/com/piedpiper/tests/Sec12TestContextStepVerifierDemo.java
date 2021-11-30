package com.piedpiper.tests;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;

public class Sec12TestContextStepVerifierDemo {
	
	@Test
	public void testWithoutContext() {
		
		Flux<Integer> flux = Flux.range(1, 4);
		
		StepVerifier.create(flux)
		.expectNextCount(12)
		.verifyComplete();
		
	}
	
	@Test
	public void testWithContext() {
		
		//here when the test fails it clearly displays the scenario which failed!!!!
		
		Flux<Integer> flux = Flux.range(1, 4);
		StepVerifierOptions options = StepVerifierOptions.create().scenarioName("number test");
		StepVerifier.create(flux,options)
		.expectNextCount(12)
		.verifyComplete();
		
	}

	
	@Test
	public void testWithContextInEachAssertion() {
		
		//here when the test fails it clearly displays the scenario which failed!!!!
		
		Flux<String> flux = Flux.just("a", "b", "c", "d");
		StepVerifierOptions options = StepVerifierOptions.create().scenarioName("alphabet test");
		StepVerifier.create(flux,options)
		.expectNext("a")
		.as("a test")
		.expectNext("b")
		.as("b test")
		.expectNext("c")
		.as("c test")
		.expectNext("e")
		.as("d test")
		.verifyComplete();
		
	}
}
