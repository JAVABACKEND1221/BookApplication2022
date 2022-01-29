package otabekkenjayev.bookapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import otabekkenjayev.bookapplication.dto.ApiResponse;
import otabekkenjayev.bookapplication.dto.AuthorDTO;
import otabekkenjayev.bookapplication.service.AuthorService;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> addAuthor(@RequestBody AuthorDTO authorDTO){
        ApiResponse response = authorService.addAuthor(authorDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public HttpEntity<?> allAuthor(){
        ApiResponse all = authorService.getAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/one/{id}")
    public HttpEntity<?> getOneAuthor(@PathVariable Integer id){
        ApiResponse oneById = authorService.getOneById(id);
        return ResponseEntity.ok(oneById);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> deleteAuthor(@PathVariable Integer id){
        ApiResponse delete = authorService.delete(id);
        return ResponseEntity.ok(delete);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> editAuthor(@PathVariable Integer id, @RequestBody AuthorDTO authorDTO){
        ApiResponse edited = authorService.edit(id, authorDTO);
        return ResponseEntity.ok(edited);
    }
}
