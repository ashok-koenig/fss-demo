package com.example.jwt_oauth2_demo.service;

import com.example.jwt_oauth2_demo.entity.Book;
import com.example.jwt_oauth2_demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {
    BookRepository bookRepository;
    @Autowired
    BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }
    public Book getBookById(Integer id){
        return bookRepository.findById(id).orElse(null);
    }
    @Transactional
    public Book saveBook(Book book){
        return bookRepository.save(book);
    }
    @Transactional
    public void deleteBook(Integer id){
        bookRepository.deleteById(id);
    }

}
