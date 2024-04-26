package com.example.lmsProjectOLek.controller;

import com.example.lmsProjectOLek.dto.requestDto.RentalRequestDto;
import com.example.lmsProjectOLek.dto.responseDto.RentalResponseDto;
import com.example.lmsProjectOLek.model.Book;
import com.example.lmsProjectOLek.model.Rental;
import com.example.lmsProjectOLek.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rental")
public class RentalController {

    private RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService){
        this.rentalService = rentalService;
    }

    @PostMapping("/rent")
    public ResponseEntity createRental(RentalRequestDto rentalRequestDto){
        try{
            RentalResponseDto result = rentalService.createRental(rentalRequestDto);
            return new ResponseEntity(result, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/availableBooks")
    public ResponseEntity notRentedBooks(){
        try{
            return new ResponseEntity(rentalService.notRentedBooks(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/rentedBooks")
    public ResponseEntity getBooksCurrentlyRented(){
        try{
           return new ResponseEntity(rentalService.getBooksCurrentlyRented(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
   
    @PostMapping("/returnBook/{rentalId}")
    public ResponseEntity returnBook(@PathVariable Long rentalId){
        try {
            return new ResponseEntity<>(rentalService.returnBook(rentalId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/records")
    public ResponseEntity getAllrecords(){
        try {
            return new ResponseEntity<>(rentalService.rentalRecords(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
