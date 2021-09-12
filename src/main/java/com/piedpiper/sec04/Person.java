package com.piedpiper.sec04;

import com.piedpiper.util.Util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
	
	private String name;
	private int age;
	public Person() {
		this.name = Util.faker().funnyName().name();
		this.age = Util.faker().random().nextInt(5, 30);
	}

}
