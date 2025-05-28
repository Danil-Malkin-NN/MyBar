package ru.nino.mybar.s3;

import io.minio.GetObjectResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/media")
public class MediaController {

    private final MinioService minioService;

    public MediaController(MinioService minioService) {
        this.minioService = minioService;
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("category") String category,
                                         @RequestParam("file") MultipartFile file) throws BadRequestException {

        return minioService.getStringResponseEntity(category, file);
    }

    @GetMapping("/{category}/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String category, @PathVariable String filename) {

        String key = category + "/" + filename;
        GetObjectResponse image = minioService.getFile("images", key);
        InputStreamResource resource = new InputStreamResource(image);

        return ResponseEntity.ok()
                .contentType(getContentType(filename))
                .body(resource);
    }

    private MediaType getContentType(String filename) {
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (filename.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (filename.endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }
}
