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
import otabekkenjayev.bookapplication.model.AudioFile;
import otabekkenjayev.bookapplication.service.AudioFileService;

import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/audio")
public class AudioFileController {

    @Autowired
    AudioFileService audioFileService;

    @Value("${upload.folderAudio}")
    private String uploadFolder;

    public AudioFileController(AudioFileService audioFileService) {
        this.audioFileService = audioFileService;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> upload(@RequestParam("audio") MultipartFile multipartFile) {
        ApiResponse apiResponse = audioFileService.save(multipartFile);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/download/{hashId}")
    public HttpEntity<?> downloadAudio(@PathVariable String hashId) throws IOException {

        AudioFile audioFile = audioFileService.findByHashId(hashId);
        return ResponseEntity.ok().
                header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; audioName\"" +
                                URLEncoder.encode(audioFile.getName()))
                .contentType(MediaType.parseMediaType(audioFile.getContentType()))
                .contentLength(audioFile.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s", uploadFolder, audioFile.getUploadPath())));
    }

    @DeleteMapping("/delete/{hashId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> delete(@PathVariable String hashId){
        audioFileService.delete(hashId);
        return ResponseEntity.ok(" delete audio");
    }
}
