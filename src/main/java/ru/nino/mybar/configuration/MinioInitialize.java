package ru.nino.mybar.configuration;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MinioInitialize {

	private final MinioClient minioClient;

	@Value("${minio.bucket}")
	String bucket;

	@PostConstruct
	private void init() throws ServerException, InsufficientDataException, ErrorResponseException, IOException,
							   NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
							   XmlParserException, InternalException {

		final boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
																	  .bucket(bucket)
																	  .build());

		if (!bucketExists) {
			log.info("Creating bucket {}", bucket);
			minioClient.makeBucket(MakeBucketArgs.builder()
										   .bucket(bucket)
										   .build());
		}

	}

}
