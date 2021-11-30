package com.piedpiper.util;



import java.util.Date;

import lombok.Data;

@Data
public class SlackMessage {
	
	private String sender;
	private String message;
	private Date dateTime;
	private String receiver;
	private static final String FORMAT = "[%s -> %s] : %s on %s";
	public SlackMessage(String sender, String message) {
		super();
		this.sender = sender;
		this.message = message;
		this.dateTime = new Date();
	}
	@Override
	public String toString() {
		return String.format(FORMAT, this.sender,this.receiver
				,this.message,this.dateTime.toString());
	}
	
	
	
	
	
	
}
