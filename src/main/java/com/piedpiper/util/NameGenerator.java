package com.piedpiper.util;

import java.util.ArrayList;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

public class NameGenerator {
	
	List<String> strings = new ArrayList<>();
	
	public Flux<String> getNameGeneratorFlux(){
		Flux<String> flux = Flux.generate(synchronousSink ->{
			String name = Util.faker().funnyName().name();
			System.out.println("generating names fresly>>>> : "+name);
			synchronousSink.next(name);
			strings.add(name);
			
		})
		 .startWith(Flux.fromIterable(strings))
		 .cast(String.class);
		return flux;
	}

}
