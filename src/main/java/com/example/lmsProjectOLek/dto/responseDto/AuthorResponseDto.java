package com.example.lmsProjectOLek.dto.responseDto;

import com.example.lmsProjectOLek.model.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults( level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorResponseDto {
    Long id;
    String name;
    String biography;
    List<Book> books;
}
