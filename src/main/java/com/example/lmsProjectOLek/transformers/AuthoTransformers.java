package com.example.lmsProjectOLek.transformers;

import com.example.lmsProjectOLek.dto.requestDto.AuthorRequestDto;
import com.example.lmsProjectOLek.dto.responseDto.AuthorResponseDto;
import com.example.lmsProjectOLek.model.Author;
import com.example.lmsProjectOLek.model.Book;

import java.util.*;


public class AuthoTransformers {

    public static Author prepareAuthor(AuthorRequestDto authorRequestDto){

        return Author.builder()
                .name(authorRequestDto.getName())
                .biography(authorRequestDto.getBiography())
                .build();
    }

    public static AuthorResponseDto prepareAuthorResponse(Author author){
        List<Book> books= new ArrayList<>();
        for(Book book: author.getBooks()){
            books.add(Book.builder()
                            .title(book.getTitle())
                            .id(book.getId())
                            .isbn(book.getIsbn())
                            .publicationYear(book.getPublicationYear())
                    .build());
        }
        return AuthorResponseDto.builder()
                .id(author.getId())
                .name(author.getName())
                .biography(author.getBiography())
                .books(books)
                .build();
    }
}
