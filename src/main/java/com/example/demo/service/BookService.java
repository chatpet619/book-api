package com.example.demo.service;

import com.example.demo.entity.Book;

import java.util.List;
import java.util.Optional;


public interface BookService {

    public List<Book> findAll();
    public Optional<Book> findById(Long id);
    public Book save(Book book);
    public void deleteById(Long id);
    public Optional<Book> findByIsbn(String isbn);
}
