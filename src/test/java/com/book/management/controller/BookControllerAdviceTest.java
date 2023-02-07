package com.book.management.controller;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerAdviceTest {

	private BookControllerAdvice advice = new BookControllerAdvice();
	private Logger log = Mockito.mock(Logger.class);

	@Before
	public void setUp() {
		advice = new BookControllerAdvice();
	}

	@Test
	public void testHandleNotFound_NoSuchElementException() {
		// Arrange
		NoSuchElementException ex = new NoSuchElementException("Test exception");
		log.error(ex.getMessage());

		// Act
		advice.handleNotFound(ex);

		// Assert
		Mockito.verify(log).error("Test exception");
	}

	@Test
	public void testHandleNotFound_IllegalArgumentException() {
		// Arrange
		IllegalArgumentException ex = new IllegalArgumentException("Test exception");
		log.error(ex.getMessage());
		// Act
		advice.handleNotFound(ex);

		// Assert
		Mockito.verify(log).error("Test exception");

	}
}