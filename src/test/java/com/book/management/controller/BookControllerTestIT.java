package com.book.management.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.book.management.entity.BookEntity;
import com.book.management.model.BookDto;
import com.book.management.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class BookControllerTestIT {
    private static final String URI = "/books";
    private static final String URI_ID = URI + "/{id}";

    @Autowired
    private MockMvc request;

    @Autowired
    private BookRepository repository;

    private BookEntity entity;
    private BookDto dto;
    private ObjectMapper mapper;

    
    @Before
    public void before() {
        entity = new BookEntity(null,"nameEntity","authorEntity",2);
        entity = repository.save(entity);
        dto = new BookDto(null, "nameDto", "authorDto", 3);
        mapper = new ObjectMapper();
    }

    @After
    public void after() {
        repository.deleteAll();
    }

    @Test
    public void testGetBooks() throws Exception {
        request.perform(get(URI))
            .andDo(print())
            .andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$").exists(),
                jsonPath("$").isArray(),
                jsonPath("$.length()").value(1),
                jsonPath("$[0].id").value(entity.getId()),
                jsonPath("$[0].name").value(entity.getName()),
                jsonPath("$[0].author").value(entity.getAuthor()),
                jsonPath("$[0].price").value(entity.getPrice()));
        assertEquals(1, repository.count());
        BookEntity other = repository.findAll().get(0);
        assertEquals(entity, other);
    }

    @Test
    public void testGetBooksEmptyList() throws Exception {
        repository.deleteAll();
        request.perform(get(URI))
            .andDo(print())
            .andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$").exists(),
                jsonPath("$").isArray(),
                jsonPath("$").isEmpty());
        assertEquals(0, repository.count());
    }

    @Test
    public void testGetBook() throws Exception {
        request.perform(get(URI_ID, entity.getId()))
            .andDo(print())
            .andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$").exists(),
                jsonPath("$").isMap(),
                jsonPath("$").isNotEmpty(),
                jsonPath("$.id").value(entity.getId()),
                jsonPath("$.name").value(entity.getName()),
                jsonPath("$.author").value(entity.getAuthor()),
                jsonPath("$.price").value(entity.getPrice()));
        assertEquals(1, repository.count());
    }

    @Test
    public void testGetBookMissing() throws Exception {
    	repository.deleteAll();
    	assertEquals(0, repository.count());
    	request.perform(
    			get(URI_ID, entity.getId()))
    		.andDo(print())
    		.andExpectAll(
    			status().isNotFound());
    	assertEquals(0, repository.count());
    }
    
    @Test
    public void testSaveBook() throws Exception {
    	repository.deleteAll();
    	assertEquals(0, repository.count());
    	request.perform(
    			post(URI)
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(mapper.writeValueAsString(dto)))
    		.andDo(print())
    		.andExpectAll(
    			status().isOk(),
    			content().contentType(MediaType.APPLICATION_JSON),
    			jsonPath("$").exists(),
    			jsonPath("$").isMap(),
    			jsonPath("$").isNotEmpty(),
    			jsonPath("$.id").isNumber(),
    			jsonPath("$.name").value(dto.getName()),
    			jsonPath("$.author").value(dto.getAuthor()),
    			jsonPath("$.price").value(dto.getPrice()));
    	assertEquals(1, repository.count());
    	BookEntity other = repository.findAll().get(0);
    	assertEquals(dto.getName(), other.getName());
    	assertEquals(dto.getAuthor(), other.getAuthor());
    	assertEquals(dto.getPrice(), other.getPrice());
    }

    @Test
    public void testSaveBookInvalid() throws Exception {
    	repository.deleteAll();
    	dto.setName("");
    	assertEquals(0, repository.count());
    	request.perform(
    			post(URI)
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(mapper.writeValueAsString(dto)))
    		.andDo(print())
    		.andExpectAll(
    			status().isBadRequest());
    	assertEquals(0, repository.count());
    }
    
    @Test
    public void testUpdateBook() throws Exception {
    	dto.setId(entity.getId());
    	assertEquals(1, repository.count());
    	request.perform(
    			put(URI_ID, entity.getId())
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(mapper.writeValueAsString(dto)))
    		.andDo(print())
    		.andExpectAll(
    			status().isOk(),
    			content().contentType(MediaType.APPLICATION_JSON),
    			jsonPath("$").exists(),
    			jsonPath("$").isMap(),
    			jsonPath("$").isNotEmpty(),
    			jsonPath("$.id").isNumber(),
    			jsonPath("$.name").value(dto.getName()),
    			jsonPath("$.author").value(dto.getAuthor()),
    			jsonPath("$.price").value(dto.getPrice()));
    	assertEquals(1, repository.count());
    	BookEntity other = repository.findAll().get(0);
    	assertEquals(dto.getId(), other.getId());
    	assertEquals(dto.getName(), other.getName());
    	assertEquals(dto.getAuthor(), other.getAuthor());
    	assertEquals(dto.getPrice(), other.getPrice());
    }

    @Test
    public void testUpdateBookMissing() throws Exception {
    	repository.deleteAll();
    	dto.setId(entity.getId());
    	assertEquals(0, repository.count());
    	request.perform(
    			put(URI_ID, entity.getId())
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(mapper.writeValueAsString(dto)))
    		.andDo(print())
    		.andExpectAll(
    			status().isNotFound());
    	assertEquals(0, repository.count());
    }

    @Test
    public void testUpdateBookInvalidField() throws Exception {
    	dto.setId(entity.getId());
    	dto.setName("");
    	assertEquals(1, repository.count());
    	request.perform(
    			put(URI_ID, entity.getId())
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(mapper.writeValueAsString(dto)))
    		.andDo(print())
    		.andExpectAll(
    			status().isBadRequest());
    	assertEquals(1, repository.count());
    }

    @Test
    public void testUpdateBookDifferentId() throws Exception {
    	dto.setId(entity.getId() + 1);
    	assertEquals(1, repository.count());
    	request.perform(
    			put(URI_ID, entity.getId())
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(mapper.writeValueAsString(dto)))
    		.andDo(print())
    		.andExpectAll(
    			status().isBadRequest());
    	assertEquals(1, repository.count());
    }

    @Test
    public void testDeleteBook() throws Exception {
    	assertEquals(1, repository.count());
    	request.perform(
    			delete(URI_ID, entity.getId()))
    		.andDo(print())
    		.andExpectAll(
    			status().isOk(),
    			content().contentType(MediaType.APPLICATION_JSON),
    			content().string("true"));
    	assertEquals(0, repository.count());
    }

    @Test
    public void testDeleteBookMissing() throws Exception {
    	repository.deleteAll();
    	dto.setId(entity.getId());
    	assertEquals(0, repository.count());
    	request.perform(
    			delete(URI_ID, entity.getId())
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(mapper.writeValueAsString(dto)))
    		.andDo(print())
    		.andExpectAll(
    			status().isNotFound());
    	assertEquals(0, repository.count());
    }
}