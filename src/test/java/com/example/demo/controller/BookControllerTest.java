package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServiceImpl bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Book1");
        book.setAuthor("Mr.A");
        book.setIsbn("1234567890123");
        book.setPublishedDate(LocalDate.of(2023,1,1));
    }

    @Test
    void getAllBooks() throws Exception {
        given(bookService.findAll()).willReturn(Arrays.asList(book));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Book1"));
    }

    @Test
    void getBookById() throws Exception {
        given(bookService.findById(anyLong())).willReturn(Optional.of(book));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book1"));
    }

    @Test
    void getBookById_NotFound() throws Exception {
        given(bookService.findById(anyLong())).willReturn(Optional.empty());

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addBook() throws Exception {
        given(bookService.findByIsbn(anyString())).willReturn(Optional.empty());
        given(bookService.save(any(Book.class))).willReturn(book);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Book1\", \"author\":\"Mr.A\", \"isbn\":\"1234567890123\", \"publishedDate\":\"2023-01-01\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Book1"));
    }

    @Test
    void updateBook() throws Exception {
        // Original book setup
        given(bookService.findById(anyLong())).willReturn(Optional.of(book));

        // Create an updated book object
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Book2");
        updatedBook.setAuthor("Mr.B");
        updatedBook.setIsbn("1234567890123");
        updatedBook.setPublishedDate(LocalDate.of(2023,1,1));

        // Configure the mock service to return the updated book
        given(bookService.save(any(Book.class))).willReturn(updatedBook);

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Book2\", \"author\":\"Mr.B\", \"isbn\":\"1234567890123\", \"publishedDate\":\"2023-01-01\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book2"))
                .andExpect(jsonPath("$.author").value("Mr.B"))
                .andExpect(jsonPath("$.isbn").value("1234567890123"))
                .andExpect(jsonPath("$.publishedDate").value("2023-01-01"));
    }


    @Test
    void updateBook_NotFound() throws Exception {
        given(bookService.findById(anyLong())).willReturn(Optional.empty());

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Book1\", \"author\":\"Mr.A\", \"isbn\":\"1234567890123\", \"publishedDate\":\"2023-01-01\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteBookNotFound() throws Exception {
        Long bookId = 1L;
        when(bookService.findById(bookId)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).findById(bookId);
        verify(bookService, never()).deleteById(bookId);
    }

    @Test
    public void testDeleteBookSuccess() throws Exception {
        Long bookId = 1L;
        Book book = new Book(); // สร้างหนังสือจำลอง
        when(bookService.findById(bookId)).thenReturn(Optional.of(book));

        mockMvc.perform(delete("/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).findById(bookId);
        verify(bookService, times(1)).deleteById(bookId);
    }
}
