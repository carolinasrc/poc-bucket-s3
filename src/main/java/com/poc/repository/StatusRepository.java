package com.poc.repository;

import com.poc.dto.FileStatusDto;
import com.poc.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StatusRepository {

    private static final String DATABASE_ERROR = "Não foi possível acessar a base de dados";

    private static final String FIND_ALL_STATUS = """
        SELECT
          s.id AS id,
          s.nemotecnico AS nemotecnico
        FROM file_status s
        """;

    private final JdbcTemplate jdbcTemplate;

    public List<FileStatusDto> findAllStatus() {
        try {
            return jdbcTemplate.query(FIND_ALL_STATUS, BeanPropertyRowMapper.newInstance(FileStatusDto.class));
        } catch (DataAccessException ex) {
            log.info(DATABASE_ERROR, ex);
            throw new InternalServerException(DATABASE_ERROR);
        }
    }

}
