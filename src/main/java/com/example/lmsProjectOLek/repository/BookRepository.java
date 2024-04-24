package com.example.lmsProjectOLek.repository;

import com.example.lmsProjectOLek.model.Author;
import com.example.lmsProjectOLek.model.Book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    @Query("SELECT b FROM Book b WHERE b NOT IN (SELECT r.book FROM Rental r WHERE r.returnDate IS NULL)")
    List<Book> findAvailableBooksForRent();

    @Query("SELECT b FROM Book b WHERE b  IN (SELECT r.book FROM Rental r WHERE r.returnDate IS NULL)")
    List<Book> currentlyRented();
    
}
