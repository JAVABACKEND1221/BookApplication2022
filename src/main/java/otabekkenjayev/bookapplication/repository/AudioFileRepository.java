package otabekkenjayev.bookapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otabekkenjayev.bookapplication.model.AudioFile;
import otabekkenjayev.bookapplication.model.template.FileStatus;

import java.util.List;

@Repository
public interface AudioFileRepository extends JpaRepository<AudioFile, Integer> {

    List<AudioFile> findAllByFileStatus(FileStatus fileStatus);

    AudioFile findByHashId(String hashId);

    boolean existsByName(String originalFilename);
}
