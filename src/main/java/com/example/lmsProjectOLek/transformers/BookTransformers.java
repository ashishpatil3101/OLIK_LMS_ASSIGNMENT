package com.example.lmsProjectOLek.transformers;

import com.example.lmsProjectOLek.dto.requestDto.BookRequestDto;
import com.example.lmsProjectOLek.dto.responseDto.AuthorResponseDto;
import com.example.lmsProjectOLek.dto.responseDto.BookResponseDto;
import com.example.lmsProjectOLek.model.Author;
import com.example.lmsProjectOLek.model.Book;

public class BookTransformers {
    public static BookResponseDto prepareBookResponseDto(Book book, Author author){
       System.out.println(author.getName());
        AuthorResponseDto authReposneDto = new AuthorResponseDto();
        authReposneDto.setId(author.getId());
        authReposneDto.setName(author.getName());
        authReposneDto.setBiography(author.getBiography());
        return BookResponseDto.builder()
                .title(book.getTitle())
                .publicationYear(book.getPublicationYear())
                .id(book.getId())
                .author(authReposneDto)
                .isbn(book.getIsbn())
                .build();
    }

    public static Book prepareBook (BookRequestDto bookRequestDto, Author author){

        return Book.builder()
                .title(bookRequestDto.getTitle())
                .isbn(bookRequestDto.getIsbn())
                .publicationYear(bookRequestDto.getPublicationYear())
                .author(author)
                .build();
    }
}
