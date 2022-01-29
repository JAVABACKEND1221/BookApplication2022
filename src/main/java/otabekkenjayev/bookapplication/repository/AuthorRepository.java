package otabekkenjayev.bookapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otabekkenjayev.bookapplication.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    boolean existsByName(String name);
}
