package com.book.management.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.book.management.entity.BookEntity;
import com.book.management.mapper.BeanMapper;
import com.book.management.model.BookDto;
import com.book.management.repository.BookRepository;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

	@InjectMocks
	private BookService service;

	@Mock
	private BookRepository repository;

	@Mock
	private BeanMapper mapper;

	@Test
	public void test_getAllBooks() {
		List<BookEntity> list = new ArrayList<BookEntity>();
		BookEntity book1 = new BookEntity(1, "first", "Fahad", 1000);
		BookEntity book2 = new BookEntity(2, "second", "Nadeem", 5000);
		list.add(book1);
		list.add(book2);
		when(repository.findAll()).thenReturn(list);

		List<BookDto> bookList = service.getBooks();

		assertEquals(2, bookList.size());
	}

}
