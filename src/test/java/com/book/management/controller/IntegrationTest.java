package com.book.management.controller;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class IntegrationTest {

    @Container
    private final MySQLContainer mysqlContainer = new MySQLContainer()
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @Test
    void testDatabaseConnection() {
        assertTrue(mysqlContainer.isRunning());
    }
}
