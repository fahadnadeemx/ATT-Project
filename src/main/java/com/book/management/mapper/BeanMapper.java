package com.book.management.mapper;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.book.management.entity.BookEntity;
import com.book.management.model.BookDto;

@Component
public class BeanMapper {
    private ModelMapper modelMapper;

    @PostConstruct
    public void postConstruct() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public BookDto mapToDto(BookEntity book) {
        return modelMapper.typeMap(BookEntity.class, BookDto.class)
            .map(book);
    }

    public BookEntity mapToEntity(BookDto book) {
        return modelMapper.typeMap(BookDto.class, BookEntity.class)
            .map(book);
    }

	public ModelMapper getModelMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
