package com.project.lms.utils;

import com.project.lms.model.BookEntity;

public class BookUtil {
	public static BookEntity bookOne() {
		BookEntity book = new BookEntity();
		book.setTitle("Hellbringer");
		return book;
	}
	
	public static BookEntity bookTwo() {
		BookEntity book = new BookEntity();
		book.setTitle("Death Star");
		return book;
	}
	
	public static BookEntity bookThree() {
		BookEntity book = new BookEntity();
		book.setTitle("Night Call");
		return book;
	}
	
	public static BookEntity bookFour() {
		BookEntity book = new BookEntity();
		book.setTitle("Red Vision");
		return book;
	}
	
	public static BookEntity bookFive() {
		BookEntity book = new BookEntity();
		book.setTitle("Grande");
		return book;
	}
}
