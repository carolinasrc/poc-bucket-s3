package com.poc.response;

import com.poc.dto.FileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PersistedFileResponse {

    private FileDto data;

}
