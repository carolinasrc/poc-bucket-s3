package com.poc.repository;

import com.poc.dto.FileDto;
import com.poc.exception.InternalServerException;
import com.poc.helper.LogGenerator;
import com.poc.repository.setters.FileSetter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FileRepository {

    private static final String DATA_ACCESS_ERROR = "Não foi possível acessar a base de dados para obter informações do arquivo desejado";

    private static final String FILE_INFO_ERROR = "Não foi possível acessar a base de dados para obter informações dos arquivos";

    private static final String FIND_ALL_FILES = """
        SELECT
           f.id AS id,
           f.name AS fileName,
           s.name AS statusName,
           f.load_date AS loadDate,
           f.start_process_time AS startProcessTime,
           f.file_size AS fileSize
         FROM file f
         inner join file_status s on s.id = f.status_id
         ORDER BY loadDate
         LIMIT ?, ?
         """;

    private static final String FIND_FILES_BY_NAME = """
        SELECT
          f.id,
          f.name AS fileName,
          t.status_id AS statusId,
          f.load_date AS loadDate,
          f.start_process_time AS startProcessTime,
          f.file_size AS fileSize
        FROM file f WHERE f.name in (%s)
        """;

    private static final String FIND_ALL_FILES_BY_DATE = """
        SELECT
           f.id AS id,
           f.name AS fileName,
           s.name AS statusName,
           f.load_date AS loadDate,
           f.start_process_time AS startProcessTime,
           f.file_size AS fileSize
         FROM file f
         inner join file_status s on s.id = f.status_id
        WHERE f.load_date = ?
        LIMIT ?, ?
        """;

    private static final String FIND_FILES_BY_STATUS_ID = """
        SELECT
          f.id AS id,
          f.name AS fileName,
          s.name AS statusName,
          f.load_date AS loadDate,
          f.start_process_time AS startProcessTime,
          f.file_size AS fileSize
        FROM file f
        INNER JOIN file_status s
        ON s.id = f.status_id
        WHERE s.nemotecnico = ?
        LIMIT ?, ?
        """;

    private static final String INSERT_FILE = """
        INSERT INTO file (name, file_size, status_id, load_date, start_process_time)
        VALUE(?,?,?,?,?)
        """;

    private static final String EXISTS_FILE = """
        SELECT id
        FROM file
        WHERE name = %s
        """;

    private final JdbcTemplate jdbcTemplate;

    public List<FileDto> findAllFiles(Integer firstItem, Integer pageSize) {
        try {
            return jdbcTemplate.query(FIND_ALL_FILES, BeanPropertyRowMapper.newInstance(FileDto.class), firstItem, pageSize);
        } catch (DataAccessException ex) {
            log.info(DATA_ACCESS_ERROR, ex);
            throw new InternalServerException(DATA_ACCESS_ERROR);
        }
    }

    public List<FileDto> findFileByDate(Integer firstItem, Integer pageSize, final String businessDate) {
        try {
            final var result = jdbcTemplate.query(FIND_ALL_FILES_BY_DATE,
                BeanPropertyRowMapper.newInstance(FileDto.class), businessDate, firstItem, pageSize);
            return !result.isEmpty() ? result : null;
        } catch (DataAccessException ex) {
            log.info(DATA_ACCESS_ERROR, ex);
            throw new InternalServerException(DATA_ACCESS_ERROR);
        }
    }

    public List<FileDto> findFilesByStatusId(Integer firstItem, Integer pageSize, final String statusId) {
        try {
            final var result = jdbcTemplate.query(FIND_FILES_BY_STATUS_ID,
                BeanPropertyRowMapper.newInstance(FileDto.class), statusId, firstItem, pageSize);
            return !result.isEmpty() ? result : null;
        } catch (DataAccessException ex) {
            log.info(FILE_INFO_ERROR, ex);
            throw new InternalServerException(FILE_INFO_ERROR);
        }
    }

    public FileDto insertFile(final String fileName, final Long fileSize, final Integer statusId, final LocalDate loadDate, final LocalDateTime startProcessTime) {

        try {
            final var file = new FileDto(fileName, fileSize, statusId, loadDate, startProcessTime);
            jdbcTemplate.update(INSERT_FILE, new FileSetter(file));

            log.info(LogGenerator.logMsg(fileName, "Informações do arquivo adicionado na base de dados"));

            final var result = jdbcTemplate.query(String.format(FIND_FILES_BY_NAME, "?"),
                BeanPropertyRowMapper.newInstance(FileDto.class),
                fileName);

            return !result.isEmpty() ? result.get(0) : null;
        } catch (DataAccessException ex) {
            final var message = LogGenerator.errorMsg(fileName, "Não foi possível adicionar o arquivo na base de dados", ex);
            log.info(message, ex);
            throw new InternalServerException(message);
        }
    }

    public boolean existsFile(final String name) {
        try {
            final var result = jdbcTemplate.query(String.format(EXISTS_FILE, "?"), BeanPropertyRowMapper.newInstance(String.class), name);

            return !result.isEmpty();
        } catch (DataAccessException ex) {
            final var message = LogGenerator.errorMsg(name, "Não foi possível encontrar o arquivo na base de dados", ex);
            log.info(message, ex);
            throw new InternalServerException(message);
        }
    }

}
