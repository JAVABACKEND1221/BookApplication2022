package otabekkenjayev.bookapplication.service;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import otabekkenjayev.bookapplication.dto.ApiResponse;
import otabekkenjayev.bookapplication.model.ImageFile;
import otabekkenjayev.bookapplication.model.template.FileStatus;
import otabekkenjayev.bookapplication.repository.ImageFileRepository;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ImageFileService {

    private final ImageFileRepository imageFileRepository;

    @Value("${upload.folderImage}")
    private String uploadFolder;

    private final Hashids hashids;


    public ImageFileService(ImageFileRepository imageFileRepository) {
        this.imageFileRepository = imageFileRepository;
        this.hashids = new Hashids(getClass().getName(), 10);
    }

    public ApiResponse save(MultipartFile multipartFile) {
        ImageFile imageFile = new ImageFile();
        if (!imageFileRepository.existsByName(multipartFile.getOriginalFilename())) {
            imageFile.setName(multipartFile.getOriginalFilename());
            imageFile.setExtension(getExt(multipartFile.getOriginalFilename()));
            imageFile.setFileSize(multipartFile.getSize());
            imageFile.setContentType(multipartFile.getContentType());
            imageFile.setFileStatus(FileStatus.ACTIVE);
            imageFileRepository.save(imageFile);

            Date now = new Date();
            File uploadFolder = new File(String.format("%s/upload_images/%d/%d/%d/",
                    this.uploadFolder,
                    1900 + now.getYear(),
                    1 + now.getMonth(),
                    now.getDate()));

            if (!uploadFolder.exists() && uploadFolder.mkdirs()) {
                System.out.println("Creat files");
            }

            imageFile.setHashId(hashids.encode(imageFile.getId()));
            imageFile.setUploadPath(String.format("upload_image/%d/%d/%d/%s.%s",
                    1900 + now.getYear(),
                    1 + now.getMonth(),
                    now.getDate(),
                    imageFile.getHashId(),
                    imageFile.getExtension()));
            imageFileRepository.save(imageFile);
            uploadFolder = uploadFolder.getAbsoluteFile();
            File file = new File(uploadFolder,
                    String.format("%s.%s",
                            imageFile.getHashId(),
                            imageFile.getExtension()));
            try {
                multipartFile.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ApiResponse("Save", true, imageFile.getHashId());
        }
        return new ApiResponse("This image already exists", false);
    }

    @Transactional(readOnly = true)
    public ImageFile findByHashId(String hashId) {
        return imageFileRepository.findByHashId(hashId);
    }

    public ApiResponse delete(String hashId) {
        ImageFile imageFile = findByHashId(hashId);
        File file = new File(String.format("%s/%s", this.uploadFolder, imageFile.getUploadPath()));
        if (file.delete()) {
            imageFileRepository.delete(imageFile);
            return new ApiResponse("ImageFile is delete", true, imageFile.getName());
        }
        return new ApiResponse("ImageFile is not delete", false);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteAllActive() {
        List<ImageFile> imageFileList = imageFileRepository.findAllByFileStatus(FileStatus.ACTIVE);
        imageFileList.forEach(fileStorage -> {
            delete(fileStorage.getHashId());
        });
    }

    private String getExt(String imageName) {
        String ext = null;
        if (imageName != null && !imageName.isEmpty()) {
            int dot = imageName.lastIndexOf('.');
            if (dot > 0 && dot <= imageName.length() - 2) {
                ext = imageName.substring(dot + 1);
            }
        }
        return ext;
    }
}
