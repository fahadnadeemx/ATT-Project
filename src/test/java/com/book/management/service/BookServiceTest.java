package com.book.management.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

	@Test
	public void testGetBook_Success() {
		// Arrange
		Integer id = 1;
		BookEntity book = new BookEntity();
		when(repository.findById(id)).thenReturn(Optional.of(book));
		BookDto expected = new BookDto();
		when(mapper.mapToDto(book)).thenReturn(expected);

		// Act
		BookDto result = service.getBook(id);

		// Assert
		assertEquals(expected, result);
	}

	@Test
	public void testGetBook_NotFound() {
		// Arrange
		Integer id = 1;
		when(repository.findById(id)).thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(NoSuchElementException.class, () -> service.getBook(id));
	}

	@Test
	public void testSaveBook() {
		// Arrange
		BookDto input = new BookDto();
		BookEntity newBook = new BookEntity();
		when(mapper.mapToEntity(input)).thenReturn(newBook);
		BookEntity savedBook = new BookEntity();
		when(repository.save(any(BookEntity.class))).thenReturn(savedBook);
		BookDto expected = new BookDto();
		when(mapper.mapToDto(savedBook)).thenReturn(expected);

		// Act
		BookDto result = service.saveBook(input);

		// Assert
		assertEquals(expected, result);
	}

	@Test
	public void testUpdateBook_WhenExists() {
		// Arrange
		Integer id = 1;
		BookDto input = new BookDto();
		when(repository.existsById(id)).thenReturn(true);
		BookEntity updatedBook = new BookEntity();
		when(mapper.mapToEntity(input)).thenReturn(updatedBook);
		when(repository.save(any(BookEntity.class))).thenReturn(updatedBook);
		BookDto expected = new BookDto();
		when(mapper.mapToDto(updatedBook)).thenReturn(expected);

		// Act
		BookDto result = service.updateBook(id, input);

		// Assert
		assertEquals(expected, result);
	}

	@Test(expected = NoSuchElementException.class)
	public void testUpdateBook_WhenNotExists() {
		// Arrange
		Integer id = 1;
		BookDto input = new BookDto();
		when(repository.existsById(id)).thenReturn(false);

		// Act
		service.updateBook(id, input);
	}

	@Test
	public void testDeleteBook_WhenExists() {
		// Arrange
		Integer id = 1;
		when(repository.existsById(id)).thenReturn(true);
		doNothing().when(repository).deleteById(id);
		// Act
		service.deleteBook(id);

		// Assert
		Mockito.verify(repository).deleteById(id);
		Mockito.verify(repository).existsById(id);
	}

	@Test(expected = NoSuchElementException.class)
	public void testDeleteBook_WhenNotExists() {
		// Arrange
		Integer id = 1;
		when(repository.existsById(id)).thenReturn(false);

		// Act
		service.deleteBook(id);

		// Act and Assert
		NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> service.deleteBook(id));
		assertEquals("Book you selected is not Present with " + id, exception.getMessage());
	}
}
