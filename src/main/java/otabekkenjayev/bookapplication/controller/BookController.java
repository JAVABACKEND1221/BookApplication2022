package otabekkenjayev.bookapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import otabekkenjayev.bookapplication.dto.ApiResponse;
import otabekkenjayev.bookapplication.dto.BookDTO;
import otabekkenjayev.bookapplication.model.Book;
import otabekkenjayev.bookapplication.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    BookService bookService;


    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> addbook(@RequestBody BookDTO bookDTO) {
        ApiResponse response = bookService.saveBook(bookDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/allBook")
    public HttpEntity<?> getAll() {
        List<Book> books = bookService.getAllBook();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Book book = bookService.getOneById(id);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> editBook(@PathVariable Integer id, @RequestBody BookDTO bookDTO) {
        ApiResponse edited = bookService.editBook(id, bookDTO);
        return ResponseEntity.ok(edited);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> deleteBook(@PathVariable Integer id) {
        boolean delete = bookService.deleted(id);
        if (delete)
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/byCategory/{id}")
    public HttpEntity<?> getCategoryId(@PathVariable Integer id){
        ApiResponse byCatId = bookService.getByCategoryId(id);
        return ResponseEntity.ok(byCatId);
    }

    @GetMapping("/byAuthor/{id}")
    public HttpEntity<?> getAuthorId(@PathVariable Integer id){
        ApiResponse byAuthorId = bookService.getByAuthorId(id);
        return ResponseEntity.ok(byAuthorId);
    }

}
