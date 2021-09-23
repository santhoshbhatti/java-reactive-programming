package com.piedpiper.sec05.hotcold;

import java.util.ArrayList;
import java.util.List;

public class Example1 {
	
	
	public static void main(String[] args) {
		List<Employee> employees = new ArrayList<Employee>(); //will this compile
		
		List<? extends Person> employees2 = new ArrayList<Employee>();
	}

}

class Person{
	String id;
	String name;
	public Person(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	

}
class Employee extends Person{
	double salary;

	public Employee(String id, String name, double salary) {
		super(id, name);
		this.salary = salary;
	}
	
	
}
