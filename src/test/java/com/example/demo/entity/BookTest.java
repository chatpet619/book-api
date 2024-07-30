package com.example.demo.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {

    @Test
    @DisplayName("Test getter and setter")
    void testBookGettersAndSetters() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book1");
        book.setAuthor("Mr.A");
        book.setIsbn("1234567890123");
        book.setPublishedDate(LocalDate.of(2020, 1, 1));

        // Act & Assert
        assertEquals(1L, book.getId());
        assertEquals("Book1", book.getTitle());
        assertEquals("Mr.A", book.getAuthor());
        assertEquals("1234567890123", book.getIsbn());
        assertEquals(13L, book.getIsbn().length());
        assertEquals(LocalDate.of(2020, 1, 1), book.getPublishedDate());
    }
}
