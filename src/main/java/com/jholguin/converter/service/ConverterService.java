package com.jholguin.converter.service;

import com.jholguin.converter.dto.NumberGroupStepDto;
import com.jholguin.converter.exception.ConversionException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

import static com.jholguin.converter.common.NumberDefinition.BASIC_NUMBERS;
import static com.jholguin.converter.common.NumberDefinition.BILLION_UNIT_GROUP;
import static com.jholguin.converter.common.NumberDefinition.HUNDRED_UNIT_GROUP;
import static com.jholguin.converter.common.NumberDefinition.TENS_NUMBERS;
import static com.jholguin.converter.common.NumberDefinition.UNITS_DEFINITION;
import static java.lang.Math.abs;

@Component
public class ConverterService {

    private static final String MAX_NUMBER_DIGITS_FORMAT = "#000000000000";
    private static final int GROUP_LENGTH = 3;
    private static final String NEGATIVE_SYMBOL = "-";
    private static final String NEGATIVE_WORD = "(negative) ";

    /**
     * Convert a given number into the representation of the number in words in English
     * @param input Given number to convert, max/min values allowed by integers
     * @return The number in the string representation
     * @throws ConversionException Throws an exception if something fail in the number conversion
     */
    public String convertNumberToString(String input) throws ConversionException {
        final int number = validateInput(input);
        // Format the number string in the max number of digits expected for the conversion
        // to divide it into groups of three
        final DecimalFormat maxDigitsFormat = new DecimalFormat(MAX_NUMBER_DIGITS_FORMAT);
        final String formattedNumber = maxDigitsFormat.format(number);
        final String numberWords = StringUtils.normalizeSpace(processNumberGroups(formattedNumber));
        final boolean isNegative = input.contains(NEGATIVE_SYMBOL);
        final String negativeWord = isNegative ? NEGATIVE_WORD : "";

        return negativeWord + numberWords;
    }

    private String processNumberGroups(final String formattedNumber) throws ConversionException {
        int nextGroupIndex = 0;
        final StringBuilder completeNumberWords = new StringBuilder();
        // Process each group of number of three to gets the word representation, according to the
        // their number unit
        for (int groupIndex = BILLION_UNIT_GROUP; groupIndex >= HUNDRED_UNIT_GROUP; groupIndex--) {
            final String groupNumber = formattedNumber.substring(nextGroupIndex, nextGroupIndex + GROUP_LENGTH);
            final NumberGroupStepDto groupNumberStep = new NumberGroupStepDto(groupNumber);
            // Only process group numbers if they are greater than zero or is the last group
            if (groupNumberStep.getNumber() > 0 || groupIndex == HUNDRED_UNIT_GROUP) {
                final String groupWord = getGroupNumberWord(groupNumberStep);
                final String unitsWord = groupIndex == HUNDRED_UNIT_GROUP ?
                        "" : " " + UNITS_DEFINITION.get(groupIndex) + " ";

                completeNumberWords.append(groupWord)
                        .append(unitsWord);
            }

            nextGroupIndex += GROUP_LENGTH;
        }

        return StringUtils.normalizeSpace(completeNumberWords.toString());
    }

    private String getGroupNumberWord(NumberGroupStepDto groupNumber) throws ConversionException {
        final int number = groupNumber.getNumber();
        if (number < 20) { // Basic numbers defined in the word dictionary
            return BASIC_NUMBERS.get(number);
        } else if (number < 100) {
            return getTenNumberWord(groupNumber);
        } else {
            return getHundredWord(groupNumber);
        }
    }

    public String getTenNumberWord(NumberGroupStepDto groupNumber) {
        final int firstDigit = groupNumber.getSecondDigit();
        final int secondDigit = groupNumber.getThirdDigit();
        final String firstWord = TENS_NUMBERS.get(firstDigit);
        final String secondWord = secondDigit == 0 ? "" : "-" + BASIC_NUMBERS.get(secondDigit);

        return firstWord + secondWord;
    }

    public String getHundredWord(NumberGroupStepDto groupNumber) throws ConversionException {
        final int firstDigit = groupNumber.getFirstDigit();
        final String firstWord = BASIC_NUMBERS.get(firstDigit) + " " + UNITS_DEFINITION.get(HUNDRED_UNIT_GROUP);
        final String remainingNumber = groupNumber.getTenPart();
        final NumberGroupStepDto tensGroupNumber = new NumberGroupStepDto(remainingNumber);
        final String secondWord = tensGroupNumber.getNumber() == 0 ? "" : " " + getTenNumberWord(tensGroupNumber);

        return firstWord + secondWord;
    }

    private static int validateInput(final String input) throws ConversionException {
        if (StringUtils.isEmpty(input)) {
            throw new ConversionException("Provided number is null or empty!");
        }

        try {
            final double bigNumber = Long.parseLong(input);
            if (bigNumber > Integer.MAX_VALUE || bigNumber < Integer.MIN_VALUE) {
                throw new ConversionException("Provided number is not in the range of Integers");
            }

            return abs(Integer.parseInt(input));
        } catch (NumberFormatException ex) {
            throw new ConversionException(String.format("Input <%s> is not a valid number", input));
        }
    }
}
