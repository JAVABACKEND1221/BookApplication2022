package otabekkenjayev.bookapplication.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otabekkenjayev.bookapplication.model.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {


   Category findByName(String name);
   boolean existsByName(String name);
   List<Category> findAllByParentCategoryId(Integer id);
}
