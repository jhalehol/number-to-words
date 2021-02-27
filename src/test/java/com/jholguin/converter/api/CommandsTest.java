package com.jholguin.converter.api;

import com.jholguin.converter.exception.ConversionException;
import com.jholguin.converter.service.ConverterService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandsTest {

    private static final String NUMBER_WORDS = "one thousand";
    private static final String NUMBER_TO_CONVERT = "1000";

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Mock
    private ConverterService converterService;

    @InjectMocks
    private Commands commands;

    @Test
    public void givenNumberWhenConvertThenShouldBeSuccess() throws Exception {
        // Arrange
        when(converterService.convertNumberToString(NUMBER_TO_CONVERT)).thenReturn(NUMBER_WORDS);

        // Act
        final String result = commands.convertNumberToString(NUMBER_TO_CONVERT);

        // Assert
        verify(converterService).convertNumberToString(NUMBER_TO_CONVERT);
        errorCollector.checkThat(result, equalTo(NUMBER_WORDS));
    }

    @Test
    public void givenNumberWhenConvertFailThenShouldFailWithMessage() throws Exception {
        // Arrange
        when(converterService.convertNumberToString(NUMBER_TO_CONVERT))
                .thenThrow(new ConversionException("Error converting"));

        // Act
        final String result = commands.convertNumberToString(NUMBER_TO_CONVERT);

        // Assert
        verify(converterService).convertNumberToString(NUMBER_TO_CONVERT);
        errorCollector.checkThat(result, equalTo("Unable to complete the conversion, reason: Error converting"));
    }
}
