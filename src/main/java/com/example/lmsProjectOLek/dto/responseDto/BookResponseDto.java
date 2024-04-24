package com.example.lmsProjectOLek.dto.responseDto;

import com.example.lmsProjectOLek.model.Rental;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults( level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponseDto {
    Long id;
    String title;
    String isbn;
    int publicationYear;
    List<Rental> rentals = new ArrayList<>();
    AuthorResponseDto author;
}
