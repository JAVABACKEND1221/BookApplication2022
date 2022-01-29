package otabekkenjayev.bookapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otabekkenjayev.bookapplication.model.MovieFile;

@Repository
public interface MovieFileRepository extends JpaRepository<MovieFile,Integer> {
    boolean existsByUrl(String url);
}
