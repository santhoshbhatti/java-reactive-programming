package com.piedpiper.sec09.batching;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Section09Assignment {
	
	static Map<String,Function<Flux<PurchaseOrder>, Flux<PurchaseOrder>>> processMap = 
			Map.of("Kids", automativeProcessing(), 
					"Automotive", kidsProcessing());
	private static Set<String> catagories = Set.of("Kids","Automotive");
	
	static Function<Flux<PurchaseOrder>, Flux<PurchaseOrder>> automativeProcessing(){
		return orderFlux -> orderFlux
				.doOnNext(po -> po.setPrice(1.1 * po.getPrice()))
				.doOnNext(po -> po.setName("{{"+po.getName()+"}}"));
	}
	
	static Function<Flux<PurchaseOrder>, Flux<PurchaseOrder>> kidsProcessing(){
		return orderFlux -> orderFlux
				.doOnNext(po -> po.setPrice(0.5 * po.getPrice()))
				.doOnNext(po -> po.setName("{{{"+po.getName()+"}}}"))
				.flatMap(po -> Flux.concat(Mono.just(po),getFreeProduct()));
	}
	
	
	private static Mono<PurchaseOrder> getFreeProduct() {
		return Mono.fromSupplier(()-> {
		var po=	new PurchaseOrder();
		po.setName("free: " + po.getName());
		po.setPrice(0.0);
		po.setCategory("Kids");
		return po;
		});
	}

	public static void main(String[] args) {
		
		orderStream()
		.filter(item -> catagories.contains(item.getCategory()))
		.groupBy(item -> item.getCategory())
		.flatMap(group -> processMap.get(group.key()).apply(group))
		.subscribe(Util.getDefaultSubscriber());
		
		Util.sleepSeconds(100);
		
	}
	
	public static Flux<PurchaseOrder> orderStream(){
		return Flux.interval(Duration.ofMillis(100))
		.map(duration -> new PurchaseOrder());
		
	}

}



