package otabekkenjayev.bookapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otabekkenjayev.bookapplication.model.AppUser;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {

  boolean existsByUsername(String username);

  AppUser findByUsername(String username);

  @Transactional
  void deleteByUsername(String username);
}
