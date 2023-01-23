package com.book.management.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.management.entity.BookEntity;
import com.book.management.mapper.BeanMapper;
import com.book.management.model.BookDto;
import com.book.management.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BeanMapper mapper;

    public List<BookDto> getBooks() {
        List<BookEntity> books = bookRepository.findAll();
        return books.stream().map(mapper::mapToDto).collect(Collectors.toList());
    }

    public BookDto getBook(Integer id) {
        BookEntity book = bookRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("No book found with id " + id)
        );
        return mapper.mapToDto(book);
    }

    public BookDto saveBook(BookDto book) {
        BookEntity newBook = mapper.mapToEntity(book);
        return mapper.mapToDto(bookRepository.save(newBook));
    }

    public BookDto updateBook(Integer id, BookDto book) {
        if (!bookRepository.existsById(id)) {
            throw new NoSuchElementException("No book found with id " + id);
        }
        BookEntity updatedBook = bookRepository.save(mapper.mapToEntity(book));
        return mapper.mapToDto(bookRepository.save(updatedBook));
    }

    public void deleteBook(Integer id) {
        if (!bookRepository.existsById(id)) {
            throw new NoSuchElementException("No book found with id " + id);
        }
        bookRepository.deleteById(id);
    }
}