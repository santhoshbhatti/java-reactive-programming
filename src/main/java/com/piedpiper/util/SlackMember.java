package com.piedpiper.util;

import java.util.function.Consumer;

public class SlackMember {
	
	private String name;
	private Consumer<String> messageConsumer;
	
	public SlackMember(String name) {
		this.name = name;
	}
	
	void receives(String message) {
		System.out.println(message);
	}
	
	public void says(String message) {
		this.messageConsumer.accept(message);
		
	}

	

	public String getName() {
		return name;
	}

	

	public void setMessageConsumer(Consumer<String> messageConsumer) {
		this.messageConsumer = messageConsumer;
	}
	
	
}
