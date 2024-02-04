package com.poc.repository.setters;//package com.poc.minio.repository.setters;

import com.poc.dto.FileDto;
import com.poc.enums.ParameterEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@RequiredArgsConstructor
public final class FileSetter implements PreparedStatementSetter {

    private final FileDto file;

    @Override
    public void setValues(PreparedStatement ps) throws SQLException {

        ps.setString(ParameterEnum.ONE.position(), file.getFileName());
        ps.setLong(ParameterEnum.TWO.position(), file.getFileSize());
        ps.setLong(ParameterEnum.THREE.position(), file.getStatusId());
        ps.setDate(ParameterEnum.FOUR.position(), Date.valueOf(file.getLoadDate()));
        ps.setTimestamp(ParameterEnum.FIVE.position(), Timestamp.valueOf(file.getStartProcessTime()));    }

}
