package otabekkenjayev.bookapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import otabekkenjayev.bookapplication.dto.ApiResponse;
import otabekkenjayev.bookapplication.dto.MovieFileDto;
import otabekkenjayev.bookapplication.service.MovieFileService;

@RestController
@RequestMapping("/api/movie")
public class MovieFileController {

    @Autowired
    MovieFileService movieFileService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> addMovie(@RequestBody MovieFileDto movieFileDto){
        ApiResponse response = movieFileService.saveUrl(movieFileDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody MovieFileDto movieFileDto){
        ApiResponse edited = movieFileService.edit(id,movieFileDto);
        return ResponseEntity.ok(edited);
    }
}
