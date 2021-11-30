package com.piedpiper.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class SlackRoom {
	
	private String name;
	private Sinks.Many<SlackMessage> sink;
	private Flux<SlackMessage> flux;
	public SlackRoom(String name) {
		this.name = name;
		sink = Sinks.many().replay().all();
		flux = sink.asFlux();
	}
	
	public void joinRoom(SlackMember member) {
		System.out.println(member.getName()+"------------- Joined ---------------"+this.name);
		this.subscribe(member);
		
		member.setMessageConsumer(
				msg -> this.postMessage(msg, member)
		);
	}
	
	private void postMessage(String message, SlackMember member) {
		SlackMessage slackMessage = new SlackMessage(member.getName()
				,message);
		this.sink.tryEmitNext(slackMessage);
		
	}
	
	private void subscribe(SlackMember member) {
		this.flux
		.doOnNext(message -> message.setReceiver(member.getName()))
		.filter(message -> !message.getSender().equals(message.getReceiver()))
		.map(SlackMessage::toString)
		.subscribe(member::receives);
	}
	
	
	

}
