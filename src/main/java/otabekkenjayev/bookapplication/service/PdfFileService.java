package otabekkenjayev.bookapplication.service;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import otabekkenjayev.bookapplication.dto.ApiResponse;
import otabekkenjayev.bookapplication.model.PdfFile;
import otabekkenjayev.bookapplication.model.template.FileStatus;
import otabekkenjayev.bookapplication.repository.PdfFileRepository;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class PdfFileService {

    private final PdfFileRepository pdfFileRepository;

    @Value("${upload.folderFile}")
    private String uploadFolder;

    private final Hashids hashids;

    public PdfFileService(PdfFileRepository pdfFileRepository) {
        this.pdfFileRepository = pdfFileRepository;
        this.hashids = new Hashids(getClass().getName(), 10);
    }

    public ApiResponse save(MultipartFile multipartFile) {

        PdfFile pdfFile = new PdfFile();
        if (!pdfFileRepository.existsByName(multipartFile.getOriginalFilename())) {
            pdfFile.setName(multipartFile.getOriginalFilename());
            pdfFile.setExtension(getExt(multipartFile.getOriginalFilename()));
            pdfFile.setFileSize(multipartFile.getSize());
            pdfFile.setContentType(multipartFile.getContentType());
            pdfFile.setFileStatus(FileStatus.ACTIVE);
            pdfFileRepository.save(pdfFile);

            Date now = new Date();
            java.io.File uploadFolder = new java.io.File(String.format("%s/upload_files/%d/%d/%d/",
                    this.uploadFolder,
                    1900 + now.getYear(),
                    1 + now.getMonth(),
                    now.getDate()));

            if (!uploadFolder.exists() && uploadFolder.mkdirs()) {
                System.out.println("Creat files");
            }

            pdfFile.setHashId(hashids.encode(pdfFile.getId()));
            pdfFile.setUploadPath(String.format("upload_file/%d/%d/%d/%s.%s",
                    1900 + now.getYear(),
                    1 + now.getMonth(),
                    now.getDate(),
                    pdfFile.getHashId(),
                    pdfFile.getExtension()));
            pdfFileRepository.save(pdfFile);
            uploadFolder = uploadFolder.getAbsoluteFile();
            java.io.File file1 = new java.io.File(uploadFolder,
                    String.format("%s.%s",
                            pdfFile.getHashId(),
                            pdfFile.getExtension()));

            try {
                multipartFile.transferTo(file1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ApiResponse("Save", true, pdfFile.getHashId());
        }
        return new ApiResponse("This file already exsist", false);
    }

    @Transactional(readOnly = true)
    public PdfFile findByHashId(String hashId) {
        return pdfFileRepository.findByHashId(hashId);
    }

    public void delete(String hashId) {
        PdfFile pdfFile = findByHashId(hashId);
        java.io.File file1 = new java.io.File(String.format("%s/%s", this.uploadFolder, pdfFile.getUploadPath()));
        if (file1.delete()) {
            pdfFileRepository.delete(pdfFile);
        }
    }

    public void NotActive(String hashId) {
        PdfFile pdfFile = findByHashId(hashId);
        pdfFile.setFileStatus(FileStatus.DRAFT);
        pdfFileRepository.save(pdfFile);

        new ApiResponse("This", true, pdfFile.getName());
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteAllActive() {
        List<PdfFile> pdfFileList = pdfFileRepository.findAllByFileStatus(FileStatus.ACTIVE);

        pdfFileList.forEach(pdfFile -> {
            delete(pdfFile.getHashId());
        });
    }

    private String getExt(String fileName) {
        String ext = null;
        if (fileName != null && !fileName.isEmpty()) {
            int dot = fileName.lastIndexOf('.');
            if (dot > 0 && dot <= fileName.length() - 2) {
                ext = fileName.substring(dot + 1);
            }
        }
        return ext;
    }
}
