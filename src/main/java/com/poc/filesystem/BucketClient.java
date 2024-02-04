package com.poc.filesystem;

import com.poc.exception.FileReadingException;
import com.poc.exception.InternalServerException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;

public interface BucketClient {

    /**
     * Envia o arquivo pedido para realização do download
     *
     * @param fileName Nome do arquivo
     *
     * @return Retorna o arquivo em bytes
     *
     * @throws FileReadingException see #FileReadingException
     * @throws InternalServerException see #InternalServerException
     */

    StreamingResponseBody downloadFile(final String fileName) throws FileReadingException, InternalServerException, IOException;

    /**
     * Recebe o arquivo que deve subir para o Bucket. Antes do arquivo ser enviado, a validação do nome do arquivo é realizada.
     * Se o nome não for validado pela regra, uma FileReadingException deve ser lançada.
     *
     * @param multipartFile Arquivo recebido pela requisição
     */

    void uploadFile(final MultipartFile multipartFile);


}
