package com.book.management.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.book.management.entity.BookEntity;
import com.book.management.model.BookDto;

@RunWith(MockitoJUnitRunner.class)
public class BeanMapperTest {
	private static BeanMapper mapper;

	@BeforeClass
	public static void beforeClass() {
		mapper = new BeanMapper();
		mapper.postConstruct();
	}

	@Test
	public void testPostConstruct() {
		BeanMapper beanMapper = new BeanMapper();
		beanMapper.postConstruct();
	}

	@Test
	public void testMapToDto() {
		BookEntity bookEntity = new BookEntity();
		bookEntity.setId(1);
		bookEntity.setName("Test Title");
		bookEntity.setAuthor("Test Author");

		BookDto bookDto = mapper.mapToDto(bookEntity);

		assertEquals(bookEntity.getId(), bookDto.getId());
		assertEquals(bookEntity.getName(), bookDto.getName());
		assertEquals(bookEntity.getAuthor(), bookDto.getAuthor());
	}

	@Test
	public void testMapToDdd() {
		// Prepare test data
		BookDto bookDto = new BookDto();
		bookDto.setName("Test Title");
		bookDto.setAuthor("Test Author");

		BookEntity bookEntity = mapper.mapToEntity(bookDto);

		assertEquals(bookDto.getId(), bookEntity.getId());
		assertEquals(bookDto.getName(), bookEntity.getName());
		assertEquals(bookDto.getAuthor(), bookEntity.getAuthor());
	}

}
