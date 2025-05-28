package ru.nino.mybar.s3;

import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import org.apache.coyote.BadRequestException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class MinioService {

    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public GetObjectResponse getFile(String bucket, String key) {
        try (GetObjectResponse response = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(key)
                        .build())) {

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Не удалось получить объект из MinIO: " + key, e);
        }
    }

    @NotNull
    public String getStringResponseEntity(String category, MultipartFile file) throws BadRequestException {
        String filename = file.getOriginalFilename();
        String key = category + "/" + filename;

        try (InputStream is = file.getInputStream()) {
            ObjectWriteResponse images = minioClient.putObject(PutObjectArgs.builder()
                                                                       .bucket("images")
                                                                       .object(key)
                                                                       .stream(is, file.getSize(), -1)
                                                                       .contentType(file.getContentType())
                                                                       .build());
            return "Файл загружен: " + key;

        } catch (Exception e) {
            throw new BadRequestException(e);
        }
    }
}
