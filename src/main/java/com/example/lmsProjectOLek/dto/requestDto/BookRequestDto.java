package com.example.lmsProjectOLek.dto.requestDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults( level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookRequestDto {
    int publicationYear;
    String title;

    long authorId;

    String isbn;

}
