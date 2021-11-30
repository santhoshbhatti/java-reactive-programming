package com.piedpiper.tests;

import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.piedpiper.util.Book;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Sec12AssertStepVerifierDemo {
	
	@Test
	public void testWithAssertions() {
		var mono = Mono.fromSupplier(() ->new Book());
		
		StepVerifier.create(mono)
		.assertNext(book -> Assertions.assertNotNull(book.getAuthor()))
		.verifyComplete();
					
	}
	
	@Test
	public void testWithAssertionsWithDelay() {
		//Adding delay does not matter to step Verifier
		//It behaves asame as without delay-----cooooooool!!!!!
		var mono = Mono.fromSupplier(() ->new Book())
				.delayElement(Duration.ofSeconds(3));
		
		StepVerifier.create(mono)
		.assertNext(book -> Assertions.assertNotNull(book.getAuthor()))
		.verifyComplete();
					
	}
	
	@Test
	public void testWithAssertionsWithIndifinateDelay() {
		//Adding delay does not matter to step Verifier
		//It behaves asame as without delay-----cooooooool!!!!!
		var mono = Mono.fromSupplier(() ->new Book())
				.delayElement(Duration.ofSeconds(3));
		
		//Here we are trying to test the behaviour
		//where the pipeline takes indefinate time to complete.
		//So we are definin in the test ccase that in case if pipeline deos not
		//complete in the stipulated time the test will fail
		
		//Like as follows .verify(Duration.ofSeconds(2)) is making sure
		//to fail the test if at end of 2 seconds if the onComplete signal
		//is not sent by publisher the test will fail.----it fails as expected
		
		//In case if we increase it to 4 it passes
		StepVerifier.create(mono)
		.assertNext(book -> Assertions.assertNotNull(book.getAuthor()))
		.expectComplete()
		.verify(Duration.ofSeconds(4));
		//.verify(Duration.ofSeconds(2));
					
	}

}
