package com.example.lmsProjectOLek.controller;


import com.example.lmsProjectOLek.dto.requestDto.AuthorRequestDto;
import com.example.lmsProjectOLek.dto.responseDto.AuthorResponseDto;
import com.example.lmsProjectOLek.model.Author;
import com.example.lmsProjectOLek.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/author")
public class AuthorController {

    AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity addAuthor(@RequestBody AuthorRequestDto authorRequestDto){
        try{
            Author result = this.authorService.addAuthor(authorRequestDto);
            return new ResponseEntity(result, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getAuhtorById(@PathVariable Long id){
        try{
            AuthorResponseDto result = this.authorService.getAuthorById(id);
            return new ResponseEntity(result, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAuthorById(@PathVariable Long id){
        try{
            String result = this.authorService.deleteAuthor(id);
            return new ResponseEntity(result, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateAuthor(@PathVariable Long id, @RequestBody AuthorRequestDto author){
        try{
            if (author.getName() == null && author.getBiography() == null) {
                throw new IllegalArgumentException("At least one parameter (name or biography) is required for updating.");
            }
            AuthorResponseDto result = this.authorService.updateAuthor(id, author);
            return new ResponseEntity(result, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
