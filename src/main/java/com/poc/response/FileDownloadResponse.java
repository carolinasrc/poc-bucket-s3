package com.poc.response;

import com.poc.dto.FileDownloadDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class FileDownloadResponse {

    private FileDownloadDto data;

}
