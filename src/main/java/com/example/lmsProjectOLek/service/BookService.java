package com.example.lmsProjectOLek.service;

import com.example.lmsProjectOLek.dto.requestDto.BookRequestDto;
import com.example.lmsProjectOLek.dto.responseDto.BookResponseDto;
import com.example.lmsProjectOLek.model.Author;
import com.example.lmsProjectOLek.model.Book;
import com.example.lmsProjectOLek.repository.AuthorRepository;
import com.example.lmsProjectOLek.repository.BookRepository;
import com.example.lmsProjectOLek.transformers.BookTransformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

//    public List<Book> getAllBooks() {
//        return bookRepository.findAll();
//    }

    public Optional<BookResponseDto> getBookById(Long id) throws Exception {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new Exception("Book does not exist.");
        }
        return Optional.of(BookTransformers.prepareBookResponseDto(optionalBook.get(), optionalBook.get().getAuthor()));
    }

    public BookResponseDto createBook(BookRequestDto bookRequestDto) throws Exception {
        Optional<Author> authorExist = authorRepository.findById(bookRequestDto.getAuthorId());
        if (authorExist.isEmpty()) {
            throw new Exception("Author does not exist. Please input a valid Author id.");
        }
        if(bookRequestDto.getIsbn() == null )throw new Exception("isbn code required.");
        if(bookRequestDto.getTitle() == null )throw new Exception("Book Title required.");
        if(bookRequestDto.getPublicationYear()+"" == "") throw new Exception("publicatonyear required.");
        if (bookRequestDto.getPublicationYear() > Long.parseLong(new SimpleDateFormat("yyyy").format(new Date()))) {
            throw new Exception("Please input a valid publication year.");
        }
        if(!isValidISBN(bookRequestDto.getIsbn()))throw new Exception("Input valid isbn code.");
        Book newBook = BookTransformers.prepareBook(bookRequestDto , authorExist.get());
        Book savedBook = bookRepository.save(newBook);
        if (authorExist.get().getBooks() == null) {
            authorExist.get().setBooks(List.of(newBook));
        } else {
            authorExist.get().getBooks().add(newBook);
        }
        Author author = authorRepository.save(authorExist.get());
        return  BookTransformers.prepareBookResponseDto(author.getBooks().get(author.getBooks().size() -1), author);
    }

    public BookResponseDto updateBook(Long bookId, BookRequestDto bookRequestDto) throws Exception {
       if(bookRequestDto.getPublicationYear()+"" == "" && bookRequestDto.getTitle() == null && bookRequestDto.getAuthorId()+"" == "" && bookRequestDto.getIsbn() == null ) throw new Exception("Updation field required");

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            throw new Exception("Book does not exist.");
        }
        Book bookToUpdate = optionalBook.get();
        if (bookRequestDto.getTitle() != null) {
            bookToUpdate.setTitle(bookRequestDto.getTitle());
        }
        if (bookRequestDto.getPublicationYear() != 0) {
            bookToUpdate.setPublicationYear(bookRequestDto.getPublicationYear());
        }
        if (bookRequestDto.getIsbn() != null) {
            bookToUpdate.setIsbn(bookRequestDto.getIsbn());
        }
        if (bookRequestDto.getAuthorId() != 0) {
            Optional<Author> authorOptional = authorRepository.findById(bookRequestDto.getAuthorId());
            if (authorOptional.isEmpty()) {
                throw new Exception("Author does not exist.");
            }
            bookToUpdate.setAuthor(authorOptional.get());
        }

        Book updatedBook = bookRepository.save(bookToUpdate);
        return BookTransformers.prepareBookResponseDto(updatedBook, updatedBook.getAuthor());
    }

    public void deleteBook(Long id) throws Exception {
        if(!bookRepository.existsById(id))throw new Exception("Book does not exist");
        bookRepository.deleteById(id);
    }

    public  boolean isValidISBN(String isbn) {
        // Regular expression for ISBN-10 and ISBN-13
        String regex = "^(?:ISBN(?:-10)?:?\\s)?(?=[0-9X]{10}$|(?=(?:[0-9]+[-\\s]){3})[-\\s0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[-\\s]){4})[-\\s0-9]{17}$)(?:97[89][-\\s]?)?[0-9]{1,5}[-\\s]?[0-9]+[-\\s]?[0-9]+[-\\s]?[0-9X]$";

        // Return true if it matches, false otherwise
        return isbn.matches(regex);
    }


}
