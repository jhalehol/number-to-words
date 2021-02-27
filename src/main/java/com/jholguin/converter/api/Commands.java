package com.jholguin.converter.api;

import com.jholguin.converter.exception.ConversionException;
import com.jholguin.converter.service.ConverterService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class Commands {

    private static final String CONVERSION_ERROR = "Unable to complete the conversion, reason: %s";
    private final ConverterService converterService;

    public Commands(ConverterService converterService) {
        this.converterService = converterService;
    }

    @ShellMethod(value = "Convert an integer number in english words", key = "convert")
    public String convertNumberToString(@ShellOption(help = "Number for conversion", defaultValue = "") String number) {
        try {
            return converterService.convertNumberToString(number);
        } catch (ConversionException e) {
            return String.format(CONVERSION_ERROR, e.getMessage());
        }
    }
}
