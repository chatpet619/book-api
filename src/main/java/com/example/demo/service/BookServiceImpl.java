package com.example.demo.service;

import com.example.demo.config.AppConfigProperties;
import com.example.demo.entity.Book;
import com.example.demo.exception.BookAlreadyExistsException;
import com.example.demo.exception.InvalidParameterException;
import com.example.demo.repository.BookRepository;
import com.example.demo.utility.ErrorMessage;
import com.example.demo.utils.StringUtils;
import com.example.demo.utils.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final AppConfigProperties appConfigProperties;

    @Autowired
    private BookRepository bookRepository;

    public BookServiceImpl(AppConfigProperties appConfigProperties) {
        this.appConfigProperties = appConfigProperties;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book save(Book book) {
        validateBook(book);
        return bookRepository.save(book);
    }
    public void validateBook(Book book) {
        if (Validate.isNullObject(book)) {
            throw new InvalidParameterException(ErrorMessage.ERROR_INVALID_PARAMETER);
        }

        if (StringUtils.hasMoreThanXCharacters(book.getIsbn(), appConfigProperties.getIsbnMaxLength())) {
            throw new InvalidParameterException(
                    ErrorMessage.ERROR_INVALID_MAX_LENGTH + appConfigProperties.getIsbnMaxLength() + " character");
        }

        if(bookRepository.existsByIsbn(book.getIsbn())){
            throw new BookAlreadyExistsException(
                    ErrorMessage.ERROR_BOOK_ALREADY_FOUND+book.getIsbn());
        }

    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }




}
