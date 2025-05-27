package ru.nino.mybar.minio;

import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static io.minio.ObjectWriteArgs.MIN_MULTIPART_SIZE;

@SpringBootTest
public class SimpleMinioUsageTest {

    @Autowired
    MinioClient minioClient;

    @Test
    public void saveTest() throws ServerException, InsufficientDataException, ErrorResponseException, IOException,
                                  NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
                                  XmlParserException, InternalException {

        InputStream resource = getClass().getResourceAsStream("/icno/Белый русский.png");

        minioClient.putObject(PutObjectArgs
                                      .builder()
                                      .bucket("user1")
                                      .object("Белый русский.png")
                                      .stream(resource, resource.available(), MIN_MULTIPART_SIZE)
                                                      .build());

    }

    @Test
    @SneakyThrows
    public void createBucket() {
        minioClient.makeBucket(MakeBucketArgs.builder()
                                       .bucket("user1")
                                       .build());
    }

}
