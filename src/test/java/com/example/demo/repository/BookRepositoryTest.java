package com.example.demo.repository;

import com.example.demo.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setTitle("Book1");
        book.setAuthor("Mr.A");
        book.setIsbn("0987654321321");
        book.setPublishedDate(LocalDate.of(2020, 1, 1));
        bookRepository.save(book);
    }

    @Test
    @DisplayName("Book find All")
    void testFindAll() {
        // Act
        List<Book> books = bookRepository.findAll();

        // Assert
        assertFalse(books.isEmpty());
        assertEquals(2, books.size());
    }

    @Test
    @DisplayName("Book find by id")
    void testFindById() {
        // Act
        Optional<Book> foundBook = bookRepository.findById(book.getId());

        // Assert
        assertTrue(foundBook.isPresent());
        assertEquals(book.getTitle(), foundBook.get().getTitle());
    }

    @Test
    @DisplayName("Book save")
    void testSave() {
        // Arrange
        Book newBook = new Book();
        newBook.setTitle("Book2");
        newBook.setAuthor("Mr.B");
        newBook.setIsbn("1987654321321");
        newBook.setPublishedDate(LocalDate.of(2021, 1, 1));


        // Act
        Book savedBook = bookRepository.save(newBook);

        // Assert
        assertNotNull(savedBook.getId());
        assertEquals("Book2", savedBook.getTitle());
    }

    @Test
    @DisplayName("Delete Book by id")
    void testDeleteById() {
        // Act
        bookRepository.deleteById(book.getId());
        Optional<Book> deletedBook = bookRepository.findById(book.getId());

        // Assert
        assertFalse(deletedBook.isPresent());
    }

    @Test
    @DisplayName("Book find by isbn")
    void testFindByIsbn() {
        // Act
        Optional<Book> foundBook = bookRepository.findByIsbn(book.getIsbn());

        // Assert
        assertTrue(foundBook.isPresent());
        assertEquals(book.getTitle(), foundBook.get().getTitle());
    }
}
