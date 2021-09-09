package com.piedpiper.sec03;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class FluxTakeDemo {
	
	public static void main(String[] args) {
		Flux<String> funnyNames = Flux.create(sink ->{
			
			for(int k = 0; k < 100; k++) {
				//The moment the take(3) cancels the downstream subscription
				//by issuing the cancel signal. the producer should also stop
				//producing any new items and stop. In this case if we don't uncomment
				//the following lines the producer will keep producing new items 
				//without regards to the subscription which got cancelled.
				
				//processing the cancel() signal from downstream subscriber and exiting
				
				/*if (sink.isCancelled())
					break;*/
				String name = Util.faker().funnyName().name();
				System.out.println("next funny name is >>>>>>> "+name);
				sink.next(name);
			}
			
		});
		//this take operator after the 2 items have flown through it it cancels subscription 
		//and sends the onComplete downtream ie to downstream subscribers ---below
		
		//But there is a problem here even though subscribers have cancelled the flow
		//and bailed out after the onComplete signal is processed
		//the producer continues to generate new names without the knowledge of the subscribers
		
		funnyNames
		.log()
		.take(2) 
		.log()
		.subscribe(Util.onNext());
	}

}
