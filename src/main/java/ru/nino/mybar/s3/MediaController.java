package ru.nino.mybar.s3;

import io.minio.GetObjectResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

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

    @GetMapping("/images/{category}/{filename}")
    public ResponseEntity<StreamingResponseBody> downloadImage(@PathVariable String category,
                                                               @PathVariable String filename) {
        String key = category + "/" + filename;
        try {
            GetObjectResponse response = minioService.getFile("images", key);

            StreamingResponseBody stream = outputStream -> {
                try (response) { // закрываем корректно
                    response.transferTo(outputStream);
                }
            };

            return ResponseEntity.ok()
                    .contentType(getContentType(filename))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(stream);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении файла из MinIO", e);
        }
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
