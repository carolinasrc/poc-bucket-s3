package com.poc.service;

import com.poc.dto.FileDto;
import com.poc.enums.StatusEnum;
import com.poc.helper.mapper.FileResponseMapper;
import com.poc.repository.FileRepository;
import com.poc.response.FileResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final FileResponseMapper responseMapper;

    private static Integer getFirstItem(Integer pageNumber, Integer pageSize) {
        return pageNumber * pageSize;
    }

    public List<FileResponse> findAllFiles(Pageable page) {

        Integer pageNumber = page.getPageNumber();
        Integer pageSize = page.getPageSize();
        Integer firstItem = getFirstItem(pageNumber, pageSize);

        return getFileResponseDtos(fileRepository.findAllFiles(firstItem, pageSize));
    }

    public List<FileResponse> findFilesByStatusId(Pageable page, final String statusId) {
        Integer pageNumber = page.getPageNumber();
        Integer pageSize = page.getPageSize();
        Integer firstItem = getFirstItem(pageNumber, pageSize);

        StatusEnum statusEnum = StatusEnum.getByName(statusId);

        return getFileResponseDtos(fileRepository.findFilesByStatusId(firstItem, pageSize, statusEnum.getNemotecnico()));
    }

    public List<FileResponse> findAllFilesByDate(Pageable page, String loadDate) {
        Integer pageNumber = page.getPageNumber();
        Integer pageSize = page.getPageSize();
        Integer firstItem = getFirstItem(pageNumber, pageSize);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate formattedDate = LocalDate.parse(loadDate, formatterDate);

        return getFileResponseDtos(fileRepository.findFileByDate(firstItem, pageSize, formattedDate.format(formatter)));
    }

    @NotNull
    private List<FileResponse> getFileResponseDtos(List<FileDto> repositoryResponse) {
        return repositoryResponse
            .stream()
            .map(responseMapper::toFileResponse).toList();
    }

    public FileDto addFile(final String fileName, final Long fileSize, final Integer statusId, final LocalDate loadDate, final LocalDateTime startProcessTime) {
        return fileRepository.insertFile(fileName, fileSize, statusId, loadDate, startProcessTime);
    }

}
