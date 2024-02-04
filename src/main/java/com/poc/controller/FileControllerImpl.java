package com.poc.controller;

import com.poc.dto.FileDto;
import com.poc.response.FileEntityResponse;
import com.poc.response.FileResponse;
import com.poc.response.ListFilesResponse;
import com.poc.service.FileActionsService;
import com.poc.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public final class FileControllerImpl implements FileController {

    private final FileService fileService;
    private final FileActionsService fileActionsService;

    @Override
    public ResponseEntity<ListFilesResponse> findAllFiles(Pageable page) {
        List<FileResponse> allFiles = fileService.findAllFiles(page);
        ListFilesResponse listFilesResponseDto = new ListFilesResponse(allFiles);
        return new ResponseEntity<>(listFilesResponseDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ListFilesResponse> listFilesByStatus(Pageable page, String status) {

        List<FileResponse> getFilesByStatus = fileService.findFilesByStatusId(page, status);
        ListFilesResponse statusFileResponse = new ListFilesResponse(getFilesByStatus);
        return new ResponseEntity<>(statusFileResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ListFilesResponse> listFilesByDate(Pageable page, final String loadDate) {

        List<FileResponse> getFilesByDate = fileService.findAllFilesByDate(page, loadDate);
        ListFilesResponse fileResponseDtoList = new ListFilesResponse(getFilesByDate);
        return new ResponseEntity<>(fileResponseDtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<StreamingResponseBody> downloadFile(final String fileName) throws IOException {
        final var file = fileActionsService.downloadFile(fileName);
        final var httpHeaders = new HttpHeaders();
        httpHeaders.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());

        return new ResponseEntity<>(file, httpHeaders, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<FileEntityResponse> uploadFile(final MultipartFile file) throws IOException {
        FileDto fileEntityDto = fileActionsService.uploadFile(file);
        final var responseDTO = new FileEntityResponse(fileEntityDto);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
