package com.example.lmsProjectOLek.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RentalResponseDto {
    private Long id;
    private String renterName;
    private Date rentalDate;
    private Date returnDate;
    private BookResponseDto bookResponseDto;

}
