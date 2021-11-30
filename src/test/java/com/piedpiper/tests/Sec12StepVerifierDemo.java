package com.piedpiper.tests;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Sec12StepVerifierDemo {
	
	@Test
	public void testFlux() {
		var flux = Flux.just(1, 2, 3);
		StepVerifier.create(flux)
					.expectNext(1)
					.expectNext(2)
					.expectNext(3)
					//.expectNext(4) :this fails the test as the onComplete signal is pushed by the publisher
					.verifyComplete();
	}
	
	@Test
	public void testFlux2() {
		var flux = Flux.just(1, 2, 3);
		StepVerifier.create(flux)
					.expectNext(1, 2, 3)
					//.expectNext(4) :this fails the test as the onComplete signal is pushed by the publisher
					.verifyComplete();
	}

}
