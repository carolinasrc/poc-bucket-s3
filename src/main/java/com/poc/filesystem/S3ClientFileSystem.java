package com.poc.filesystem;

import com.amazonaws.SdkClientException;
import com.amazonaws.util.IOUtils;
import com.poc.config.AwsProperties;
import com.poc.exception.FileReadingException;
import com.poc.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.BufferedInputStream;
import java.io.IOException;

import static com.poc.filesystem.S3Constants.S3_ERROR;
import static com.poc.filesystem.S3Constants.S3_IN_PATH;
import static com.poc.filesystem.S3Constants.TO_READ_FILE;

@Slf4j
@Service
@RequiredArgsConstructor
public final class S3ClientFileSystem implements BucketClient {

    public static final String UPLOAD_ERROR = "Não foi possível fazer o upload do arquivo";

    private final S3Client awsClient;
    private final AwsProperties properties;

    @Override
    public StreamingResponseBody downloadFile(final String fileName) throws FileReadingException, InternalServerException, IOException {

        final var fullPath = S3_IN_PATH + fileName;

        try {
            final var request = GetObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(fullPath)
                .build();

            final var response = awsClient.getObject(request);

            return outputStream -> {
                try (final var inputStream = new BufferedInputStream(response)) {
                    IOUtils.copy(inputStream, outputStream);
                } catch (IOException | IllegalArgumentException e) {
                    log.error(e.getMessage(), e);
                    throw new InternalServerException("Não foi possível tratar o arquivo para retornar para o cliente");
                }
            };
        } catch (S3Exception e) {
            final var message = fileName + "' Erro ao ler arquivo '" + fullPath + "'" + null;
            log.error(message, e);
            throw new FileReadingException(message);
        } catch (SdkClientException e) {
            final var message = fileName + S3_ERROR + TO_READ_FILE + "'" + fullPath + "'" + null;
            log.error(message, e);
            throw new InternalServerException(message);
        }
    }

    @Override
    public void uploadFile(final MultipartFile multipartFile) {
        final var file = S3_IN_PATH + multipartFile.getOriginalFilename();

        try {
            final var requestBody = RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize());
            final var request = PutObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(file)
                .build();

            awsClient.putObject(request, requestBody);
            log.info(multipartFile.getName(), "Upload para S3 finalizado...");
        } catch (S3Exception | IOException e) {
            log.info(multipartFile.getName(), UPLOAD_ERROR, e);
            throw new FileReadingException(UPLOAD_ERROR);
        } catch (SdkClientException e) {
            log.info((multipartFile.getName()), UPLOAD_ERROR, e);
            throw new InternalServerException(UPLOAD_ERROR);
        }
    }

}
