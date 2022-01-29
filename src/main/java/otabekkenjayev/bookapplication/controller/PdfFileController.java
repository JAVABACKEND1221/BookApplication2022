package otabekkenjayev.bookapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import otabekkenjayev.bookapplication.dto.ApiResponse;
import otabekkenjayev.bookapplication.model.PdfFile;
import otabekkenjayev.bookapplication.service.PdfFileService;

import java.io.IOException;
import java.net.URLEncoder;


@RestController
@RequestMapping("/api/file")
public class PdfFileController {

    @Autowired
    PdfFileService pdfFileService;

    @Value("${upload.folderFile}")
    private String uploadFolder;


    public PdfFileController(PdfFileService pdfFileService) {
        this.pdfFileService = pdfFileService;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> upload(@RequestParam("file") MultipartFile multipartFile){
        ApiResponse apiResponse = pdfFileService.save(multipartFile);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/download/{hashId}")
    public HttpEntity<?> downloadFile(@PathVariable String hashId) throws IOException{
        PdfFile pdfFile = pdfFileService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; fileName\"" +
                                URLEncoder.encode(pdfFile.getName()))
                .contentType(MediaType.parseMediaType(pdfFile.getContentType()))
                .contentLength(pdfFile.getFileSize())
                .body(new FileUrlResource(String.format("%s/&s",uploadFolder, pdfFile.getUploadPath())));
    }

    @DeleteMapping("/delete/{hashId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> delete(@PathVariable String hashId){
        pdfFileService.delete(hashId);
        return ResponseEntity.ok(" delete file");
    }

    @PostMapping("/notActive/{hashId}")
    private HttpEntity<?> notActive(@PathVariable String hashId){
        pdfFileService.NotActive(hashId);
        return ResponseEntity.ok(" This file status is DRAFT");
    }
}
