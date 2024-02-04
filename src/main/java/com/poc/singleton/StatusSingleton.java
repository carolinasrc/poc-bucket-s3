package com.poc.singleton;

import com.poc.dto.FileStatusDto;
import com.poc.enums.StatusEnum;
import com.poc.repository.StatusRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.poc.enums.StatusEnum.FILE_END_PROCESS;
import static com.poc.enums.StatusEnum.FILE_FAIL_PROCESS;
import static com.poc.enums.StatusEnum.FILE_READY;
import static com.poc.enums.StatusEnum.FILE_RECEIVED;
import static com.poc.enums.StatusEnum.FILE_START_PROCESS;
import static com.poc.enums.StatusEnum.FILE_UPLOAD_ERROR;
import static com.poc.enums.StatusEnum.FILE_VALIDATION_ERROR;

@Service
@Scope("singleton")
@RequiredArgsConstructor
public class StatusSingleton {

    @Getter
    private FileStatusDto received;

    @Getter
    private FileStatusDto finished;

    @Getter
    private FileStatusDto startProcess;

    @Getter
    private FileStatusDto endProcess;

    @Getter
    private FileStatusDto failProgress;

    @Getter
    private FileStatusDto uploadError;

    @Getter
    private FileStatusDto validationError;

    @Getter
    private List<FileStatusDto> statusList;

    private final StatusRepository statusRepository;

    @PostConstruct
    public void init() {
        statusList = statusRepository.findAllStatus();

        received = getStatus(FILE_RECEIVED);
        finished = getStatus(FILE_READY);
        startProcess = getStatus(FILE_START_PROCESS);
        endProcess = getStatus(FILE_END_PROCESS);
        failProgress = getStatus(FILE_FAIL_PROCESS);
        uploadError = getStatus(FILE_UPLOAD_ERROR);
        validationError = getStatus(FILE_VALIDATION_ERROR);
    }

    private FileStatusDto getStatus(final StatusEnum status) {
        return statusList.stream()
            .filter(s -> s.getNemotecnico().equals(status.name()))
            .findFirst()
            .orElse(null);
    }

}