package otabekkenjayev.bookapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otabekkenjayev.bookapplication.model.ImageFile;
import otabekkenjayev.bookapplication.model.template.FileStatus;

import java.util.List;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile, Integer> {

    List<ImageFile> findAllByFileStatus(FileStatus fileStatus);

    ImageFile findByHashId(String hashId);

    boolean existsByName(String originalFilename);
}
