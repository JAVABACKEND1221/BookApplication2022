package otabekkenjayev.bookapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otabekkenjayev.bookapplication.dto.ApiResponse;
import otabekkenjayev.bookapplication.dto.BookDTO;
import otabekkenjayev.bookapplication.exception.ResourceNotFoundException;
import otabekkenjayev.bookapplication.model.Author;
import otabekkenjayev.bookapplication.model.Book;
import otabekkenjayev.bookapplication.model.Category;
import otabekkenjayev.bookapplication.repository.AuthorRepository;
import otabekkenjayev.bookapplication.repository.BookRepository;
import otabekkenjayev.bookapplication.repository.CategoryRepository;
import otabekkenjayev.bookapplication.repository.PdfFileRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    PdfFileRepository pdfFileRepository;

    public ApiResponse saveBook(BookDTO bookDTO) {
        Book book = new Book();
        if (!bookRepository.existsByName(bookDTO.getName())) {

            Optional<Category> optionalCategory = categoryRepository.findById(bookDTO.getCatId());
            Category category = categoryRepository.findById(bookDTO.getCatId())
                    .orElseThrow(() -> new ResourceNotFoundException("category", "id", bookDTO.getCatId()));

            Optional<Author> optionalAuthor = authorRepository.findById(bookDTO.getAuthorId());
            Author author = authorRepository.findById(bookDTO.getAuthorId())
                    .orElseThrow(() -> new ResourceNotFoundException("author", "id", bookDTO.getAuthorId()));
            book.setName(bookDTO.getName());

            book.setCategory(category);
            book.setCategory(optionalCategory.get());

            book.setAuthor(author);
            book.setAuthor(optionalAuthor.get());

            book.setImageUrl(bookDTO.getImageUrl());
            book.setAudioUrl(bookDTO.getAudioUrl());
            book.setFileUrl(bookDTO.getFileUrl());
            book.setMovieUrl(bookDTO.getMovieUrl());

            bookRepository.save(book);
            return new ApiResponse("Saved!", true,book.getName());
        }
        return new ApiResponse("Bunday kitob bor!", false);
    }


    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    public Book getOneById(Integer id) {
        Optional<Book> byId = bookRepository.findById(id);
        return byId.orElse(null);
    }

    public ApiResponse editBook(Integer id, BookDTO bookDTO) {
        Optional<Book> byId = bookRepository.findById(id);
        Optional<Category> optionalCategory = categoryRepository.findById(bookDTO.getCatId());
        if (byId.isPresent()) {
            Book editBook = byId.get();
            editBook.setName(bookDTO.getName());
            editBook.setCategory(optionalCategory.get());
            bookRepository.save(editBook);
            return new ApiResponse("Edit Book",true,editBook.getName());
        }
        return new ApiResponse("Book is not edit",false);
    }

    public boolean deleted(Integer id) {
        try {
            bookRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ApiResponse getByCategoryId(Integer id) {
        List<Book> bookList = bookRepository.findByCategory_Id(id);
        return new ApiResponse("This", true, bookList);
    }

    public ApiResponse getByAuthorId(Integer id){
        List<Book> bookList = bookRepository.findByAuthor_Id(id);
        return new ApiResponse("This",true,bookList);
    }

}
