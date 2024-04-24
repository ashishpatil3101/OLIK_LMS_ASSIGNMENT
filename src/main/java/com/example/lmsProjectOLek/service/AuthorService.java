package com.example.lmsProjectOLek.service;

import com.example.lmsProjectOLek.dto.requestDto.AuthorRequestDto;
import com.example.lmsProjectOLek.dto.responseDto.AuthorResponseDto;
import com.example.lmsProjectOLek.model.Author;
import com.example.lmsProjectOLek.repository.AuthorRepository;
import com.example.lmsProjectOLek.transformers.AuthoTransformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public Author addAuthor(AuthorRequestDto authorRequestDto){

        Author authorObj = AuthoTransformers.prepareAuthor(authorRequestDto);
        Author savedAuthor = authorRepository.save(authorObj);

        return savedAuthor;
    }
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public AuthorResponseDto getAuthorById(Long id) throws Exception {
        if(!authorRepository.existsById(id))throw new Exception("Auhtor does not exist");
        Optional<Author> author =  authorRepository.findById(id);
        return AuthoTransformers.prepareAuthorResponse(author.get());
    }


    public AuthorResponseDto updateAuthor(Long id, AuthorRequestDto author) throws Exception {
        if (!authorRepository.existsById(id)) {
            throw  new Exception( "Author does not exists." );
        }
        Optional<Author> authorExist = authorRepository.findById(id);
        if(author.getName() != null)authorExist.get().setName(author.getName());
        if(author.getBiography() != null)authorExist.get().setBiography(author.getBiography());
        Author savedAuthor= authorRepository.save(authorExist.get());
        return AuthoTransformers.prepareAuthorResponse(savedAuthor);
    }

    public String deleteAuthor(Long id) throws Exception {
        if(!authorRepository.existsById(id)) throw new Exception("Author does not exist");
        authorRepository.deleteById(id);
        return "Author deleted successfully.";
    }
}
