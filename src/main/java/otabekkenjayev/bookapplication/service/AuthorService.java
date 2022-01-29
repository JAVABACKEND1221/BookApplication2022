package otabekkenjayev.bookapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otabekkenjayev.bookapplication.dto.ApiResponse;
import otabekkenjayev.bookapplication.dto.AuthorDTO;
import otabekkenjayev.bookapplication.model.Author;
import otabekkenjayev.bookapplication.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public ApiResponse addAuthor(AuthorDTO authorDto){
        boolean existsByName = authorRepository.existsByName(authorDto.getName());

        if (existsByName){
            return new ApiResponse("This Author already exist",false);
        }

        Author author = new Author();
        author.setName(authorDto.getName());

        authorRepository.save(author);
        return new ApiResponse("Saved",true,author.getName());
    }

    public ApiResponse delete(Integer id){
        authorRepository.deleteById(id);
        return new ApiResponse("Deleted!",true);
    }

    public ApiResponse edit(Integer id, AuthorDTO authorDTO){
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if ((!optionalAuthor.isPresent()))
            return new ApiResponse("Author not found", false);

        Author author = optionalAuthor.get();
        author.setName(authorDTO.getName());


        Author authorEdit = authorRepository.save(author);

        return new ApiResponse("Edited!", true,authorEdit.getName());
    }

    public ApiResponse getAll(){
        List<Author> authorList = authorRepository.findAll();
        return new ApiResponse("This",true, authorList);
    }

    public ApiResponse getOneById(Integer id){
        Optional<Author> byId = authorRepository.findById(id);
        return new ApiResponse("This",true,byId);
    }

    public ApiResponse deleteAll(){
        authorRepository.deleteAll();
        return new ApiResponse("All Author Deleted!", true);
    }
}
