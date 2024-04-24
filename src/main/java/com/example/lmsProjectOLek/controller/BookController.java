package com.example.lmsProjectOLek.controller;

import com.example.lmsProjectOLek.dto.requestDto.BookRequestDto;
import com.example.lmsProjectOLek.dto.responseDto.BookResponseDto;
import com.example.lmsProjectOLek.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @GetMapping("/")
    public  String senmsg(){
        return "hey bhiay";
    }

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{bookId}")
    public ResponseEntity getBookById(@PathVariable Long bookId) {
        try {
            BookResponseDto result = bookService.getBookById(bookId).orElse(null);
            if (result != null) {
                return new ResponseEntity(result, HttpStatus.OK);
            } else {
                return new ResponseEntity("Book not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping
    public ResponseEntity createBook(@RequestBody BookRequestDto book) {
        try {
            BookResponseDto result = bookService.createBook(book);
            return new ResponseEntity(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity deleteBook(@PathVariable Long bookId) {
        try {
            bookService.deleteBook(bookId);
            return new ResponseEntity("Book deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{bookId}")
    public ResponseEntity updateBook(@PathVariable Long bookId, @RequestBody BookRequestDto book) {
        try {
            BookResponseDto result = bookService.updateBook(bookId, book);
            if (result != null) {
                return new ResponseEntity(result, HttpStatus.OK);
            } else {
                return new ResponseEntity("Book not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
