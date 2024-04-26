package com.example.lmsProjectOLek.service;

import com.example.lmsProjectOLek.dto.requestDto.RentalRequestDto;
import com.example.lmsProjectOLek.dto.responseDto.BookResponseDto;
import com.example.lmsProjectOLek.dto.responseDto.RentalResponseDto;
import com.example.lmsProjectOLek.model.Book;
import com.example.lmsProjectOLek.model.Rental;
import com.example.lmsProjectOLek.repository.BookRepository;
import com.example.lmsProjectOLek.repository.RentalRepository;
import com.example.lmsProjectOLek.transformers.BookTransformers;
import com.example.lmsProjectOLek.transformers.RentalTransformers;
import com.zaxxer.hikari.pool.HikariProxyCallableStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class RentalService {

    RentalRepository rentalRepository;
    BookRepository bookRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository,BookRepository bookRepository){
        this.rentalRepository=rentalRepository;
        this.bookRepository=bookRepository;
    }
    //create rental
     public RentalResponseDto createRental(RentalRequestDto rentalRequestDto) throws Exception {
        System.out.println(findOverdueRentals());
        Optional<Book> book = bookRepository.findById(rentalRequestDto.getBookId());
        if(book.isEmpty())throw new Exception("Book does not exist.");
        if(!isBookAvailableForRent(rentalRequestDto.getBookId())) throw new Exception("Book is already rented.");
        Rental rental = RentalTransformers.prepareRental(rentalRequestDto, book.get());
        book.get().getTransactions().add(rental);
        Book savedBook = bookRepository.save(book.get());

        return RentalTransformers.prepareRentalResponse(savedBook.getTransactions().get(savedBook.getTransactions().size() -1));
    }



    //books available for rent
    public List<BookResponseDto> notRentedBooks() {
        List<Book> rentals = bookRepository.findAvailableBooksForRent();
        List<BookResponseDto> result= new ArrayList<>();
        for(Book book: rentals)result.add(BookTransformers.prepareBookResponseDto(book, book.getAuthor()));
        return result;   
    }

    //overdue rentals
    public List<Rental> findOverdueRentals() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -14);
        Date date = calendar.getTime();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return rentalRepository.findOverdueRentals(sqlDate);
    }
    //rented books
    public List<BookResponseDto> getBooksCurrentlyRented() {
        List<BookResponseDto> currentlyRentedBooks = new ArrayList<>();
        List<Book> rentals = bookRepository.currentlyRented();
        for (Book book : rentals) {
            currentlyRentedBooks.add(BookTransformers.prepareBookResponseDto(book,book.getAuthor()));
        }
        return currentlyRentedBooks;
    }

    public RentalResponseDto returnBook(Long id) throws Exception{

        Optional<Rental> rental = rentalRepository.findById(id);
        if(rental.isEmpty()) throw new Exception("Rental record does not exist");
        System.out.println(rental.get().getReturnDate());
        if(rental.get().getReturnDate() != null)throw new Exception("Book is already returned");
        rental.get().setReturnDate(new Date());
        Rental  savedRental =  rentalRepository.save(rental.get());

        RentalResponseDto rentalResponseDto =  RentalTransformers.prepareRentalResponse(savedRental);
        rentalResponseDto.setReturnDate(savedRental.getReturnDate());
        return rentalResponseDto;
    }
    
    
    public boolean isBookAvailableForRent(Long bookId) {
        List<Rental> rentals = rentalRepository.findAllByBookId(bookId);
        if (rentals.isEmpty()) {
            return true;
        } else {

            for (Rental rental : rentals) {
                if (rental.getReturnDate() == null) {
                    return false;
                }
            }
            return true;
        }
    }


    public List<RentalResponseDto> rentalRecords(){
        List<RentalResponseDto>result  = new ArrayList<>();
        List<Rental> renatals = rentalRepository.findAll();
        for(Rental rental : renatals)result.add(RentalTransformers.prepareRentalResponse(rental));
        return result;
    }
}
