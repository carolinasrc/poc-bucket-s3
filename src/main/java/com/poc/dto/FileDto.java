package com.poc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

    @JsonIgnore
    private Long id;

    private String fileName;

    private String statusName;

    @JsonIgnore
    private Integer statusId;

    private Long fileSize;

    private LocalDate loadDate;

    @JsonIgnore
    private LocalDateTime startProcessTime;

    public FileDto(final String fileName, final Long fileSize, final Integer statusId, final LocalDate loadDate,
        final LocalDateTime startProcessTime
                  ) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.statusId = statusId;
        this.loadDate = loadDate;
        this.startProcessTime = startProcessTime;
    }

}
