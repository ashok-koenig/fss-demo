package com.example.jwt_oauth2_demo.controller;

import com.example.jwt_oauth2_demo.entity.Book;
import com.example.jwt_oauth2_demo.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    BookService bookService;
    BookController(BookService bookService){
        this.bookService = bookService;
    }
    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getABook(@PathVariable Integer id){
        return bookService.getBookById(id);
    }

    @PostMapping
    public Book createBook(@RequestBody Book book){
        return bookService.saveBook(book);
    }
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Integer id, @RequestBody Book book){
        book.setId(id);
        return bookService.saveBook(book);
    }
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Integer id){
        bookService.deleteBook(id);
    }
}
