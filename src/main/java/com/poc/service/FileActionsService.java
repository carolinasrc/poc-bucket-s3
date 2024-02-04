package com.poc.service;

import com.poc.dto.FileDto;
import com.poc.filesystem.BucketClient;
import com.poc.singleton.StatusSingleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileActionsService {

    private final BucketClient s3Client;
    private final FileService fileService;
    private final StatusSingleton statusSingleton;

    public StreamingResponseBody downloadFile(final String fileName) throws IOException {
        log.info(fileName, "Iniciando download do arquivo...");

        final var file = s3Client.downloadFile(fileName);

        log.info(fileName, "Arquivo obtido na AWS...");

        return file;
    }

    public FileDto uploadFile(final MultipartFile file) {
        log.info(file.getName(), "Iniciando upload do arquivo...");

        s3Client.uploadFile(file);
        final var persistedFile = persistFileInformation(file);

        log.info(file.getName(), "Arquivo enviado");
        return persistedFile;
    }

    private FileDto persistFileInformation(final MultipartFile file) {

        final var fileName = file.getOriginalFilename();
        final var fileSize = file.getSize();
        final var statusId = statusSingleton.getReceived().getId();
        final var loadDate = LocalDate.now();
        final var startProcessTime = LocalDateTime.now();

        return fileService.addFile(fileName, fileSize, statusId, loadDate, startProcessTime);
    }

}

