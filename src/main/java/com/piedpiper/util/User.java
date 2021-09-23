package com.piedpiper.util;

import lombok.Data;

@Data
public class User {
	
	private String id;
	private String name;
	
	public User(String id) {
		this.id = id;
		this.name = Util.faker().funnyName().name();
	}

}
