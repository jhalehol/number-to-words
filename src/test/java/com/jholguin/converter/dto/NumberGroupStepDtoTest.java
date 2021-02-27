package com.jholguin.converter.dto;

import com.jholguin.converter.exception.ConversionException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class NumberGroupStepDtoTest {

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void givenValidGroupNumberWhenInitializeThenShouldBeSuccess() throws Exception {
        // Arrange
        final String groupNumber = "876";

        // Act
        final NumberGroupStepDto numberGroup = new NumberGroupStepDto(groupNumber);

        // Assert
        errorCollector.checkThat(numberGroup.getNumber(), equalTo(876));
        errorCollector.checkThat(numberGroup.getFirstDigit(), equalTo(8));
        errorCollector.checkThat(numberGroup.getSecondDigit(), equalTo(7));
        errorCollector.checkThat(numberGroup.getThirdDigit(), equalTo(6));
        errorCollector.checkThat(numberGroup.getStringNumber(), equalTo(groupNumber));
        errorCollector.checkThat(numberGroup.getTenPart(), equalTo("76"));
    }

    @Test
    public void givenInvalidMaxLengthGroupNumberWhenInitializeThenShouldFail() throws Exception {
        // Act && Assert
        exceptionRule.expect(ConversionException.class);
        exceptionRule.expectMessage("Invalid group length");

        new NumberGroupStepDto("9876");
    }

    @Test
    public void givenInvalidGroupNumberWhenInitializeThenShouldFail() throws Exception {
        // Act && Assert
        exceptionRule.expect(ConversionException.class);
        exceptionRule.expectMessage("Unable to convert number group to number");

        new NumberGroupStepDto("abc");
    }
}
