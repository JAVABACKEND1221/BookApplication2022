package otabekkenjayev.bookapplication.service;


import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import otabekkenjayev.bookapplication.dto.ApiResponse;
import otabekkenjayev.bookapplication.model.AudioFile;
import otabekkenjayev.bookapplication.model.template.FileStatus;
import otabekkenjayev.bookapplication.repository.AudioFileRepository;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class AudioFileService {

    @Autowired
    AudioFileRepository audioFileRepository;

    @Value("${upload.folderAudio}")
    private String uploadFolder;

    private final Hashids hashids;

    public AudioFileService(AudioFileRepository audioFileRepository) {
        this.audioFileRepository = audioFileRepository;
        this.hashids = new Hashids(getClass().getName(), 10);
    }

    public ApiResponse save(MultipartFile multipartFile) {
        AudioFile audioFile = new AudioFile();
        if (!audioFileRepository.existsByName(multipartFile.getOriginalFilename())) {
            audioFile.setName(multipartFile.getOriginalFilename());
            audioFile.setExtension(getExt(multipartFile.getOriginalFilename()));
            audioFile.setFileSize(multipartFile.getSize());
            audioFile.setContentType(multipartFile.getContentType());
            audioFile.setFileStatus(FileStatus.ACTIVE);
            audioFileRepository.save(audioFile);

            Date now = new Date();
            File uploadFolder = new File(String.format("%s/upload_audios/%d/%d/%d/",
                    this.uploadFolder,
                    1900 + now.getYear(),
                    1 + now.getMonth(),
                    now.getDate()));

            if (!uploadFolder.exists() && uploadFolder.mkdirs()) {
                System.out.println("Creat files");
            }

            audioFile.setHashId(hashids.encode(audioFile.getId()));
            audioFile.setUploadPath(String.format("upload_audio/%d/%d/%d/%s.%s",
                    1900 + now.getYear(),
                    1 + now.getMonth(),
                    now.getDate(),
                    audioFile.getHashId(),
                    audioFile.getExtension()));
            audioFileRepository.save(audioFile);
            uploadFolder = uploadFolder.getAbsoluteFile();
            File file = new File(uploadFolder,
                    String.format("%s.%s",
                            audioFile.getHashId(),
                            audioFile.getExtension()));
            try {
                multipartFile.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new ApiResponse("Save", true, audioFile.getHashId());
        }
        return new ApiResponse("This audio already exists", false);
    }

    @Transactional(readOnly = true)
    public AudioFile findByHashId(String hashId) {
        return audioFileRepository.findByHashId(hashId);
    }

    public void delete(String hashId) {
        AudioFile audioFile = findByHashId(hashId);
        File file = new File(String.format("%s/%s", this.uploadFolder, audioFile.getUploadPath()));
        if (file.delete()) {
            audioFileRepository.delete(audioFile);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteAllActive() {
        List<AudioFile> audioFileList = audioFileRepository.findAllByFileStatus(FileStatus.ACTIVE);
        audioFileList.forEach(fileStorage -> {
            delete(fileStorage.getHashId());
        });
    }

    private String getExt(String audioName) {
        String ext = null;
        if (audioName != null && !audioName.isEmpty()) {
            int dot = audioName.lastIndexOf('.');
            if (dot > 0 && dot <= audioName.length() - 2) {
                ext = audioName.substring(dot + 1);
            }
        }
        return ext;
    }
}
