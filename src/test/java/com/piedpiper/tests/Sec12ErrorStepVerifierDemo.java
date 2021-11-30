package com.piedpiper.tests;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Sec12ErrorStepVerifierDemo {
	
	@Test
	public void testError() {
		var flux = Flux.just(1, 2, 3);
		Flux<Integer> errFlux = Flux.error(new RuntimeException("Error"));
		
		var intFlux=Flux.concat(flux,errFlux);
		StepVerifier.create(intFlux)
					.expectNext(1)
					.expectNext(2)
					.expectNext(3)
					.verifyError();
					
	}
	
	@Test
	public void testErrorType() {
		var flux = Flux.just(1, 2, 3);
		Flux<Integer> errFlux = Flux.error(new RuntimeException("Error"));
		
		var intFlux=Flux.concat(flux,errFlux);
		StepVerifier.create(intFlux)
					.expectNext(1)
					.expectNext(2)
					.expectNext(3)
					.verifyError(RuntimeException.class);
					//.verifyError(IllegalStateException.class);
					//will cause the error as the upstream
					//publisher will emit a RuntimeException
					
	}
	
	@Test
	public void testErrorMessage() {
		var flux = Flux.just(1, 2, 3);
		Flux<Integer> errFlux = Flux.error(new RuntimeException("Error"));
		
		var intFlux=Flux.concat(flux,errFlux);
		StepVerifier.create(intFlux)
					.expectNext(1)
					.expectNext(2)
					.expectNext(3)
					.verifyErrorMessage("Error");
	}

}
