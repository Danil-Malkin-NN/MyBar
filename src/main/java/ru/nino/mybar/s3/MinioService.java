package ru.nino.mybar.s3;

import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.nino.mybar.entity.Image;
import ru.nino.mybar.repository.impl.ImageRepositoryImpl;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class MinioService {

	private final MinioClient minioClient;

	private final ImageRepositoryImpl imageRepository;

	@Value("${minio.bucket}")
	private String bucket;


	public GetObjectResponse getFile(String key) throws ServerException, InsufficientDataException,
																	   ErrorResponseException, IOException,
																	   NoSuchAlgorithmException, InvalidKeyException,
																	   InvalidResponseException, XmlParserException,
																	   InternalException {
		GetObjectResponse response = minioClient.getObject(GetObjectArgs.builder()
																   .bucket(bucket)
																   .object(key)
																   .build());

		return response;
	}

	@NotNull
	public String uploadImage(String category, MultipartFile file) throws BadRequestException {
		String filename = file.getOriginalFilename();
		String key = category + "/" + filename;

		try (InputStream is = file.getInputStream()) {
			ObjectWriteResponse images = minioClient.putObject(PutObjectArgs.builder()
																	   .bucket(bucket)
																	   .object(key)
																	   .stream(is, file.getSize(), -1)
																	   .contentType(file.getContentType())
																	   .build());

			imageRepository.save(new Image(key));
			return "Файл загружен: " + key;

		} catch (Exception e) {
			throw new BadRequestException(e);
		}
	}
}
