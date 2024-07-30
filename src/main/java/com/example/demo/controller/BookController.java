package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.service.BookServiceImpl;
import com.example.demo.utility.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<List<Book>>(bookService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        Optional<Book> book = bookService.findById(id);
        return book.map(ResponseEntity::ok).orElseThrow(()-> new BookNotFoundException(ErrorMessage.ERROR_BOOK_NOT_FOUND + id));
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = bookService.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book book) {
        bookService.findById(id).orElseThrow(() -> new BookNotFoundException(ErrorMessage.ERROR_BOOK_NOT_FOUND + + id));

        book.setId(id);
        Book updatedBook = bookService.save(book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
       Optional<Book> book =  bookService.findById(id);
        if(book.isPresent()){
            bookService.deleteById(id);
            return ResponseEntity.noContent().build();
        }else{
            throw new BookNotFoundException(ErrorMessage.ERROR_BOOK_NOT_FOUND + +id);
        }
    }
}
