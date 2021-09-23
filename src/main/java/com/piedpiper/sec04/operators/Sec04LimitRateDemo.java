package com.piedpiper.sec04.operators;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class Sec04LimitRateDemo {
	
	public static void main(String[] args) {
		Flux.range(0, 1000)
		.log()
		.limitRate(100)
		.subscribe(Util.getDefaultSubscriber());
		
		//here the subscriber requests for unbounded stream of elements
		//So we can a use a rate limit operator here to regulate the flow
		
		//when we add limitRate(100) even though the subscriber requests unbounded items from producer
		//the limit rate will intercept this call and requests only 100 items so limitRate(100).
		//the producer on its part also will produce 100 items and not more initially.----reactive!!!!
		
		//then when the subscriber starts draining these items one by one and finally when it drains
		//75% of the items , the rate limiter checks the buffer and requests 75 more items.
		//thus mainitaining the buffere size to 100
		
		//This is very important as in real life the producer and consumer will run different threads
		//and may not even have knowledge of each other. Producer may not know the rate at which the consumer can
		//consume and vice versa. So rate limiter sits in betweena and regulates the flow
		
		//we can also tweek the the percentage of data after which the request to the producer should be made by
		//passing one more argument to the limiRate function specifying the percentage
	}

}
