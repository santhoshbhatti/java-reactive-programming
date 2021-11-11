package com.piedpiper.util;

import lombok.Data;

@Data
public class Book {
	
	String title;
	String author;
	String publisher;
	String catagory;
	double price;
	

	public Book() {
		var book = Util.faker().book();
		this.title = book.title();
		this.author = book.author();
		this.publisher = book.publisher();
		this.catagory = book.genre();
		this.price = Util.faker().random().nextDouble();
	}
}
