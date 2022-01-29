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
import otabekkenjayev.bookapplication.model.ImageFile;
import otabekkenjayev.bookapplication.service.ImageFileService;

import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/image")
public class ImageFileController {

    @Autowired
    ImageFileService imageFileService;

    @Value("${upload.folderImage}")
    private String uploadFolder;

    public ImageFileController(ImageFileService imageFileService) {
        this.imageFileService = imageFileService;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> upload(@RequestParam("image") MultipartFile multipartFile) {
        ApiResponse apiResponse = imageFileService.save(multipartFile);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/download/{hashId}")
    public HttpEntity<?> downloadImage(@PathVariable String hashId) throws IOException {

        ImageFile imageFile = imageFileService.findByHashId(hashId);
        return ResponseEntity.ok().
                header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; imageName\"" +
                                URLEncoder.encode(imageFile.getName()))
                .contentType(MediaType.parseMediaType(imageFile.getContentType()))
                .contentLength(imageFile.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s", uploadFolder, imageFile.getUploadPath())));
    }

    @DeleteMapping("/delete/{hashId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> delete(@PathVariable String hashId){
        ApiResponse apiResponse = imageFileService.delete(hashId);
        return ResponseEntity.ok(apiResponse);
    }
}
