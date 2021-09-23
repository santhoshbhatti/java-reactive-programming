package com.piedpiper.util;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class DefaultSubscriber<T> implements Subscriber<T>{

	private Subscription subscription;
	
	private String name;
	
	
	public DefaultSubscriber(String name) {
		super();
		this.name = name;
	}
	
	

	public DefaultSubscriber() {
		super();
		this.name = Util.faker().funnyName().name();
	}



	@Override
	public void onSubscribe(Subscription s) {
		System.out.println("Subscription received : "+s);
		this.subscription = s;
		this.subscription.request(Long.MAX_VALUE);
	}

	@Override
	public void onNext(T t) {
		
		System.out.println("got next Item : "+name +"   "+t.toString()+" >>>> current thread : "+Thread.currentThread().getName());
	}

	@Override
	public void onError(Throwable t) {
		System.out.println("error :"+getName()+" : "+t.getMessage());
		
	}

	@Override
	public void onComplete() {
		System.out.println("flow completed!!!!! : "+getName());
		
	}
	
	
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public static void main(String[] args) {
		DefaultSubscriber<String> sub = new DefaultSubscriber<>();
		sub.onNext("Hello");
		System.out.println(sub.getName());
	}

}
