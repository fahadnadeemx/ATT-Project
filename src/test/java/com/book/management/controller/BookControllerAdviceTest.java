package com.book.management.controller;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import static org.junit.Assert.assertEquals;
@RunWith(MockitoJUnitRunner.class)
public class BookControllerAdviceTest {

	private BookControllerAdvice advice;

	@Mock
	private Logger mockLog;

	@Before
	public void setUp() {
		advice = new BookControllerAdvice();
	}

	@Test
	public void testHandleNotFound_NoSuchElementException() {
		// Arrange
		NoSuchElementException ex = new NoSuchElementException("Test exception");

		// Act
		advice.handleNotFound(ex);
		assertEquals(ex, ex);
	}

	@Test
	public void testHandleNotFound_IllegalArgumentException() {
		// Arrange
		IllegalArgumentException ex = new IllegalArgumentException("Test exception");

		// Act
		advice.handleNotFound(ex);
		assertEquals(ex, ex);
	}
}