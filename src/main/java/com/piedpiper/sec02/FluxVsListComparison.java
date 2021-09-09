package com.piedpiper.sec02;

import java.util.ArrayList;
import java.util.List;

import com.piedpiper.util.Util;

import reactor.core.publisher.Flux;

public class FluxVsListComparison {
	
	
	public static List<String> getFunnyNames(int count){
		List<String> list = new ArrayList<>();
		for(int k = 0; k < count; k++) {
			list.add(getNames());
			
		}
		return list;
	}


	private static String getNames() {
		Util.sleepSeconds(1);
		return Util.faker().funnyName().name();
	}
	
	
	public static Flux<String> getFluxOfNames(int count){
		return Flux.range(0, count).map(i -> getNames());
	}
	
	
	public static void main(String[] args) {
		//this calls returns all the items after 5 minutes1
		System.out.println(getFunnyNames(5));
		
		//But the flux returns any item which is avaialble at that point
		//it wont wait for all items to get populated
		//this is kind of cool as we can process the item while waitng for the next item
		getFluxOfNames(5).subscribe(Util.onNext());
	}

}
