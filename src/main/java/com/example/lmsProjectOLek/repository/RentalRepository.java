package com.example.lmsProjectOLek.repository;

import com.example.lmsProjectOLek.model.Book;
import com.example.lmsProjectOLek.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental,Long> {
    List<Rental> findAllByBookId(Long bookId);

    List<Rental> findAllByRenterName(String renterName);
   

    List<Rental> findAllByReturnDateBeforeAndReturnDateNotNull(java.util.Date today);
    
    @Query("SELECT r.book from Rental r where returnDate IS NULL")
    List<Book> currentlyRented();

    @Query("SELECT r FROM Rental r WHERE r.rentalDate < :date")
    List<Rental> findOverdueRentals(@Param("date") Date date);
}
