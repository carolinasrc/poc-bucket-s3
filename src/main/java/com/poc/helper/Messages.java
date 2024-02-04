package com.poc.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Messages {

    public static final String TO_READ_FILE = "para ler o arquivo";
    public static final String FILE_NOT_FOUND = "Arquivo não encontrado na base de dados";
    public static final String FILE_ALREADY_EXISTS = "O arquivo enviado já existe. Considere modificar o nome do arquivo, mantendo o padrão de formatação";

}
