package com.book.management.mapper;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BeanMapperTest {
    private static BeanMapper mapper;

    @BeforeClass
    public static void beforeClass() {
        mapper = new BeanMapper();
        mapper.postConstruct();
    }

    @Test
    public void testMapToDtoBook() {

    }

    @Test
    public void testMapToEntityBook() {

    }
}

