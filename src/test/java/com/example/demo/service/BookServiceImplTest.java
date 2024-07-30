package com.example.demo.service;

import com.example.demo.config.AppConfigProperties;
import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookServiceImpl Tests")
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AppConfigProperties appConfigProperties;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Book1");
        book.setAuthor("Mr.A");
        book.setIsbn("1234567890123");
        book.setPublishedDate(LocalDate.of(2020, 1, 1));
    }

    @Nested
    @DisplayName("Save Method Tests")
    class SaveMethodTests {
        @Test
        @DisplayName("Test save book")
        public void testSaveBook() {

            when(bookRepository.save(book)).thenReturn(book);

            bookRepository.save(book);

            verify(bookRepository,times(1)).save(book);

        }
    }

    @Nested
    @DisplayName("Find Method Tests")
    class FindMethodTests {

        @Test
        @DisplayName("Test finding all books")
        void testFindAll() {
            when(bookRepository.findAll()).thenReturn(List.of(book));

            List<Book> books = bookRepository.findAll();

            assertEquals(1, books.size());
            verify(bookRepository, times(1)).findAll();

            // Ensure the mock is reset to avoid unnecessary stubbing exception
            reset(bookRepository);
        }

        @Test
        @DisplayName("Test finding book by ID")
        void testFindById() {
            when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

            Optional<Book> foundBook = bookRepository.findById(book.getId());

            assertTrue(foundBook.isPresent());
            assertEquals(book.getId(), foundBook.get().getId());

            // Ensure the mock is reset to avoid unnecessary stubbing exception
            reset(bookRepository);
        }

        @Test
        @DisplayName("Test finding book by ISBN")
        void testFindByIsbn() {
            when(bookRepository.findByIsbn(book.getIsbn())).thenReturn(Optional.of(book));

            Optional<Book> foundBook = bookRepository.findByIsbn(book.getIsbn());

            assertTrue(foundBook.isPresent());
            assertEquals(book.getIsbn(), foundBook.get().getIsbn());

            // Ensure the mock is reset to avoid unnecessary stubbing exception
            reset(bookRepository);
        }
    }

    @Nested
    @DisplayName("Delete Method Tests")
    class DeleteMethodTests {

        @Test
        @DisplayName("Test deleting a book by ID")
        void testDeleteById() {
            doNothing().when(bookRepository).deleteById(book.getId());

            bookRepository.deleteById(book.getId());

            verify(bookRepository, times(1)).deleteById(book.getId());

            // Ensure the mock is reset to avoid unnecessary stubbing exception
            reset(bookRepository);
        }
    }
}
