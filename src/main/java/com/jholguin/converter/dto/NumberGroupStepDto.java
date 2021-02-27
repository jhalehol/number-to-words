package com.jholguin.converter.dto;

import com.jholguin.converter.exception.ConversionException;
import lombok.Data;

import java.text.DecimalFormat;

@Data
public class NumberGroupStepDto {

    private static final int GROUP_MAX_LENGTH = 3;
    private static final int INDEX_FIRST_DIGIT = 0;
    private static final int INDEX_SECOND_DIGIT = 1;
    private static final int INDEX_THIRD_DIGIT = 2;
    private static final String GROUP_FORMAT = "#000";

    private Integer number;
    private String stringNumber;

    public NumberGroupStepDto(String numberGroup) throws ConversionException {
        if (numberGroup.length() > GROUP_MAX_LENGTH) {
            throw new ConversionException("Invalid group length");
        }

        try {
            initialize(Integer.parseInt(numberGroup));
        } catch (NumberFormatException ex) {
            throw new ConversionException("Unable to convert number group to number");
        }
    }

    public int getFirstDigit() {
        return getDigit(INDEX_FIRST_DIGIT);
    }

    public int getSecondDigit() {
        return getDigit(INDEX_SECOND_DIGIT);
    }

    public int getThirdDigit() {
        return getDigit(INDEX_THIRD_DIGIT);
    }

    public String getTenPart() {
        return stringNumber.substring(INDEX_SECOND_DIGIT, INDEX_THIRD_DIGIT + 1);
    }

    private void initialize(int numberGroup) {
        DecimalFormat numberFormat = new DecimalFormat(GROUP_FORMAT);
        stringNumber = numberFormat.format(numberGroup);
        number = numberGroup;
    }

    private int getDigit(final int index) {
        final String digit = stringNumber.substring(index, index + 1);
        return Integer.parseInt(digit);
    }
}
