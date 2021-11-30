package com.piedpiper.tests;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Sec12VirtualTimeStepVerifierDemo {
	
	@Test
	public void testVirtualTime(){
		
		//HERE WE ARE SIMULATING THE SCENARIO WHERE A 
		//time consuming pipeline is created and tests 
		//need time to complete....which is not practical
		//So we are specifying to the tests a virtual time 
		//so it switches to a scheduler which will run the tests more quickly
		//but internally provides an environment where this delay is simulated
		
		StepVerifier.withVirtualTime(() -> timeConsumingFlux())
		.thenAwait(Duration.ofSeconds(30))
		.expectNext("1a","2a","3a","4a")
		.verifyComplete();
	}
	
	@Test
	public void testVirtualTimeExpectNoEventInTimeFrame(){
		
		//HERE WE ARE SIMULATING THE SCENARIO WHERE A 
		//time consuming pipeline is created and tests 
		//need time to complete....which is not practical
		//So we are specifying to the tests a virtual time 
		//so it switches to a scheduler which will run the tests more quickly
		//but internally provides an environment where this delay is simulated
		
		//Aditionally we are saying in this test that 
		//we expect a on subscribe event and after that no event happens for next 
		//4 seconds
		StepVerifier.withVirtualTime(() -> timeConsumingFlux())
		.expectSubscription()
		.expectNoEvent(Duration.ofSeconds(4))
		.thenAwait(Duration.ofSeconds(20))
		.expectNext("1a","2a","3a","4a")
		.verifyComplete();
	}

	private Flux<String> timeConsumingFlux() {
		Flux<String> flux = Flux.range(1, 4)
							.delayElements(Duration.ofSeconds(5))
							.map(i -> i+"a");
		return flux;
	}

}
