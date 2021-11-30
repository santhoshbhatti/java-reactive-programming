package com.piedpiper.tests;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Sec12RangeStepVerifierDemo {
	
	@Test
	public void testFluxRange() {
		Flux<Integer> flux = Flux.range(1, 50);
		
		StepVerifier.create(flux)
		.expectNextCount(50) // 49 will fail the test 
		//as flux emits 50 elements , so does 49
		.verifyComplete();
	}
	
	@Test
	public void testFluxRangeCheckElementsEmitted() {
		Flux<Integer> flux = Flux.range(1, 5000);
		//Here we are asimulating the scenario
		//where we dont know howmany elemets are emitted 
		//to the pipeline , but we hare checking the 
		//elements emitted individually to make sure
		//those elements meet a condidtion
		StepVerifier.create(flux)
		.thenConsumeWhile(i -> i <= 5000)
		.verifyComplete();
	}

}
