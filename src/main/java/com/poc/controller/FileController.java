package com.poc.controller;

import com.poc.config.ApiBasicResponses;
import com.poc.response.FileDownloadResponse;
import com.poc.response.ListFilesResponse;
import com.poc.response.PersistedFileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@ApiBasicResponses
@RequestMapping("/v1")
public interface FileController {

    @Tag(name = "Lista todos os arquivos", description = "Consulta paginada que retorna uma lista de arquivos")
    @GetMapping("/list-files")
    @ApiResponse(responseCode = "200",
        description = "Arquivos listados com sucesso",
        content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ListFilesResponse.class)))
    ResponseEntity<ListFilesResponse> findAllFiles(@PageableDefault(size = 20) Pageable page);

    @Tag(name = "Lista arquivos por status", description = "Consulta paginada que retorna uma lista de arquivos pelo status informado")
    @GetMapping(value = "/list-files/status/{status}")
    ResponseEntity<ListFilesResponse> listFilesByStatus(@PageableDefault(size = 20) Pageable page, @PathVariable("status") String status);

    @Tag(name = "Lista arquivos por data", description = "Consulta paginada que retorna uma lista de arquivos pela data informada")
    @GetMapping(value = "/list-files/date/{date}")
    ResponseEntity<ListFilesResponse> listFilesByDate(@PageableDefault(size = 20) Pageable page, @PathVariable("date") String loadDate);

    @Operation(summary = "Geração do link para download de arquivo solicitado pelo usuário")
    @GetMapping(value = "/download-file", produces = APPLICATION_OCTET_STREAM_VALUE)
    @ApiResponse(responseCode = "200",
        description = "Arquivo enviado com sucesso",
        content = @Content(mediaType = APPLICATION_OCTET_STREAM_VALUE, schema = @Schema(implementation = FileDownloadResponse.class)))
    ResponseEntity<StreamingResponseBody> downloadFile(@RequestParam final String fileName) throws IOException;

    @Operation(summary = "Upload de arquivo")
    @PostMapping(value = "/upload-file",
        consumes = MULTIPART_FORM_DATA_VALUE,
        produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200",
        description = "Arquivo recebido com sucesso",
        content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = PersistedFileResponse.class)))
    ResponseEntity<PersistedFileResponse> uploadFile(@RequestPart final MultipartFile file) throws IOException;

}