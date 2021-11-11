package com.piedpiper.sec09.batching;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.piedpiper.util.Book;
import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Lec05BatchingWithBufferingAssignment {
	
	public static void main(String[] args) {
		booksSoldStream()
		.buffer(Duration.ofSeconds(2))
		.map(list -> revenueCaluculatorList(list))
		.subscribe(Util.getDefaultSubscriber());
		
		Util.sleepSeconds(100);
			
	}
	
	private static Map<String,Double> revenueCaluculatorList(List<Book> list) {
		var map = list.stream()
		.collect(Collectors
				.groupingBy(Book::getCatagory,
						Collectors.summingDouble(Book::getPrice)));
		return map;
	}

	public static Flux<Book> booksSoldStream(){
		return Flux.interval(Duration.ofMillis(100))
				.map(i -> new Book());
	}

}


