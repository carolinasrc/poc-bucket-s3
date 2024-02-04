package com.poc.helper.mapper;


import com.poc.dto.FileDto;
import com.poc.response.FileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileResponseMapper {

    @Mapping(target = "name", source = "fileDto.fileName")
    @Mapping(target = "statusName", source = "fileDto.statusName")
    @Mapping(target = "fileSize", source = "fileDto.fileSize")
    @Mapping(target = "inputTime", source = "fileDto.loadDate")
    FileResponse toFileResponse(FileDto fileDto);

}
