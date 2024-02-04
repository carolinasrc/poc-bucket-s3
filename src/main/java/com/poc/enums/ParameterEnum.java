package com.poc.enums;

public enum ParameterEnum {

    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int position;

    ParameterEnum(final int position) {
        this.position = position;
    }

    public int position() {
        return position;
    }
}
