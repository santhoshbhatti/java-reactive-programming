package com.piedpiper.sec04;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class sec03OperatorHandler {
	
	public static void main(String[] args) {
		//Handler acts a filter as well has a mapper
		//It has access to synchrnousSink object which can be used for 
		// for forwarding the flow objects to downstream consumer or just
		//filter them out or transform (map) them 
		
		//or synchrnousSink can be used to send complete signal
		Flux.range(0, 30)
		.handle((counter,synchonousSink) ->{
			if(counter == 25) {
				synchonousSink.complete();
			}else if((counter % 2) == 0) {
				synchonousSink.next(counter);
			}else {
				synchonousSink.next(counter*2);
			}
		}).subscribe(Util.getDefaultSubscriber());
	}

}
