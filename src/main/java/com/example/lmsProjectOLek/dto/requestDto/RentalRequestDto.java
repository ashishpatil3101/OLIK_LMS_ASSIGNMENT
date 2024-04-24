package com.example.lmsProjectOLek.dto.requestDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults( level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RentalRequestDto {
    Long bookId;
    String  renterName;
}
