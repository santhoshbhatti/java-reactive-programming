package com.piedpiper.util;

import java.util.function.Consumer;

import org.reactivestreams.Subscriber;

import com.github.javafaker.Faker;

public class Util {
	
	public static Consumer<Object> onNext(){
		return o -> System.out.println("Recieved: "+o.toString());
	}
	
	public static Consumer<Throwable> onError(){
		return e -> System.out.println("Error: "+e.getMessage());
	}
	
	public static Runnable onComplete() {
		return () -> System.out.println("Completed!!!!");
	}
	
	public static Faker faker() {
		return Faker.instance();
	}
	
	public static void sleepSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static <T> Subscriber<T> getDefaultSubscriber(){
		return new DefaultSubscriber<>();
	}
	
	public static <T> Subscriber<T> getDefaultSubscriber(String name){
		return new DefaultSubscriber<>(name);
	}

	public static void sleepMilliSeconds(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
