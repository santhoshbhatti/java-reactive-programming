package com.piedpiper.sec08.combinepublishers;

import com.piedpiper.util.NameGenerator;
import com.piedpiper.util.Util;

public class Lec01StartWithDemo {

	public static void main(String[] args) {
		NameGenerator generator = new NameGenerator();
		generator
		.getNameGeneratorFlux()
		.take(2)
		.subscribe(Util.getDefaultSubscriber());
		
		
		generator
		.getNameGeneratorFlux()
		.take(3)
		.subscribe(Util.getDefaultSubscriber());
		
		generator
		.getNameGeneratorFlux()
		.doOnNext(item -> System.out.println("do on first: "+ item))
		.filter(name -> name.startsWith("K"))
		.take(3)
		.subscribe(Util.getDefaultSubscriber());
	}
}
