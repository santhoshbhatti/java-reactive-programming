package com.piedpiper.sec08.combinepublishers;

import java.time.Duration;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class CombiningPublishersAssignment {
	
	public static void main(String[] args) {
		Flux.combineLatest(deprciatingCarPrize(10, 3000),getDemandFactor(), (a,b) -> {
			System.out.println("latest price: "+ a +"  latest demand-factor: "+b);
			return a * b;
		})
		.subscribe(Util.getDefaultSubscriber());
		
		Util.sleepSeconds(100);
		
		/*getDemandFactor().subscribe(Util.getDefaultSubscriber());
		Util.sleepSeconds(100);*/
	}
	
	public static Flux<Integer> deprciatingCarPrize(int percent,int initialPrize){
		
		return Flux.create(sink -> {
			var currentPrize = initialPrize;
			while(true) {
				var depPrize = currentPrize - ((currentPrize * percent)/100);
				if(depPrize < 100) {
					break;
				}
				currentPrize = depPrize;
				sink.next(depPrize);
			}
			sink.complete();
		}).cast(Integer.class)
		  .delayElements(Duration.ofSeconds(3));
	}
	
	
	public static Flux<Double> getDemandFactor(){
		return Flux.generate(sink -> {
			double d = Util.faker().number().numberBetween(8, 12)/10.0;
			sink.next(d);		
		}).cast(Double.class)
		.delayElements(Duration.ofSeconds(1));
	}

}
