package otabekkenjayev.bookapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otabekkenjayev.bookapplication.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByCategory_Id(Integer category_id);

    boolean existsByName(String name);

    Optional<Book> findByName(String name);

    List<Book> findByAuthor_Id(Integer author_id);
}
