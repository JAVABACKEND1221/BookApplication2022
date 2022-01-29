package otabekkenjayev.bookapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otabekkenjayev.bookapplication.dto.ApiResponse;
import otabekkenjayev.bookapplication.dto.MovieFileDto;
import otabekkenjayev.bookapplication.model.MovieFile;
import otabekkenjayev.bookapplication.repository.MovieFileRepository;

import java.util.Optional;

@Service
public class MovieFileService {

    @Autowired
    MovieFileRepository movieFileRepository;

    public ApiResponse saveUrl(MovieFileDto movieFileDto){
        MovieFile movieFile = new MovieFile();
        if(!movieFileRepository.existsByUrl(movieFileDto.getUrl())){
            movieFile.setName(movieFileDto.getName());
            movieFile.setUrl(movieFileDto.getUrl());
            movieFile.setActive(movieFileDto.isActive());

            movieFileRepository.save(movieFile);
            return new ApiResponse("Saved",true,movieFile.getUrl());
        }
        return new ApiResponse("This url already exists",false);
    }

    public ApiResponse edit(Integer id, MovieFileDto movieFileDto){
        Optional<MovieFile> byId = movieFileRepository.findById(id);

        if(byId.isPresent()){
            MovieFile editMovie = byId.get();
            editMovie.setName(movieFileDto.getName());
            editMovie.setUrl(movieFileDto.getUrl());
            editMovie.setActive(movieFileDto.isActive());
            movieFileRepository.save(editMovie);
            return new ApiResponse("Edit movie",true,editMovie.getName());
        }
        return new ApiResponse("Movie is not edit",false);
    }

}
