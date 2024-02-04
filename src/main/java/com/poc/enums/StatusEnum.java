package com.poc.enums;

import com.poc.exception.StatusNotFoundException;

public enum StatusEnum {

    FILE_RECEIVED("received", "FILE_RECEIVED"),

    FILE_READY("finished", "FILE_READY"),

    FILE_START_PROCESS("start process", "FILE_START_PROCESS"),

    FILE_END_PROCESS("end process", "FILE_END_PROCESS"),

    FILE_FAIL_PROCESS("fail progress", "FILE_FAIL_PROCESS"),

    FILE_UPLOAD_ERROR("upload error", "FILE_UPLOAD_ERROR"),

    FILE_VALIDATION_ERROR("validation error", "FILE_VALIDATION_ERROR");


    private String name;
    private String nemotecnico;

    StatusEnum(final String name, final String nemotecnico) {
        this.name = name;
        this.nemotecnico = nemotecnico;
    }

    public String getNemotecnico() {
        return nemotecnico;
    }

    public static StatusEnum getByName(String name) {
        for (StatusEnum statusEnum : StatusEnum.values()) {
            if (statusEnum.name.equals(name)) {
                return statusEnum;
            }
        }
        throw new StatusNotFoundException(name);
    }
}
