package com.poc.filesystem;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class S3Constants {

    public static final String S3_ROOT_FOLDER = "s3/buckets/test-file-bucket-s3";

    public static final String SLASH = "/";

    public static final String S3_IN_PATH = S3_ROOT_FOLDER + SLASH;

    public static final String S3_ERROR = "Erro ao tentar tentar conectar com o servidor s3";

    public static final String TO_READ_FILE = "Erro ao ler o arquivo";

}
