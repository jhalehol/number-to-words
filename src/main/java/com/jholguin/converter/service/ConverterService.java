package com.jholguin.converter.service;

import com.jholguin.converter.dto.NumberGroupStepDto;
import com.jholguin.converter.exception.ConversionException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import static com.jholguin.converter.common.NumberUnitGroups.BILLION;
import static com.jholguin.converter.common.NumberUnitGroups.HUNDRED;

@Component
public class ConverterService {

    private Logger logger = LoggerFactory.getLogger(ConverterService.class);

    private static final String MAX_NUMBER_DIGITS_FORMAT = "#000000000000";
    private static final int GROUP_LENGTH = 3;

    private static List<String> basic_numbers = Arrays.asList(
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    );

    private static List<String> tensUpper = Arrays.asList(
            "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    );

    public String convertNumberToString(String input) throws ConversionException {
        final int number = validateInput(input);
        final DecimalFormat maxDigitsFormat = new DecimalFormat(MAX_NUMBER_DIGITS_FORMAT);
        final String formattedNumber = maxDigitsFormat.format(number);

        logger.debug(String.format("Starting to convert %s", input));
        final int billionGroupIndex = BILLION.getValue();
        final int hundredGroupIndex = HUNDRED.getValue();
        int currentIndex = 0;
        final StringBuilder completeNumberWords = new StringBuilder();
        for (int groupIndex = billionGroupIndex; groupIndex >= hundredGroupIndex; groupIndex--) {
            final String groupNumber = formattedNumber.substring(currentIndex, currentIndex + GROUP_LENGTH);
            final NumberGroupStepDto groupNumberStep = new NumberGroupStepDto(groupNumber);
            if (groupNumberStep.getNumber() > 0 || groupIndex == hundredGroupIndex) {
                final String groupWord = getHundredNumberWord(groupNumberStep);
                final String unitsWord = groupIndex == 1 ? "" : " " + HUNDRED.nameFromGroup(groupIndex) + " ";

                completeNumberWords.append(groupWord)
                        .append(unitsWord);
            }

            currentIndex += GROUP_LENGTH;
        }

        return StringUtils.normalizeSpace(completeNumberWords.toString());
    }

    public String getHundredNumberWord(NumberGroupStepDto groupNumber) throws ConversionException {
        final int number = groupNumber.getNumber();
        final StringBuilder numberWords = new StringBuilder();
        if (number < 20) {
            final String numberWord = basic_numbers.get(number);
            numberWords.append(numberWord);
        } else if (number < 100) {
            final int firstDigit = groupNumber.getSecondDigit();
            final int secondDigit = groupNumber.getThirdDigit();
            final String firstWord = tensUpper.get(firstDigit - 2);
            final String secondWord = secondDigit == 0 ? "" : "-" + basic_numbers.get(secondDigit);
            numberWords.append(firstWord).append(secondWord);
        } else {
            final int firstDigit = groupNumber.getFirstDigit();
            final String firstWord = basic_numbers.get(firstDigit) + " " + HUNDRED.getName();
            final String remainingNumber = groupNumber.getTenPart();
            final NumberGroupStepDto tensGroupNumber = new NumberGroupStepDto(remainingNumber);
            final String secondWord = tensGroupNumber.getNumber() == 0 ? "" : " " + getHundredNumberWord(tensGroupNumber);

            numberWords.append(firstWord).append(secondWord);
        }

        return numberWords.toString();
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

            return Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            throw new ConversionException(String.format("Input <%s> is not a valid number", input));
        }
    }
}
