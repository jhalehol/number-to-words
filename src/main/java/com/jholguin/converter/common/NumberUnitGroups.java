package com.jholguin.converter.common;

import com.jholguin.converter.exception.ConversionException;

public enum NumberUnitGroups {

    HUNDRED(1, "hundred"),
    THOUSAND(2, "thousand"),
    MILLION(3, "million"),
    BILLION(4, "billion");

    private String name;
    private int value;

    NumberUnitGroups(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String nameFromGroup(int group) throws ConversionException {
        switch (group) {
            case 1:
                return HUNDRED.getName();
            case 2:
                return THOUSAND.getName();
            case 3:
                return MILLION.getName();
            case 4:
                return BILLION.getName();
            default:
                throw new ConversionException("Group number not found");
        }
    }
}
