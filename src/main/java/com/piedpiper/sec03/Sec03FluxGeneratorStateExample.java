package com.piedpiper.sec03;

import java.util.HashMap;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec03FluxGeneratorStateExample {
	
	public static void main(String[] args) {
		var authCount = new HashMap<String,Integer>();
		Flux.generate(() ->  authCount, (map,sink) ->{
			
			String author = Util.faker().book().publisher();
			if (map.containsKey(author)) {
				map.put(author, map.get(author)+1);
			}else {
				map.put(author,1);
			}
			sink.next(author);
			return map;
			
		}).log()
		.take(200)
		.subscribe(Util.onNext());
		
		
		authCount.entrySet().forEach(System.out::println);
	}

}
