CREATE TABLE `file_status` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Id da Tabela',
  `nemotecnico` varchar(30) NOT NULL COMMENT 'Nemotecnico (Nome Referência) do Status',
  `name` varchar(30) NOT NULL COMMENT 'Nome do Status',
  `description` varchar(4000) NOT NULL COMMENT 'Descrição do Status',
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
    UNIQUE (nemotecnico)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO file_status (id, nemotecnico, name, description) values (1, 'FILE_RECEIVED', 'received', 'Status que indica que o arquivo está pronto.');
INSERT INTO file_status (id, nemotecnico, name, description) values (2, 'FILE_READY', 'finished', 'Status que indica que o arquivo está pronto.');
INSERT INTO file_status (id, nemotecnico, name, description) values (3, 'FILE_START_PROCESS', 'start process', 'Status que indica o início do processo de geração do arquivo.');
INSERT INTO file_status (id, nemotecnico, name, description) values (4, 'FILE_END_PROCESS', 'end process', 'Status que indica o fim do processo de geração do arquivo.');
INSERT INTO file_status (id, nemotecnico, name, description) values (5, 'FILE_FAIL_PROCESS', 'fail process', 'Status que indica a falha no processamento do arquivo.');
INSERT INTO file_status (id, nemotecnico, name, description) values (6, 'FILE_UPLOAD_ERROR', 'upload error', 'Status que indica erro ao fazer o upload do arquivo.');
INSERT INTO file_status (id, nemotecnico, name, description) values (7, 'FILE_VALIDATION_ERROR', 'validation error', 'Status que indica que o arquivo gerado é inválido.');

SELECT * FROM file_status;

CREATE TABLE IF NOT EXISTS file (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Id da Tabela',
	name VARCHAR(300) NOT NULL UNIQUE COMMENT 'Path do arquivo no S3',
	status_id BIGINT NOT NULL COMMENT 'Id do Status',
	load_date DATE NOT NULL COMMENT 'Data real dos dados processados.',
	start_process_time TIMESTAMP(6) NOT NULL COMMENT 'Data-hora de início do processamento/geração do arquivo',
	file_size BIGINT COMMENT 'Tamanho aproximado do arquivo'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO file (id, name, status_id, load_date, start_process_time, file_size) VALUES ('1', 'credentials(1).json', '1', '2024-02-03', '2024-03-03 00:00:00.000000','183');

SELECT * FROM file;