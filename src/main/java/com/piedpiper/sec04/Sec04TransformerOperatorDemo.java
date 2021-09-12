package com.piedpiper.sec04;

import java.util.function.Function;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec04TransformerOperatorDemo {
	
	public static void main(String[] args) {
		//When we have steps which can be reused in multiple pipelines
		//we can isolate steps or operators and add them to pipeline
		
		getAllPersons()
		.transform(transform())
		.subscribe(Util.getDefaultSubscriber());
		
	}
	
	public static Flux<Person> getAllPersons() {
		return Flux.range(1, 20)
		.map(i -> new Person());
	}
	
	public static Function<Flux<Person>, Flux<Person>> transform(){
		return pf -> pf.filter(p -> p.getAge() > 10)
				.doOnNext(p -> p.setName(p.getName().toUpperCase()))
				.doOnDiscard(Person.class, p -> System.out.println("discarded "+p));
	}

}
