package com.project.lms.converter;

import java.util.ArrayList;
import java.util.List;

import com.project.lms.dto.BookCreateUpdateDto;
import com.project.lms.dto.BookDto;
import com.project.lms.model.BookEntity;

public class BookConverter {
	
	private BookConverter() {}
	
	public static BookDto toDto(BookEntity book) {
		BookDto bookToReturn = new BookDto();
		bookToReturn.setId(book.getId());
		bookToReturn.setTitle(book.getTitle());
		return bookToReturn;
	}
	
	public static BookEntity toEntity(BookCreateUpdateDto book) {
		BookEntity bookToReturn = new BookEntity();
		bookToReturn.setId(null);
		bookToReturn.setTitle(book.getTitle());
		return bookToReturn;
	}
	
	public static List<BookDto> toListDto(List<BookEntity> books) {
		List<BookDto> booksToReturn = new ArrayList<>();
		for(BookEntity be : books) {
			booksToReturn.add(toDto(be));
		}
		return booksToReturn;
	}

}
