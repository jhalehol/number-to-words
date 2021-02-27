package com.jholguin.converter.service;

import com.jholguin.converter.exception.ConversionException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class ConverterServiceTest {

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    private ConverterService service;

    @Before
    public void setup() {
        service = new ConverterService();
    }

    @Test
    public void givenUnitNumberWhenConvertToWordThenReturnWord() throws Exception {
        // Act
        final String result1 = service.convertNumberToString("3");
        final String result2 = service.convertNumberToString("6");
        final String result3 = service.convertNumberToString("0");


        // Assert
        errorCollector.checkThat(result1, equalTo("three"));
        errorCollector.checkThat(result2, equalTo("six"));
        errorCollector.checkThat(result3, equalTo("zero"));
    }

    @Test
    public void givenTenNumberWhenConvertToWordThenReturnWord() throws Exception {
        // Act
        final String result1 = service.convertNumberToString("16");
        final String result2 = service.convertNumberToString("19");
        final String result3 = service.convertNumberToString("14");
        final String result4 = service.convertNumberToString("11");

        // Assert
        errorCollector.checkThat(result1, equalTo("sixteen"));
        errorCollector.checkThat(result2, equalTo("nineteen"));
        errorCollector.checkThat(result3, equalTo("fourteen"));
        errorCollector.checkThat(result4, equalTo("eleven"));
    }

    @Test
    public void givenTenUpperNumberWhenConvertToWordThenReturnWord() throws Exception {
        // Act
        final String result1 = service.convertNumberToString("31");
        final String result2 = service.convertNumberToString("87");
        final String result3 = service.convertNumberToString("20");

        // Assert
        errorCollector.checkThat(result1, equalTo("thirty-one"));
        errorCollector.checkThat(result2, equalTo("eighty-seven"));
        errorCollector.checkThat(result3, equalTo("twenty"));
    }

    @Test
    public void givenHundredNumberWhenConvertToWordThenReturnWord() throws Exception {
        // Act
        final String result1 = service.convertNumberToString("100");
        final String result2 = service.convertNumberToString("356");
        final String result3 = service.convertNumberToString("889");
        final String result4 = service.convertNumberToString("777");

        // Assert
        errorCollector.checkThat(result1, equalTo("one hundred"));
        errorCollector.checkThat(result2, equalTo("three hundred fifty-six"));
        errorCollector.checkThat(result3, equalTo("eight hundred eighty-nine"));
        errorCollector.checkThat(result4, equalTo("seven hundred seventy-seven"));
    }

    @Test
    public void givenThousandNumberWhenConvertToWordThenReturnWord() throws Exception {
        // Act
        final String result1 = service.convertNumberToString("1001");
        final String result2 = service.convertNumberToString("2456");
        final String result3 = service.convertNumberToString("8341");
        final String result4 = service.convertNumberToString("5555");

        // Assert
        errorCollector.checkThat(result1, equalTo("one thousand one"));
        errorCollector.checkThat(result2, equalTo("two thousand four hundred fifty-six"));
        errorCollector.checkThat(result3, equalTo("eight thousand three hundred forty-one"));
        errorCollector.checkThat(result4, equalTo("five thousand five hundred fifty-five"));
    }

    @Test
    public void givenHundredThousandNumberWhenConvertToWordThenReturnWord() throws Exception {
        // Act
        final String result1 = service.convertNumberToString("125555");
        final String result2 = service.convertNumberToString("300986");
        final String result3 = service.convertNumberToString("888888");
        final String result4 = service.convertNumberToString("933100");


        // Assert
        errorCollector.checkThat(result1, equalTo("one hundred twenty-five thousand five hundred fifty-five"));
        errorCollector.checkThat(result2, equalTo("three hundred thousand nine hundred eighty-six"));
        errorCollector.checkThat(result3, equalTo("eight hundred eighty-eight thousand eight hundred eighty-eight"));
        errorCollector.checkThat(result4, equalTo("nine hundred thirty-three thousand one hundred"));

    }

    @Test
    public void givenMillionNumberWhenConvertToWordThenReturnWord() throws Exception {
        // Act
        final String result1 = service.convertNumberToString("2455666");
        final String result2 = service.convertNumberToString("34766122");
        final String result3 = service.convertNumberToString("786333876");

        // Assert
        errorCollector.checkThat(result1, equalTo("two million four hundred fifty-five thousand six hundred sixty-six"));
        errorCollector.checkThat(result2, equalTo("thirty-four million seven hundred sixty-six thousand one hundred twenty-two"));
        errorCollector.checkThat(result3, equalTo("seven hundred eighty-six million three hundred thirty-three thousand eight hundred seventy-six"));
    }

    @Test
    public void givenBillionNumberWhenConvertToWordThenReturnWord() throws Exception {
        // Act
        final String result1 = service.convertNumberToString("1234576987");
        final String result2 = service.convertNumberToString(String.valueOf(Integer.MAX_VALUE));

        // Assert
        errorCollector.checkThat(result1, equalTo("one billion two hundred thirty-four million five hundred seventy-six thousand nine hundred eighty-seven"));
        errorCollector.checkThat(result2, equalTo("two billion one hundred forty-seven million four hundred eighty-three thousand six hundred forty-seven"));
    }

    @Test
    public void givenInvalidNumberWhenConvertToWordThenShouldFail() throws Exception {
        // Arrange
        final String invalidNumber = "abc";
        // Act && Assert
        exceptionRule.expect(ConversionException.class);
        exceptionRule.expectMessage(String.format("Input <%s> is not a valid number", invalidNumber));

        service.convertNumberToString(invalidNumber);
    }

    @Test
    public void givenEmptyNumberWhenConvertToWordThenShouldFail() throws Exception {
        // Act && Assert
        exceptionRule.expect(ConversionException.class);
        exceptionRule.expectMessage("Provided number is null or empty!");

        service.convertNumberToString(null);
    }

    @Test
    public void givenValueOverMaximumWhenConvertToWordThenShouldFail() throws Exception {
        // Arrange
        final Long maxValue = 2147483648L;

        // Act && Assert
        exceptionRule.expect(ConversionException.class);
        exceptionRule.expectMessage("Provided number is not in the range of Integers");

        service.convertNumberToString(String.valueOf(maxValue));
    }
}