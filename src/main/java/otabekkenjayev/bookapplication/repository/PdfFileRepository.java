package otabekkenjayev.bookapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otabekkenjayev.bookapplication.model.PdfFile;
import otabekkenjayev.bookapplication.model.template.FileStatus;

import java.util.List;

@Repository
public interface PdfFileRepository extends JpaRepository<PdfFile, Integer> {

    List<PdfFile> findAllByFileStatus(FileStatus fileStatus);

    PdfFile findByHashId(String hashId);


    boolean existsByName(String originalFilename);
}
