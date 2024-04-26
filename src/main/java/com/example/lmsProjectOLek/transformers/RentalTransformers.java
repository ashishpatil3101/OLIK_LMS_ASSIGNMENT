package com.example.lmsProjectOLek.transformers;

import com.example.lmsProjectOLek.dto.requestDto.RentalRequestDto;
import com.example.lmsProjectOLek.dto.responseDto.AuthorResponseDto;
import com.example.lmsProjectOLek.dto.responseDto.BookResponseDto;
import com.example.lmsProjectOLek.dto.responseDto.RentalResponseDto;
import com.example.lmsProjectOLek.model.Author;
import com.example.lmsProjectOLek.model.Book;
import com.example.lmsProjectOLek.model.Rental;

import java.util.Date;

public class RentalTransformers {
    public static Rental prepareRental(RentalRequestDto rentalRequestDto, Book book){

        return Rental.builder()
                .book(book)
                .rentalDate(new Date())
                .renterName(rentalRequestDto.getRenterName())
                .build();
    }

    public static RentalResponseDto prepareRentalResponse(Rental rental){
        Book book= rental.getBook();
        System.out.println(book.getAuthor().getName());
        AuthorResponseDto author= AuthorResponseDto.builder()
        .id(book.getId())
        .name(book.getAuthor().getName())
        .build();
        BookResponseDto newBook = BookResponseDto.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .publicationYear(book.getPublicationYear())
                .title(book.getTitle())
                .author(author)
                .build();
        return RentalResponseDto.builder()
                .id(rental.getId())
                .renterName(rental.getRenterName())
                .rentalDate(rental.getRentalDate())
                .returnDate(rental.getReturnDate())
                .bookResponseDto(newBook)
                .build();

    }
}
